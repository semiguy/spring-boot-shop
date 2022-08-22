package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
 * 장바구니 아이템(상품) 엔티티
 */
@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    /*
     * 하나의 장바구니에는 여러 개의 상품을 담을 수 있으므로 @ManyToOne 어노테이션을 이용하여 다대일 관계로 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    /*
     * 장바구니에 담을 상품의 정보를 알아야 하므로 상품 엔티티를 매핑해줍니다.
     * 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있으므로 마찬가지로 @ManyToOne 어노테이션을 이용하여
     * 다대일 관계로 매핑 합니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 같은 상품을 장바구니에 몇 개 담을지 저장합니다.
    private int count;
}
