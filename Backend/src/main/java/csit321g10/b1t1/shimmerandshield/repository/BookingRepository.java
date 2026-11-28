package csit321g10.b1t1.shimmerandshield.repository;

import csit321g10.b1t1.shimmerandshield.entity.BookingEntity;
import csit321g10.b1t1.shimmerandshield.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findByCustomer_UserId(Long customerId);

    List<BookingEntity> findByStaff_UserId(Long staffId);

    List<BookingEntity> findByStatus(BookingStatus status);
}
