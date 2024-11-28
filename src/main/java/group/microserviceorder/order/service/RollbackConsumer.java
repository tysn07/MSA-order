package group.microserviceorder.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RollbackConsumer {
    private final OrderService orderService;
    @KafkaListener(groupId = "group-01")
    public void rollbackOrder(Long orderId){
        orderService.deleteOrder(orderId);
        System.out.print("Rollback order"+orderId);
    }
}
