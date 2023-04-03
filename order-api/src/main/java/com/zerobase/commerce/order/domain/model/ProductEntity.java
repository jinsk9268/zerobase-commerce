package com.zerobase.commerce.order.domain.model;

import com.zerobase.commerce.order.domain.product.AddProductForm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity(name = "product")
@Audited // 변경사항 트래킹
@AuditOverride(forClass = BaseEntity.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long sellerId;
  private String name;
  private String description;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id")
  private List<ProductItemEntity> productItems = new ArrayList<>();

  public static ProductEntity of(Long sellerId, AddProductForm form) {
    return ProductEntity.builder()
        .sellerId(sellerId)
        .name(form.getName())
        .description(form.getDescription())
        .productItems(form.getItems().stream()
            .map(piForm -> ProductItemEntity.of(sellerId, piForm))
            .collect(Collectors.toList()))
        .build();
  }
}