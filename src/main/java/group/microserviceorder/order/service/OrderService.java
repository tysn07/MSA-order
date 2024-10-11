package group.microserviceorder.order.service;


import group.microserviceorder.common.security.UserDetailsImpl;
import group.microserviceorder.order.dto.OrderDetailResponseDto;
import group.microserviceorder.order.dto.OrderResponseDto;
import group.microserviceorder.order.entity.Order;
import group.microserviceorder.order.entity.OrderDetail;
import group.microserviceorder.order.entity.OrderState;
import group.microserviceorder.order.repository.OrderDetailRepository;
import group.microserviceorder.order.repository.OrderRepository;
import group.microserviceorder.remote.feign.ProductFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductFeignClient productFeignClient;

    @Transactional
    public void createOrder(Map<Long,Long> basket, UserDetailsImpl userDetails, String address) throws Exception {
        if(address.isEmpty()){
            throw new RuntimeException("주소를 선택하십시오");
        }
        checkBasket(basket);
        Order order = new Order(userDetails.getUser().getId(),address, OrderState.NOTPAYED);
        orderRepository.save(order);
        for(Long key:basket.keySet()){
            updateStock(key,basket.get(key));
            OrderDetail orderDetail= new OrderDetail(order.getId(),key,basket.get(key),productFeignClient.getProduct(key).getPrice(),productFeignClient.getProduct(key).getName());
            orderDetailRepository.save(orderDetail);
        }

    }
    public List<OrderDetailResponseDto> getOrderDetailList(Long orderId){
        List<OrderDetail> listOfOrderedProducts = orderDetailRepository.findOrderDetailsByOrderId(orderId);
        return listOfOrderedProducts.stream().map(OrderDetailResponseDto::new).toList();
    }
    @Transactional
    public void deleteOrder(Long orderId){
        orderDetailRepository.deleteAll(orderDetailRepository.findOrderDetailsByOrderId(orderId));
        orderRepository.delete(orderRepository.getById(orderId));
    }

    public boolean checkUser(UserDetailsImpl userDetails,Long orderId){
        return Objects.equals(userDetails.getUser().getId(), orderRepository.getById(orderId).getUserId());
    }

    public void updateStock(Long productId,Long quantity){
        productFeignClient.getProduct(productId).updateStockAfterOrder(quantity);

    }

    public boolean checkStock(Long productId,Long quantity){
        return productFeignClient.getProduct(productId).getStock() - quantity >= 0;
    }

    public List<OrderResponseDto> getOrderList(UserDetailsImpl userDetails){
        List<Order> orderList = orderRepository.findOrdersByUserId(userDetails.getUser().getId());
        List<OrderResponseDto> ResponseList= new ArrayList<OrderResponseDto>();
        for(Order order:orderList){
            OrderResponseDto orderResponseDto = new OrderResponseDto(order.getId(),order.getAddress(),order.getState().toString());
            ResponseList.add(orderResponseDto);
        }
        return ResponseList;
    }

    public void checkBasket(Map<Long,Long> basket) throws Exception {
        for(Long key:basket.keySet()){
            if(!checkStock(key,basket.get(key))){throw new Exception("id:"+key+" 수량부족");}
        }
    }

    public Order getOrder(Long orderId){
        return orderRepository.getById(orderId);
    }

    public Long getTotalPrice(Long orderId){
        List<OrderDetail> ListofOrderDetail = orderDetailRepository.findOrderDetailsByOrderId(orderId);
        Long totalPrice=0L;
        for(OrderDetail orderDetail:ListofOrderDetail){
            totalPrice+=orderDetail.getPrice()*orderDetail.getQuantity();
        }
        return totalPrice;
    }




}
