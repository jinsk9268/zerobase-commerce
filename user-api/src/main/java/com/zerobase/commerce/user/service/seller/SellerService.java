package com.zerobase.commerce.user.service.seller;

import static com.zerobase.commerce.user.exception.ErrorCode.ALREADY_VERIFY;
import static com.zerobase.commerce.user.exception.ErrorCode.EXPIRE_CODE;
import static com.zerobase.commerce.user.exception.ErrorCode.NOT_FOUND_USER;
import static com.zerobase.commerce.user.exception.ErrorCode.WRONG_VERIFICATION;

import com.zerobase.commerce.user.domain.SignupForm;
import com.zerobase.commerce.user.domain.model.SellerEntity;
import com.zerobase.commerce.user.domain.repository.SellerRepository;
import com.zerobase.commerce.user.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository sellerRepository;

  public Optional<SellerEntity> findByIdAndEmail(Long id, String email) {
    return sellerRepository.findByIdAndEmail(id, email);
  }

  public Optional<SellerEntity> findValidSeller(String email, String password) {
    return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
  }

  public SellerEntity signup(SignupForm form) {
    return sellerRepository.save(SellerEntity.from(form));
  }

  public boolean isEmailExist(String email) {
    return sellerRepository.findByEmail(email).isPresent();
  }

  @Transactional
  public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode) {
    SellerEntity sellerEntity = sellerRepository.findById(sellerId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    sellerEntity.setVerificationCode(verificationCode);
    sellerEntity.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));

    return sellerEntity.getVerifyExpiredAt();
  }

  @Transactional
  public void verifyEmail(String email, String code) {
    SellerEntity sellerEntity = sellerRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    if (sellerEntity.isVerify()) {
      throw new CustomException(ALREADY_VERIFY);
    } else if (!sellerEntity.getVerificationCode().equals(code)) {
      throw new CustomException(WRONG_VERIFICATION);
    } else if (sellerEntity.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
      throw new CustomException(EXPIRE_CODE);
    }

    sellerEntity.setVerify(true);
  }

}
