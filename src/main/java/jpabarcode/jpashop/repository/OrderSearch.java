package jpabarcode.jpashop.repository;

import jpabarcode.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;          //회원이름
    private OrderStatus orderStatus;    //주문상태[ORDRER, CANCEL]
}
