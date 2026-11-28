package csit321g10.b1t1.shimmerandshield.service;

import csit321g10.b1t1.shimmerandshield.dto.BookingConfirmRequest;
import csit321g10.b1t1.shimmerandshield.dto.BookingRequest;
import csit321g10.b1t1.shimmerandshield.dto.BookingResponse;
import csit321g10.b1t1.shimmerandshield.entity.BookingEntity;
import csit321g10.b1t1.shimmerandshield.entity.BookingStatus;
import csit321g10.b1t1.shimmerandshield.entity.Role;
import csit321g10.b1t1.shimmerandshield.entity.ServiceEntity;
import csit321g10.b1t1.shimmerandshield.entity.UserEntity;
import csit321g10.b1t1.shimmerandshield.exception.BadRequestException;
import csit321g10.b1t1.shimmerandshield.exception.ResourceNotFoundException;
import csit321g10.b1t1.shimmerandshield.repository.BookingRepository;
import csit321g10.b1t1.shimmerandshield.repository.ServiceRepository;
import csit321g10.b1t1.shimmerandshield.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    // CREATE
    public BookingResponse create(BookingRequest request) {
        if (request.bookingDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Booking date cannot be in the past");
        }
        BookingEntity booking = new BookingEntity();
        apply(booking, request);
        return BookingResponse.from(bookingRepository.save(booking));
    }

    // READ (all, optionally filtered by customer, staff and/or status)
    @Transactional(readOnly = true)
    public List<BookingResponse> findAll(Long customerId, Long staffId, BookingStatus status) {
        List<BookingEntity> bookings;
        if (customerId != null) {
            bookings = bookingRepository.findByCustomer_UserId(customerId);
        } else if (staffId != null) {
            bookings = bookingRepository.findByStaff_UserId(staffId);
        } else if (status != null) {
            bookings = bookingRepository.findByStatus(status);
        } else {
            bookings = bookingRepository.findAll();
        }
        return bookings.stream()
                .filter(b -> staffId == null || (b.getStaff() != null && Objects.equals(b.getStaff().getUserId(), staffId)))
                .filter(b -> status == null || b.getStatus() == status)
                .map(BookingResponse::from)
                .toList();
    }

    // READ (one)
    @Transactional(readOnly = true)
    public BookingResponse findById(Long id) {
        return BookingResponse.from(getOrThrow(id));
    }

    // UPDATE
    public BookingResponse update(Long id, BookingRequest request) {
        BookingEntity booking = getOrThrow(id);
        apply(booking, request);
        return BookingResponse.from(bookingRepository.save(booking));
    }

    // CONFIRM (staff confirms a pending booking)
    public BookingResponse confirm(Long id, BookingConfirmRequest request) {
        BookingEntity booking = getOrThrow(id);
        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new BadRequestException("Booking " + id + " is already confirmed");
        }
        booking.setStaff(getStaffOrThrow(request.staffId()));
        booking.setStatus(BookingStatus.CONFIRMED);
        return BookingResponse.from(bookingRepository.save(booking));
    }

    // DELETE
    public void delete(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id " + id);
        }
        bookingRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private void apply(BookingEntity booking, BookingRequest request) {
        BookingStatus status = request.status() == null ? BookingStatus.PENDING : request.status();

        UserEntity staff = null;
        if (request.staffId() != null) {
            staff = getStaffOrThrow(request.staffId());
        } else if (status == BookingStatus.CONFIRMED) {
            throw new BadRequestException("A confirmed booking needs a staff id");
        }

        ServiceEntity service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + request.serviceId()));

        booking.setCustomer(getCustomerOrThrow(request.customerId()));
        booking.setStaff(staff);
        booking.setService(service);
        booking.setVehicleType(request.vehicleType().trim());
        booking.setBookingDate(request.bookingDate());
        booking.setTimeSlot(request.timeSlot().trim());
        booking.setEstimateTotal(request.estimateTotal() != null ? request.estimateTotal() : service.getPrice());
        booking.setStatus(status);
    }

    private BookingEntity getOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
    }

    private UserEntity getCustomerOrThrow(Long customerId) {
        UserEntity user = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + customerId));
        if (user.getRole() != Role.CUSTOMER) {
            throw new BadRequestException("User " + customerId + " is not a customer");
        }
        return user;
    }

    private UserEntity getStaffOrThrow(Long staffId) {
        UserEntity user = userRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + staffId));
        if (user.getRole() != Role.STAFF) {
            throw new BadRequestException("User " + staffId + " is not a staff member");
        }
        return user;
    }
}
