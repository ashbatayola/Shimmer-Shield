package csit321g10.b1t1.shimmerandshield.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "daily_slot_limit")
public class DailySlotLimitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "limit_id")
    private Long limitId;

    // The staff member who sets this limit (FK -> users.user_id)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private UserEntity staff;

    @Column(name = "max_slots", nullable = false)
    private Integer maxSlots;

    public DailySlotLimitEntity() {
    }

    public DailySlotLimitEntity(UserEntity staff, Integer maxSlots) {
        this.staff = staff;
        this.maxSlots = maxSlots;
    }

    public Long getLimitId() { return limitId; }
    public void setLimitId(Long limitId) { this.limitId = limitId; }

    public UserEntity getStaff() { return staff; }
    public void setStaff(UserEntity staff) { this.staff = staff; }

    public Integer getMaxSlots() { return maxSlots; }
    public void setMaxSlots(Integer maxSlots) { this.maxSlots = maxSlots; }
}
