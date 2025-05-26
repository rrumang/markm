package jpabarcode.jpashop.service;

import jpabarcode.jpashop.domain.Address;
import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.domain.Order;
import jpabarcode.jpashop.domain.OrderStatus;
import jpabarcode.jpashop.domain.item.Item;
import jpabarcode.jpashop.domain.item.Print;
import jpabarcode.jpashop.exception.NotEnoughStockException;
import jpabarcode.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Item printer = createPrinter("마크엠프린터", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), printer.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 산태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문가격은 가격 * 수량이다", 20000, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다.", 8, printer.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item printer = createPrinter("마크엠프린터", 10000, 10);

        int orderCount = 11;

        //when
        orderService.order(member.getId(), printer.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }
    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createPrinter("마크엠2 프린터", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소괸 상품은 그만큼 재고가 증가해야한다.", 10, item.getStockQuantity());

    }

    private Item createPrinter(String name, int price, int quantity) {
        Item printer = new Print();
        printer.setName(name);
        printer.setPrice(price);
        printer.setStockQuantity(quantity);
        em.persist(printer);
        return printer;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("유민하");
        member.setAddress(new Address("대전", "월평로", "252779"));
        em.persist(member);
        return member;
    }

}