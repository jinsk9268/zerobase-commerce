package com.zerobase.commerce.order.domain.model;

import com.zerobase.commerce.order.domain.product.AddProductItemForm;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity(name = "product_item")
@AuditOverride(forClass = BaseEntity.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductItemEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long sellerId;
  @Audited
  private String name;
  @Audited
  private Integer price;
  private Integer count;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  public static ProductItemEntity of(Long sellerId, AddProductItemForm form) {
    return ProductItemEntity.builder()
        .sellerId(sellerId)
        .name(form.getName())
        .price(form.getPrice())
        .count(form.getCount())
        .build();
  }
}