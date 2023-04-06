package com.zerobase.commerce.order.application;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import com.zerobase.commerce.order.domain.product.AddProductCartForm;
import com.zerobase.commerce.order.domain.product.AddProductForm;
import com.zerobase.commerce.order.domain.product.AddProductItemForm;
import com.zerobase.commerce.order.domain.redis.Cart;
import com.zerobase.commerce.order.domain.repository.ProductRepository;
import com.zerobase.commerce.order.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartApplicationTest {

  @Autowired
  private CartApplication cartApplication;
  @Autowired
  private ProductService productService;
  @Autowired
  private ProductRepository productRepository;

  @Test
  void addModifyTest() {
    // given
    Long customerId = 100L;
    cartApplication.clearCart(customerId);

    // when
    ProductEntity product = addProduct();
    ProductEntity result = productRepository.findWithProductItemsById(product.getId()).get();

    // then
    assertEquals(result.getName(), "나이키");
    assertEquals(result.getDescription(), "신발");
    assertEquals(result.getProductItems().size(), 3);
    assertEquals(result.getProductItems().get(0).getName(), "나이키0");
    assertEquals(result.getProductItems().get(0).getPrice(), 10000);

    Cart cart = cartApplication.addCart(customerId, makeAddForm(result));
    assertEquals(cart.getMessages().size(), 0);

    cart = cartApplication.getCart(customerId);
    assertEquals(cart.getMessages().size(), 1);
  }

  private AddProductCartForm makeAddForm(ProductEntity p) {
    AddProductCartForm.ProductItem productItem =
        AddProductCartForm.ProductItem.builder()
            .id(p.getProductItems().get(0).getId())
            .name(p.getProductItems().get(0).getName())
            .count(5)
            .price(20000)
            .build();

    return AddProductCartForm.builder()
        .id(p.getId())
        .sellerId(p.getSellerId())
        .name(p.getName())
        .description(p.getDescription())
        .items(List.of(productItem))
        .build();
  }

  private ProductEntity addProduct() {
    Long sellerId = 1L;
    AddProductForm form = makeProductForm("나이키", "신발", 3);
    return productService.addProduct(sellerId, form);
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
        .count(10)
        .build();
  }
}