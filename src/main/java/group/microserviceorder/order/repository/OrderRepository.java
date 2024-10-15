package group.microserviceorder.order.repository;


import group.microserviceorder.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUserId(Long userId);
    Optional<Order> findOrderById(Long Id);
}
