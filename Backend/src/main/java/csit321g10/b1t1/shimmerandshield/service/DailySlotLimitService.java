package csit321g10.b1t1.shimmerandshield.service;

import csit321g10.b1t1.shimmerandshield.dto.DailySlotLimitRequest;
import csit321g10.b1t1.shimmerandshield.dto.DailySlotLimitResponse;
import csit321g10.b1t1.shimmerandshield.entity.DailySlotLimitEntity;
import csit321g10.b1t1.shimmerandshield.entity.Role;
import csit321g10.b1t1.shimmerandshield.entity.UserEntity;
import csit321g10.b1t1.shimmerandshield.exception.BadRequestException;
import csit321g10.b1t1.shimmerandshield.exception.ResourceNotFoundException;
import csit321g10.b1t1.shimmerandshield.repository.DailySlotLimitRepository;
import csit321g10.b1t1.shimmerandshield.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DailySlotLimitService {

    private final DailySlotLimitRepository limitRepository;
    private final UserRepository userRepository;

    public DailySlotLimitService(DailySlotLimitRepository limitRepository, UserRepository userRepository) {
        this.limitRepository = limitRepository;
        this.userRepository = userRepository;
    }

    // CREATE
    public DailySlotLimitResponse create(DailySlotLimitRequest request) {
        UserEntity staff = getStaffOrThrow(request.staffId());
        DailySlotLimitEntity limit = new DailySlotLimitEntity(staff, request.maxSlots());
        return DailySlotLimitResponse.from(limitRepository.save(limit));
    }

    // READ (all, optionally filtered by staff)
    @Transactional(readOnly = true)
    public List<DailySlotLimitResponse> findAll(Long staffId) {
        List<DailySlotLimitEntity> limits = (staffId == null)
                ? limitRepository.findAll()
                : limitRepository.findByStaff_UserId(staffId);
        return limits.stream().map(DailySlotLimitResponse::from).toList();
    }

    // READ (one)
    @Transactional(readOnly = true)
    public DailySlotLimitResponse findById(Long id) {
        return DailySlotLimitResponse.from(getOrThrow(id));
    }

    // UPDATE
    public DailySlotLimitResponse update(Long id, DailySlotLimitRequest request) {
        DailySlotLimitEntity limit = getOrThrow(id);
        limit.setStaff(getStaffOrThrow(request.staffId()));
        limit.setMaxSlots(request.maxSlots());
        return DailySlotLimitResponse.from(limitRepository.save(limit));
    }

    // DELETE
    public void delete(Long id) {
        if (!limitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Daily slot limit not found with id " + id);
        }
        limitRepository.deleteById(id);
    }

    private DailySlotLimitEntity getOrThrow(Long id) {
        return limitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daily slot limit not found with id " + id));
    }

    // Only users with role STAFF may set a limit
    private UserEntity getStaffOrThrow(Long staffId) {
        UserEntity user = userRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + staffId));
        if (user.getRole() != Role.STAFF) {
            throw new BadRequestException("User " + staffId + " is not a staff member");
        }
        return user;
    }
}
