package group.microserviceorder.order.repository;


import group.microserviceorder.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findOrderDetailsByOrderId(Long orderId);
}
