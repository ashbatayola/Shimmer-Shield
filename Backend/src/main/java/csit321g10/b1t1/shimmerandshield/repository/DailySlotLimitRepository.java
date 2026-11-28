package csit321g10.b1t1.shimmerandshield.repository;

import csit321g10.b1t1.shimmerandshield.entity.DailySlotLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailySlotLimitRepository extends JpaRepository<DailySlotLimitEntity, Long> {

    List<DailySlotLimitEntity> findByStaff_UserId(Long staffId);
}
