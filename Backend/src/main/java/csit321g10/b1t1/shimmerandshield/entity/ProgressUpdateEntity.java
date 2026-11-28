package csit321g10.b1t1.shimmerandshield.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "progress_updates")
public class ProgressUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "update_id")
    private Long updateId;

    // Plain FK column for now. Switch to @ManyToOne BookingEntity once BOOKING exists.
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private String stage;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ProgressUpdateEntity() {
    }

    public ProgressUpdateEntity(Long bookingId, String stage) {
        this.bookingId = bookingId;
        this.stage = stage;
    }

    // updated_at is set automatically on insert and on every update
    @PrePersist
    @PreUpdate
    void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getUpdateId() { return updateId; }
    public void setUpdateId(Long updateId) { this.updateId = updateId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
