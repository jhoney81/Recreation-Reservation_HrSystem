package Recreation.Reservation;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface HrSystemRepository extends PagingAndSortingRepository<HrSystem, Long>{

    Optional<HrSystem> findByOrderId(Long orderId);

}