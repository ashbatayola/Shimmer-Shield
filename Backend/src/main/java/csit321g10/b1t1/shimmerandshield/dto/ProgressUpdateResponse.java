package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.ProgressUpdateEntity;

import java.time.LocalDateTime;

public record ProgressUpdateResponse(Long updateId, Long bookingId, String stage, LocalDateTime updatedAt) {

    public static ProgressUpdateResponse from(ProgressUpdateEntity p) {
        return new ProgressUpdateResponse(p.getUpdateId(), p.getBookingId(), p.getStage(), p.getUpdatedAt());
    }
}
