package com.zerobase.commerce.user.controller;

import static com.zerobase.commerce.user.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.commerce.user.domain.model.SellerEntity;
import com.zerobase.commerce.user.domain.seller.SellerDto;
import com.zerobase.commerce.user.exception.CustomException;
import com.zerobase.commerce.user.service.seller.SellerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

  private final JwtAuthenticationProvider provider;
  private final SellerService sellerService;

  @GetMapping("/info")
  public ResponseEntity<SellerDto> getSellerInfo(
      @RequestHeader(name = "X-AUTH-TOKEN") String token
  ) {
    UserVo vo = provider.getUserVo(token);

    SellerEntity seller = sellerService.findByIdAndEmail(vo.getId(), vo.getEmail())
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    return ResponseEntity.ok(SellerDto.from(seller));
  }
}
