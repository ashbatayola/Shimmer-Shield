package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.DailySlotLimitEntity;

public record DailySlotLimitResponse(Long limitId, Long staffId, String staffName, Integer maxSlots) {

    public static DailySlotLimitResponse from(DailySlotLimitEntity limit) {
        return new DailySlotLimitResponse(
                limit.getLimitId(),
                limit.getStaff().getUserId(),
                limit.getStaff().getFullName(),
                limit.getMaxSlots());
    }
}
