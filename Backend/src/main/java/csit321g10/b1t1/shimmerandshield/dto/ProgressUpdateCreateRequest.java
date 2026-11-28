package csit321g10.b1t1.shimmerandshield.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProgressUpdateCreateRequest(
        @NotNull(message = "Booking id is required") Long bookingId,
        @NotBlank(message = "Stage is required") String stage
) {
}
