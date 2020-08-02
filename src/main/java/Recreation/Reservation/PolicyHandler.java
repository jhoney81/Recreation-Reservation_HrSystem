package Recreation.Reservation;

import Recreation.Reservation.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    HrSystemRepository hrSystemRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRoomOrdered_OrderRequest(@Payload RoomOrdered roomOrdered){

        if(roomOrdered.isMe()){
            HrSystem hrSystem = new HrSystem();

            hrSystem.setOrderId(roomOrdered.getId());
            hrSystem.setId(roomOrdered.getId());
            hrSystem.setStatus("ROOMORDED");
            hrSystem.setAgentName("Engineer" + roomOrdered.getId());
            hrSystem.setAgentId(roomOrdered.getId() + 100);

            hrSystemRepository.save(hrSystem);

            System.out.println("##### listener OrderRequest : " + roomOrdered.toJson());

        }

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCompleted_ReservationCompleteNotify(@Payload ReservationCompleted reservationCompleted){

        if(reservationCompleted.isMe()) {
            System.out.println("ORDERID" + reservationCompleted.getOrderId());
            System.out.println("GETID" + reservationCompleted.getId());
            try
            {
                System.out.println("##### listener ReservationCompleteNotify : " + reservationCompleted.toJson());
                hrSystemRepository.findById(reservationCompleted.getOrderId())
                        .ifPresent(
                                managementCenter -> {
                                    managementCenter.setStatus(reservationCompleted.getStatus());
                                    hrSystemRepository.save(managementCenter);
                                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelOrdered_CancelRequest(@Payload CancelOrdered cancelOrdered){

        if(cancelOrdered.isMe()){
            try {
                System.out.println("##### listener CancelRequest : " + cancelOrdered.toJson());

                Optional<HrSystem> hrSystem = hrSystemRepository.findByOrderId(cancelOrdered.getId());
                hrSystem.get().setStatus("CANCELREQUESTED");
                hrSystemRepository.save(hrSystem.get());

                System.out.println("######  test10");

            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
