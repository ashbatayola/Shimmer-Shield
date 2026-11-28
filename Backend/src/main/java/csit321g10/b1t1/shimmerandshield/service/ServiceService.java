package csit321g10.b1t1.shimmerandshield.service;

import csit321g10.b1t1.shimmerandshield.dto.ServiceCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.ServiceResponse;
import csit321g10.b1t1.shimmerandshield.dto.ServiceUpdateRequest;
import csit321g10.b1t1.shimmerandshield.entity.ServiceEntity;
import csit321g10.b1t1.shimmerandshield.exception.DuplicateServiceNameException;
import csit321g10.b1t1.shimmerandshield.exception.ResourceNotFoundException;
import csit321g10.b1t1.shimmerandshield.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    // CREATE
    public ServiceResponse create(ServiceCreateRequest request) {
        String name = request.serviceName().trim();
        if (serviceRepository.existsByServiceNameIgnoreCase(name)) {
            throw new DuplicateServiceNameException(name);
        }
        ServiceEntity service = new ServiceEntity(name, clean(request.description()), request.price());
        return ServiceResponse.from(serviceRepository.save(service));
    }

    // READ (all, optionally searched by name)
    @Transactional(readOnly = true)
    public List<ServiceResponse> findAll(String search) {
        List<ServiceEntity> services = (search == null || search.isBlank())
                ? serviceRepository.findAll()
                : serviceRepository.findByServiceNameContainingIgnoreCase(search.trim());
        return services.stream().map(ServiceResponse::from).toList();
    }

    // READ (one)
    @Transactional(readOnly = true)
    public ServiceResponse findById(Long id) {
        return ServiceResponse.from(getOrThrow(id));
    }

    // UPDATE
    public ServiceResponse update(Long id, ServiceUpdateRequest request) {
        ServiceEntity service = getOrThrow(id);
        String name = request.serviceName().trim();
        if (serviceRepository.existsByServiceNameIgnoreCaseAndServiceIdNot(name, id)) {
            throw new DuplicateServiceNameException(name);
        }
        service.setServiceName(name);
        service.setDescription(clean(request.description()));
        service.setPrice(request.price());
        return ServiceResponse.from(serviceRepository.save(service));
    }

    // DELETE
    public void delete(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service not found with id " + id);
        }
        serviceRepository.deleteById(id);
    }

    private ServiceEntity getOrThrow(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + id));
    }

    private String clean(String text) {
        return (text == null || text.isBlank()) ? null : text.trim();
    }
}
