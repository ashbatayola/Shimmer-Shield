package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.ServiceEntity;

import java.math.BigDecimal;

public record ServiceResponse(Long serviceId, String serviceName, String description, BigDecimal price) {

    public static ServiceResponse from(ServiceEntity service) {
        return new ServiceResponse(service.getServiceId(), service.getServiceName(),
                service.getDescription(), service.getPrice());
    }
}
