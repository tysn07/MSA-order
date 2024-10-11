package group.microserviceorder.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderResponseDto {

    private Long orderId;
    private String address;
    private String state;
}
