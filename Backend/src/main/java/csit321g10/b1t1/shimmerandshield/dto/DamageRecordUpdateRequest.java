package csit321g10.b1t1.shimmerandshield.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DamageRecordUpdateRequest(
        @NotNull(message = "Booking id is required") Long bookingId,
        @Size(max = 500, message = "Damage photo must be at most 500 characters") String damagePhoto,
        @NotBlank(message = "Damage label is required") String damageLabel,
        @Size(max = 1000, message = "Description must be at most 1000 characters") String description
) {
}
