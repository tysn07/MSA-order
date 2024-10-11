package group.microserviceorder.order.dto;
import lombok.Getter;

import java.util.Map;
@Getter
public class OrderRequestDto {

    private Map<Long,Long> basket;
    private String address;

}
