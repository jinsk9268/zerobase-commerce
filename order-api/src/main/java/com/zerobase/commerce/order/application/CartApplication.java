package com.zerobase.commerce.order.application;

import static com.zerobase.commerce.order.exception.ErrorCode.ITEM_COUNT_NOT_ENOUGH;
import static com.zerobase.commerce.order.exception.ErrorCode.NOT_FOUND_PRODUCT;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import com.zerobase.commerce.order.domain.model.ProductItemEntity;
import com.zerobase.commerce.order.domain.product.AddProductCartForm;
import com.zerobase.commerce.order.domain.redis.Cart;
import com.zerobase.commerce.order.exception.CustomException;
import com.zerobase.commerce.order.service.CartService;
import com.zerobase.commerce.order.service.ProductSearchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartApplication {

  private final ProductSearchService productSearchService;
  private final CartService cartService;

  /**
   * 장바구니 추가
   */
  public Cart addCart(Long customerId, AddProductCartForm form) {
    ProductEntity product = productSearchService.getByProductId(form.getId());
    if (product == null) {
      throw new CustomException(NOT_FOUND_PRODUCT);
    }

    Cart cart = cartService.getCart(customerId);
    if (!addAble(cart, product, form)) {
      throw new CustomException(ITEM_COUNT_NOT_ENOUGH);
    }

    return cartService.addCart(customerId, form);
  }

  private boolean addAble(Cart cart, ProductEntity product, AddProductCartForm form) {
    Cart.Product cartProduct = cart.getProducts().stream()
        .filter(p -> p.getId().equals(form.getId())).findFirst()
        .orElse(Cart.Product.builder()
            .id(product.getId())
            .items(Collections.emptyList())
            .build());

    Map<Long, Integer> cartItemCountMap = cartProduct.getItems().stream()
        .collect(Collectors.toMap(Cart.ProductItem::getId, Cart.ProductItem::getCount));

    Map<Long, Integer> currentItemCountMap = product.getProductItems().stream()
        .collect(Collectors.toMap(ProductItemEntity::getId, ProductItemEntity::getCount));

    return form.getItems().stream().noneMatch(
        item -> {
          Integer cartCount = cartItemCountMap.get(item.getId());
          if (cartCount == null) {
            cartCount = 0;
          }

          Integer currentCount = currentItemCountMap.get(item.getId());

          return (item.getCount() + cartCount) > currentCount;
        }
    );
  }

  /**
   * 장바구니 가져오기
   */
  public Cart getCart(Long customerId) {
    Cart cart = refreshCart(cartService.getCart(customerId));
    cartService.putCart(cart.getCustomerId(), cart);

    Cart returnCart = new Cart();
    returnCart.setCustomerId(customerId);
    returnCart.setProducts(cart.getProducts());
    returnCart.setMessages(cart.getMessages());

    cart.setMessages(new ArrayList<>());
    cartService.putCart(customerId, cart);

    return returnCart;
  }

  /**
   * 장바구니 새로고침
   */
  protected Cart refreshCart(Cart cart) {
    Map<Long, ProductEntity> productMap = productSearchService.getListByProductIds(
            cart.getProducts().stream().map(Cart.Product::getId).collect(Collectors.toList())
        ).stream()
        .collect(Collectors.toMap(ProductEntity::getId, product -> product));

    for (int i = 0; i < cart.getProducts().size(); i++) {
      Cart.Product cartProduct = cart.getProducts().get(i);

      ProductEntity product = productMap.get(cartProduct.getId());

      if (product == null) {
        cart.getProducts().remove(cartProduct);
        i--;
        cart.addMessage(cartProduct.getName() + " 상품이 삭제되었습니다.");
        continue;
      }

      Map<Long, ProductItemEntity> productItemMap = product.getProductItems().stream()
          .collect(Collectors.toMap(ProductItemEntity::getId, item -> item));

      List<String> tempMessages = new ArrayList<>();
      for (int j = 0; j < cartProduct.getItems().size(); j++) {
        Cart.ProductItem cartProductItem = cartProduct.getItems().get(j);
        ProductItemEntity pi = productItemMap.get(cartProductItem.getId());

        if (pi == null) {
          cartProduct.getItems().remove(cartProductItem);
          j--;
          tempMessages.add(cartProductItem.getName() + " 옵션이 삭제되었습니다.");
          continue;
        }

        boolean isPriceChanged = false;
        boolean isCountNotEnough = false;

        if (!cartProductItem.getPrice().equals(pi.getPrice())) {
          isPriceChanged = true;
          cartProductItem.setPrice(pi.getPrice());
        }

        if (cartProductItem.getCount() > pi.getCount()) {
          isCountNotEnough = true;
          cartProductItem.setCount(pi.getCount());
        }

        if (isPriceChanged && isCountNotEnough) {
          tempMessages.add(cartProductItem.getName() + " 가격 변동 및 수량 부족으로 구매 가능한 최대치로 변경되었습니다.");
        } else if (isPriceChanged) {
          tempMessages.add(cartProductItem.getName() + " 가격이 변동되었습니다.");
        } else if (isCountNotEnough) {
          tempMessages.add(cartProduct.getName() + " 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
        }
      }

      if (cartProduct.getItems().size() == 0) {
        cart.getProducts().remove(cartProduct);
        i--;
        cart.addMessage(cartProduct.getName() + " 상품의 옵션이 모두 없어져 구매가 불가능합니다.");
      } else if (tempMessages.size() > 0) {
        StringBuilder sb = new StringBuilder();

        sb.append(cartProduct.getName() + " 상품의 변동 사항 : ");
        for (String message : tempMessages) {
          sb.append(message);
          sb.append(", ");
        }

        cart.addMessage(sb.toString());
      }
    }

    return cart;
  }

  /**
   * 장바구니 업데이트
   */
  public Cart updateCart(Long customerId, Cart cart) {
    cartService.putCart(customerId, cart);
    return getCart(customerId);
  }

  /**
   * 장바구니 삭제
   */
  public void clearCart(Long customerId) {
    cartService.putCart(customerId, null);
  }
}
