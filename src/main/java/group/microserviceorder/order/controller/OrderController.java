package group.microserviceorder.order.controller;


import group.microserviceorder.common.security.UserDetailsImpl;
import group.microserviceorder.order.dto.OrderDetailResponseDto;
import group.microserviceorder.order.dto.OrderRequestDto;
import group.microserviceorder.order.dto.OrderResponseDto;
import group.microserviceorder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<String> makeOrder(@RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        orderService.createOrder(requestDto.getBasket(),userDetails, requestDto.getAddress());
        return ResponseEntity.status(201)
                .body("order created");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderDetailResponseDto>> getOrder(@PathVariable Long orderId){
            return ResponseEntity.status(200).body(orderService.getOrderDetailList(orderId));
    }

    @GetMapping("/userorder")
    public ResponseEntity<List<OrderResponseDto>> getUserOrder(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(200).body(orderService.getOrderList(userDetails));}

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
            orderService.deleteOrder(orderId);
        return ResponseEntity.status(201).body("cancel order");
    }

    @GetMapping()
    public String test(){
        return "OK";

    }

}
