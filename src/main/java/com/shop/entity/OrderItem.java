package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * 주문 상품 엔티티
 */
@Getter
@Setter
@Entity
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 하나의 상품은 여러 주문 상품으로 들어갈 수 있으므로
    // 주문 상품을 기준으로 다대일 단방향 매핑을 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 한 번의 부문에 여러 개의 상품을 주문할 수 있으므로
    // 주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑을 먼저 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격

    private int count;      // 수량

    //private LocalDateTime regTime;
    //private LocalDateTime updateTime;

    // 주문할 상품과 주문 수량을 통해 OrderItem 객체를 만드는 메소드
    public static OrderItem createOrderItem(Item item, int count) {

        OrderItem orderItem = new OrderItem();
        // 주문할 상품과 주문 수량을 세팅
        orderItem.setItem(item);
        orderItem.setCount(count);
        // 현재 시간 기준으로 상품 가격을 주문 가격으로 세팅
        orderItem.setOrderPrice(item.getPrice());

        // 주문 수량만큼 상품의 재고 수량을 감소
        item.removeStock(count);

        return orderItem;
    }

    public int getTotalPrice() {

        return orderPrice * count;
    }
}
