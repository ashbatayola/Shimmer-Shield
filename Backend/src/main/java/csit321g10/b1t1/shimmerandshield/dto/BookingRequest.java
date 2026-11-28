package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.BookingStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Used for both create (POST) and update (PUT).
 * - staffId: optional (a booking has no staff until it is confirmed)
 * - estimateTotal: optional (defaults to the service price)
 * - status: optional (defaults to PENDING)
 */
public record BookingRequest(
        @NotNull(message = "Customer id is required") Long customerId,
        Long staffId,
        @NotNull(message = "Service id is required") Long serviceId,
        @NotBlank(message = "Vehicle type is required") String vehicleType,
        @NotNull(message = "Booking date is required (yyyy-MM-dd)") LocalDate bookingDate,
        @NotBlank(message = "Time slot is required") String timeSlot,
        @DecimalMin(value = "0.0", message = "Estimate total cannot be negative") BigDecimal estimateTotal,
        BookingStatus status
) {
}
