package com.zerobase.commerce.order.application;

import static com.zerobase.commerce.order.exception.ErrorCode.ORDER_FAIL_CHECK_CART;
import static com.zerobase.commerce.order.exception.ErrorCode.ORDER_FAIL_NO_MONEY;

import com.zerobase.commerce.order.client.UserClient;
import com.zerobase.commerce.order.client.user.ChangeBalanceForm;
import com.zerobase.commerce.order.client.user.CustomerDto;
import com.zerobase.commerce.order.domain.model.ProductItemEntity;
import com.zerobase.commerce.order.domain.redis.Cart;
import com.zerobase.commerce.order.exception.CustomException;
import com.zerobase.commerce.order.service.ProductItemService;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerOrderApplication {

  private final CartApplication cartApplication;
  private final UserClient userClient;
  private final ProductItemService productItemService;

  /**
   * 결제를 위해 필요한 것 1 - 물건들이 전부 주문 가능한 상태인지 확인 2 - 가격 변동이 있었는지에 대해 확인 3 - 고객의 돈이 충분한지 4 - 결제 & 상품의
   * 재고관리
   */

  @Transactional
  public void order(String token, Cart cart) {
    // 1 - 주문 시 기존 카트 버림
    // 2 - 선택 주문 (내가 사지 않은 아이템을 살려야 함)
    // 숙제
    Cart orderCart = cartApplication.refreshCart(cart);
    if (orderCart.getMessages().size() > 0) {
      throw new CustomException(ORDER_FAIL_CHECK_CART);
    }

    CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();

    int totalPrice = getTotalPrice(cart);
    if (customerDto.getBalance() < totalPrice) {
      throw new CustomException(ORDER_FAIL_NO_MONEY);
    }

    // 롤백 계획 생각하기
    userClient.changeBalance(
        token,
        ChangeBalanceForm.builder()
            .from("USER")
            .message("Order")
            .money(-totalPrice)
            .build()
    );

    // 결제 내역 메일로 보내기 추가
    for (Cart.Product product : orderCart.getProducts()) {
      for (Cart.ProductItem cartItem : product.getItems()) {
        ProductItemEntity productItem = productItemService.getProductItem(cartItem.getId());
        productItem.setCount(productItem.getCount() - cartItem.getCount());
      }
    }
  }

  private Integer getTotalPrice(Cart cart) {
    return cart.getProducts().stream()
        .flatMapToInt(product -> product.getItems().stream().flatMapToInt(
            productItem -> IntStream.of(productItem.getPrice() * productItem.getCount())
        ))
        .sum();
  }
}
