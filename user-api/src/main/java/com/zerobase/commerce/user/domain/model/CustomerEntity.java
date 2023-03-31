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

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AuditOverride(forClass = BaseEntity.class)
public class CustomerEntity extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String email;
  private String name;
  private String password;
  private LocalDate birth;
  private String phone;

  private LocalDateTime verifyExpiredAt;
  private String verificationCode;
  private boolean verify;

  public static CustomerEntity from(SignupForm form) {
    return CustomerEntity.builder()
        .email(form.getEmail().toLowerCase(Locale.ROOT))
        .name(form.getName())
        .password(form.getPassword()) // pw 인코딩
        .birth(form.getBirth())
        .phone(form.getPhone()) // validate 확인 필요
        .verify(false)
        .build();
  }
}
