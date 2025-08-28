package com.niyava.ecom.orderservice.test.integrationtest;

import com.niyava.ecom.orderservice.config.DBConfiguration;
import com.niyava.ecom.orderservice.config.MySQLConfig;
import com.niyava.ecom.orderservice.entity.Order;
import com.niyava.ecom.orderservice.repository.OrderRepository;
import com.niyava.ecom.orderservice.test.TestOrderFactory;
import com.niyava.ecom.orderservice.util.OrderDateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static com.niyava.ecom.orderservice.test.TestOrderConstants.ORDER_STATUS_PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/DataJpaTest.html
 * If you are looking to load your full application configuration, but use an embedded database,
 * you should consider @SpringBootTest combined with @AutoConfigureTestDatabase rather than this annotation.
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MySQLConfig.class, DBConfiguration.class})
public class OrderRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void contextLoads() {
        assertNotNull(testEntityManager);
    }

    @Test
    @Rollback(value = false)
    public void whenFindByOrderStatus_thenReturnOrderList() {
        //given
        Order order = TestOrderFactory.createNewOrder();
        Long orderId = testEntityManager.persist(order).getId();
        testEntityManager.flush();
        //when
        List<Order> found = orderRepository.findByOrderStatus(ORDER_STATUS_PENDING);
        //then
        System.out.println("orders::" + found);
        assertThat(found.size()).isNotEqualTo(0);
    }

    @Test
    @Rollback(value = false)
    public void whenFindOrderByUserEmailAndId_thenReturnOrder() {
        //given
        Order order = TestOrderFactory.createNewOrder();
        Long orderId = testEntityManager.persist(order).getId();
        testEntityManager.flush();
        //when
        Order found = orderRepository.findOrderByUserEmailAndId(order.getUserEmail(), orderId);
        //then
        assertThat(found.getUserEmail()).isEqualTo(order.getUserEmail());
        assertThat(found.getId()).isEqualTo(orderId);
    }

    @Test
    @Rollback(value = false)
    public void findAllOrdersByUserEmailAndOrderDate() {
        //given
        Order order = TestOrderFactory.createNewOrder();
        Long orderId = testEntityManager.persist(order).getId();
        testEntityManager.flush();
        //when
        List<Order> found = orderRepository.findAllOrdersByUserEmailAndOrderDate(order.getUserEmail(),
                OrderDateUtil.toLocalDate(new Date()));
        //then
        assertThat(found).isNotEmpty();
        assertThat(found).filteredOn(order1 -> order1.getUserEmail().equalsIgnoreCase(order.getUserEmail())).isNotNull();
    }


}
