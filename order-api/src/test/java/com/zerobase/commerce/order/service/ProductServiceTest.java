package com.zerobase.commerce.order.service;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import com.zerobase.commerce.order.domain.product.AddProductForm;
import com.zerobase.commerce.order.domain.product.AddProductItemForm;
import com.zerobase.commerce.order.domain.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

  @Autowired
  private ProductService productService;
  @Autowired
  private ProductRepository productRepository;

  @Test
  void addProductTest() {
    // given
    Long sellerId = 1L;
    AddProductForm form = makeProductForm("나이키 에어포스", "신발", 3);
    ProductEntity product = productService.addProduct(sellerId, form);

    // when
    ProductEntity result = productRepository.findWithProductItemsById(product.getId()).get();

    // then
    assertNotNull(result);
    assertEquals(result.getName(), "나이키 에어포스");
    assertEquals(result.getProductItems().size(), 3);
    assertEquals(result.getProductItems().get(0).getName(), "나이키 에어포스0");
    assertEquals(result.getProductItems().get(1).getPrice(), 10000);
    assertEquals(result.getProductItems().get(1).getCount(), 1);
  }

  private AddProductForm makeProductForm(String name, String description, int itemCount) {
    List<AddProductItemForm> itemForms = new ArrayList<>();

    for (int i = 0; i < itemCount; i++) {
      itemForms.add(makeProductItemForm(null, name + i));
    }

    return AddProductForm.builder()
        .name(name)
        .description(description)
        .items(itemForms)
        .build();
  }

  private AddProductItemForm makeProductItemForm(Long productId, String name) {
    return AddProductItemForm.builder()
        .productId(productId)
        .name(name)
        .price(10000)
        .count(1)
        .build();
  }
}