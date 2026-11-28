package csit321g10.b1t1.shimmerandshield.repository;

import csit321g10.b1t1.shimmerandshield.entity.DamageRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageRecordRepository extends JpaRepository<DamageRecordEntity, Long> {

    List<DamageRecordEntity> findByBookingId(Long bookingId);
}
