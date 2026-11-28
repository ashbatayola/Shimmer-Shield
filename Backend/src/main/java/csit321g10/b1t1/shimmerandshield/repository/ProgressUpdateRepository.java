package csit321g10.b1t1.shimmerandshield.repository;

import csit321g10.b1t1.shimmerandshield.entity.ProgressUpdateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressUpdateRepository extends JpaRepository<ProgressUpdateEntity, Long> {

    List<ProgressUpdateEntity> findByBookingIdOrderByUpdatedAtAsc(Long bookingId);
}
