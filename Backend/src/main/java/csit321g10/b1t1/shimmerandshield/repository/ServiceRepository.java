package csit321g10.b1t1.shimmerandshield.repository;

import csit321g10.b1t1.shimmerandshield.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    boolean existsByServiceNameIgnoreCase(String serviceName);

    boolean existsByServiceNameIgnoreCaseAndServiceIdNot(String serviceName, Long serviceId);

    List<ServiceEntity> findByServiceNameContainingIgnoreCase(String serviceName);
}
