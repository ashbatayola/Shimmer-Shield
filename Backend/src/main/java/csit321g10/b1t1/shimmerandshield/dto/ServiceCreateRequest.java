package csit321g10.b1t1.shimmerandshield.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ServiceCreateRequest(
        @NotBlank(message = "Service name is required") String serviceName,
        @Size(max = 1000, message = "Description must be at most 1000 characters") String description,
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", message = "Price cannot be negative")
        @Digits(integer = 8, fraction = 2, message = "Price must have at most 2 decimal places") BigDecimal price
) {
}
