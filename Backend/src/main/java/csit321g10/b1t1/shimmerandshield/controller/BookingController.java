package csit321g10.b1t1.shimmerandshield.controller;

import csit321g10.b1t1.shimmerandshield.dto.BookingConfirmRequest;
import csit321g10.b1t1.shimmerandshield.dto.BookingRequest;
import csit321g10.b1t1.shimmerandshield.dto.BookingResponse;
import csit321g10.b1t1.shimmerandshield.entity.BookingStatus;
import csit321g10.b1t1.shimmerandshield.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // POST /api/bookings
    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest request) {
        BookingResponse created = bookingService.create(request);
        return ResponseEntity.created(URI.create("/api/bookings/" + created.bookingId())).body(created);
    }

    // GET /api/bookings   (optional filters: ?customerId=1 &staffId=2 &status=PENDING)
    @GetMapping
    public List<BookingResponse> getAll(@RequestParam(required = false) Long customerId,
                                        @RequestParam(required = false) Long staffId,
                                        @RequestParam(required = false) BookingStatus status) {
        return bookingService.findAll(customerId, staffId, status);
    }

    // GET /api/bookings/{id}
    @GetMapping("/{id}")
    public BookingResponse getById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    // PUT /api/bookings/{id}
    @PutMapping("/{id}")
    public BookingResponse update(@PathVariable Long id, @Valid @RequestBody BookingRequest request) {
        return bookingService.update(id, request);
    }

    // PATCH /api/bookings/{id}/confirm   body: { "staffId": 2 }
    @PatchMapping("/{id}/confirm")
    public BookingResponse confirm(@PathVariable Long id, @Valid @RequestBody BookingConfirmRequest request) {
        return bookingService.confirm(id, request);
    }

    // DELETE /api/bookings/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
