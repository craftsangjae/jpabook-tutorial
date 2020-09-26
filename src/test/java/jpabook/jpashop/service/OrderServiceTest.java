package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
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

    @Autowired
    private EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook();

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order targetOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 Order", OrderStatus.ORDER, targetOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, targetOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 100000 * orderCount, targetOrder.getTotalPrice());;
        assertEquals("주문 수량만큼 재고가 줄어야 한다..", 8 , book.getStockQuantity());;

   }

    private Book createBook() {
        Book book = Book.create("시골 JPA", 100000, 10, "", "");
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void 주문최소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook();

        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order targetOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 CANCEL", OrderStatus.CANCEL, targetOrder.getStatus());
        assertEquals("재고 갯수가 돌아와야 한다", 10 , book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook();

        //when
        int orderCount = 11;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고수량초과로 인해 NotEnoughStockException이 발생해야 합니다.");
    }

}