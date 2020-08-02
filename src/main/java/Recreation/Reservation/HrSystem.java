package Recreation.Reservation;

import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name="HrSystem_table")
public class HrSystem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Long agentId;
    private String agentName;
    private String status;

    @PostPersist
    public void onPostPersist(){

        if("ROOMORDED".equals(this.getStatus())) {

            OrderAccepted orderAccepted = new OrderAccepted();

            orderAccepted.setId(this.getId());
            orderAccepted.setOrderId(this.getId());
            orderAccepted.setStatus(this.getStatus());
            orderAccepted.setAgentId(this.getAgentId());
            orderAccepted.setAgentName(this.getAgentName());

            BeanUtils.copyProperties(this, orderAccepted);
            orderAccepted.publishAfterCommit();
        }

   }

    @PostUpdate
    public void onPostUpdate(){

        if (this.getStatus().equals("RESERVATIONCOMPLETED")) {

            System.out.println("11111111" + this.getStatus());

            RoomCompleted roomCompleted = new RoomCompleted();

            roomCompleted.setAgentId(this.getAgentId());
            roomCompleted.setAgentName(this.getAgentName());
            roomCompleted.setId(this.getId());
            roomCompleted.setOrderId(this.orderId);
            roomCompleted.setStatus(this.getStatus());

            BeanUtils.copyProperties(this, roomCompleted);
            roomCompleted.publishAfterCommit();

        }else  if (this.getStatus().equals("CANCELREQUESTED")){

            System.out.println("TEST1");

            Recreation.Reservation.external.Reservation reservation = new Recreation.Reservation.external.Reservation();

            reservation.setOrderId(this.getOrderId());
            HrSystemApplication.applicationContext.getBean(Recreation.Reservation.external.ReservationService.class)
                    .reservationCancellation(reservation);

            OrderCancelAccepted orderCancelAccepted = new OrderCancelAccepted();

            orderCancelAccepted.setId(this.getId());
            orderCancelAccepted.setOrderId(this.getId());
            orderCancelAccepted.setStatus("ORDERCANCELED");
            orderCancelAccepted.setAgentId(this.getAgentId());
            orderCancelAccepted.setAgentName(this.getAgentName());

            BeanUtils.copyProperties(this, orderCancelAccepted);
            orderCancelAccepted.publishAfterCommit();

        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
