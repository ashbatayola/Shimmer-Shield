package csit321g10.b1t1.shimmerandshield.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "damage_records")
public class DamageRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "damage_id")
    private Long damageId;

    // Plain FK column for now. Switch to @ManyToOne BookingEntity once BOOKING exists.
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "damage_photo", length = 500)
    private String damagePhoto; // photo URL or file path

    @Column(name = "damage_label", nullable = false)
    private String damageLabel;

    @Column(length = 1000)
    private String description;

    public DamageRecordEntity() {
    }

    public DamageRecordEntity(Long bookingId, String damagePhoto, String damageLabel, String description) {
        this.bookingId = bookingId;
        this.damagePhoto = damagePhoto;
        this.damageLabel = damageLabel;
        this.description = description;
    }

    public Long getDamageId() { return damageId; }
    public void setDamageId(Long damageId) { this.damageId = damageId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getDamagePhoto() { return damagePhoto; }
    public void setDamagePhoto(String damagePhoto) { this.damagePhoto = damagePhoto; }

    public String getDamageLabel() { return damageLabel; }
    public void setDamageLabel(String damageLabel) { this.damageLabel = damageLabel; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
