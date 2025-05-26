package jpabarcode.jpashop.service;

import jpabarcode.jpashop.domain.Delivery;
import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.domain.Order;
import jpabarcode.jpashop.domain.OrderItem;
import jpabarcode.jpashop.domain.item.Item;
import jpabarcode.jpashop.repository.ItemRepository;
import jpabarcode.jpashop.repository.MemberRepository;
import jpabarcode.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        //주문취소
        order.cancel();
    }

    //검색
    //public List<Order> findOrders(OrderSearch orderSearch) {};
}
