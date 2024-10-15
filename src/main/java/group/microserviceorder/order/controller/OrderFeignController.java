package group.microserviceorder.order.controller;

import group.microserviceorder.order.entity.Order;
import group.microserviceorder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external")
public class OrderFeignController {

    private final OrderService orderService;
    @GetMapping("orders/{orderId}")
    public Order getOrder(@PathVariable Long orderId){return orderService.getOrder(orderId);}

    @GetMapping("orders/total/{orderId}")
    public Long getTotalPrice(@PathVariable Long orderId){return orderService.getTotalPrice(orderId);}

}

