package com.zerobase.commerce.user.domain.model;

import com.zerobase.commerce.user.domain.SignupForm;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity(name = "seller")
@AuditOverride(forClass = BaseEntity.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SellerEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String email;
  private String name;
  private String password;
  private String phone;
  private LocalDate birth;

  private LocalDateTime verifyExpiredAt;
  private String verificationCode;
  private boolean verify;

  public static SellerEntity from(SignupForm form) {
    return SellerEntity.builder()
        .email(form.getEmail().toLowerCase(Locale.ROOT))
        .name(form.getName())
        .password(form.getPassword())
        .phone(form.getPhone())
        .birth(form.getBirth())
        .verify(false)
        .build();
  }

}
