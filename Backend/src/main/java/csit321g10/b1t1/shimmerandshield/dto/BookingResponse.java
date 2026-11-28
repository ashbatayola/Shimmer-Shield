package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.BookingEntity;
import csit321g10.b1t1.shimmerandshield.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(
        Long bookingId,
        Long customerId,
        String customerName,
        Long staffId,
        String staffName,
        Long serviceId,
        String serviceName,
        String vehicleType,
        LocalDate bookingDate,
        String timeSlot,
        BigDecimal estimateTotal,
        BookingStatus status
) {

    public static BookingResponse from(BookingEntity b) {
        return new BookingResponse(
                b.getBookingId(),
                b.getCustomer().getUserId(),
                b.getCustomer().getFullName(),
                b.getStaff() == null ? null : b.getStaff().getUserId(),
                b.getStaff() == null ? null : b.getStaff().getFullName(),
                b.getService().getServiceId(),
                b.getService().getServiceName(),
                b.getVehicleType(),
                b.getBookingDate(),
                b.getTimeSlot(),
                b.getEstimateTotal(),
                b.getStatus());
    }
}
