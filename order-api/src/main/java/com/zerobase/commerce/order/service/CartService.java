package com.zerobase.commerce.order.service;

import com.zerobase.commerce.order.client.RedisClient;
import com.zerobase.commerce.order.domain.product.AddProductCartForm;
import com.zerobase.commerce.order.domain.redis.Cart;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

  private final RedisClient redisClient;

  public Cart getCart(Long customerId) {
    Cart cart = redisClient.get(customerId, Cart.class);
    return cart != null ? cart : new Cart(customerId);
  }

  public Cart putCart(Long customerId, Cart cart) {
    redisClient.put(customerId, cart);

    return cart;
  }

  public Cart addCart(Long customerId, AddProductCartForm form) {
    Cart cart = getCart(customerId);

    // 같은 상품이 있는지 확인
    Optional<Cart.Product> productOptional = cart.getProducts().stream()
        .filter(p -> p.getId().equals(form.getId()))
        .findFirst();

    if (productOptional.isPresent()) {
      Cart.Product redisProduct = productOptional.get();

      // requested
      List<Cart.ProductItem> items = form.getItems().stream()
          .map(Cart.ProductItem::from).collect(Collectors.toList());

      Map<Long, Cart.ProductItem> redisItemMap = redisProduct.getItems().stream()
          .collect(Collectors.toMap(Cart.ProductItem::getId, it -> it));

      if (!redisProduct.getName().equals(form.getName())) {
        cart.addMessage(redisProduct.getName() + "의 정보가 변경되었습니다. 확인 부탁드립니다.");
      }

      for (Cart.ProductItem item : items) {
        Cart.ProductItem redisItem = redisItemMap.get(item.getId());

        if (redisItem == null) {
          redisProduct.getItems().add(item);
        } else {
          if (!redisItem.getPrice().equals(item.getPrice())) {
            cart.addMessage(redisProduct.getName() + " " + item.getName() +
                "의 가격이 변경되었습니다. 확인 부탁드립니다.");
          }
        }
      }
    } else {
      Cart.Product product = Cart.Product.from(form);
      cart.getProducts().add(product);
    }

    redisClient.put(customerId, cart);

    return cart;
  }
}
