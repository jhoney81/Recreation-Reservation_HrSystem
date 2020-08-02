
package Recreation.Reservation.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="Reservation", url="http://localhost:8083")  //url="http://Reservation:8080")
@FeignClient(name="Reservation", url="http://Reservation:8080")
public interface ReservationService {

    @RequestMapping(method= RequestMethod.POST, path="/reservations")
    public void reservationCancellation(@RequestBody Reservation reservation);

}