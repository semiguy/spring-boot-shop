package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/*
 * 장바구니 엔티티
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
     * @OneToOne 어노테이션을 이용해 회원 엔티티와 일대일로 매핑을 합니다.
     * @JoinColumn 어노테이션을 이용해 매핑할 외래키를 지정합니다. name 속성에는 매핑할 외래키의 이름을 설정 합니다.
     * @JoinColumn의 name을 명시하지 않으면 JPA가 알아서 ID를 찾지만 컬럼명이 원하는 대로 생성되지 않을 수 있기 때문에
     * 직접 지정합니다.
     * @OneToOne, @ManyToOne 으로 매핑할 경우 즉시 로딩을 기본 Fetch 전략으로 설정합니다.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
