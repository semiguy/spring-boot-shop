package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * 주문 엔티티
 */
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;    // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문 상태

    /*
     * 1) 주문 상품 엠티티와 일대다 매핑을 합니다. 외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은
     * OrderItem 엔티티 입니다. Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정합니다.
     * 속성의 값으로 "order"를 적어준 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미로 해석 하면 됩니다.
     * 즉, 연관 관계의 주인 필드인 order를 mappedBy의 값으로 세팅하면 됩니다.
     *
     * 2) 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용하여 매핑
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    //private LocalDateTime regTime;
    //private LocalDateTime updateTime;

    // 생성한 주문 상품 객체를 이용하여 주문 객체를 만드는 메소드
    // orderItems에는 주문 상품 정보들을 담아줍니다. orderItem 객체를 order 객체의 orderItems에 추가
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        // Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계 이므로, orderItem 객체에도 order 객체를 세팅
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {

        Order order = new Order();
        order.setMember(member); // 상품을 주문한 회원의 정보를 세팅
        // 상품 페이지에서는 1개의 상품을 주문하지만,
        // 장바구니 페이지에서는 한번에 여러 개의 상품을 주문할 수 있습니다.
        // 따라서 여러 개의 주문 상품을 담을 수 있도록 리스트형태로 따라서 여러 개의 주문 상품을 담을 수 있도록
        // 리스트 형태로 파라미터 값을 받으며 주문 객체에서 orderItem 객체를 추가 합니다.
        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER); // 주문 상태를 ORDER로 세팅
        order.setOrderDate(LocalDateTime.now()); // 현재 시간을 주문 시간으로 세팅

        return order;
    }

    // 총 주문 금액을 구하는 메소드
    public int getTotalPrice() {

        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }

    // 주문 최소 시 주문 수량을 상품의 재고에 더해주는 로직과
    // 주문 상태를 취소 상태로 변경하는 메소드
    public void cancelOrder() {

        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
