package csit321g10.b1t1.shimmerandshield.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Used for both create (POST) and update (PUT). */
public record DailySlotLimitRequest(
        @NotNull(message = "Staff id is required") Long staffId,
        @NotNull(message = "Max slots is required")
        @Min(value = 1, message = "Max slots must be at least 1") Integer maxSlots
) {
}
