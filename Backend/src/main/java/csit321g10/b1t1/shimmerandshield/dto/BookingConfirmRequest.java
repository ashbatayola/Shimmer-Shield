package csit321g10.b1t1.shimmerandshield.dto;

import jakarta.validation.constraints.NotNull;

public record BookingConfirmRequest(
        @NotNull(message = "Staff id is required") Long staffId
) {
}
