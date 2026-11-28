package csit321g10.b1t1.shimmerandshield.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import csit321g10.b1t1.shimmerandshield.entity.DailySlotLimitEntity;

@Repository
public interface DailySlotLimitRepository extends JpaRepository<DailySlotLimitEntity, Long> {

    List<DailySlotLimitEntity> findByStaff_UserId(Long staffId);
}
