package group.microserviceorder.order.entity;


import group.microserviceorder.global.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String address;

    @Column
    private String KakaoTid;

    @Enumerated(value = EnumType.STRING)
    private OrderState state;

    @Builder
    public Order(Long userId,String address,OrderState state){
        this.userId = userId;
        this.address = address;
        this.state = state;
    }
    public void updateTid(String tid){
        this.KakaoTid=tid;
    }
    public void changeState(OrderState state){this.state = state;}


}
