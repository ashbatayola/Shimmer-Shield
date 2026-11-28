package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.DamageRecordEntity;

public record DamageRecordResponse(Long damageId, Long bookingId, String damagePhoto,
                                   String damageLabel, String description) {

    public static DamageRecordResponse from(DamageRecordEntity d) {
        return new DamageRecordResponse(d.getDamageId(), d.getBookingId(), d.getDamagePhoto(),
                d.getDamageLabel(), d.getDescription());
    }
}
