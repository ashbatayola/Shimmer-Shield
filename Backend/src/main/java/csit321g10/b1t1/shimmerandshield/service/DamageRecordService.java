package csit321g10.b1t1.shimmerandshield.service;

import csit321g10.b1t1.shimmerandshield.dto.DamageRecordCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.DamageRecordResponse;
import csit321g10.b1t1.shimmerandshield.dto.DamageRecordUpdateRequest;
import csit321g10.b1t1.shimmerandshield.entity.DamageRecordEntity;
import csit321g10.b1t1.shimmerandshield.exception.ResourceNotFoundException;
import csit321g10.b1t1.shimmerandshield.repository.DamageRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DamageRecordService {

    private final DamageRecordRepository damageRecordRepository;

    public DamageRecordService(DamageRecordRepository damageRecordRepository) {
        this.damageRecordRepository = damageRecordRepository;
    }

    // CREATE
    public DamageRecordResponse create(DamageRecordCreateRequest request) {
        DamageRecordEntity record = new DamageRecordEntity(
                request.bookingId(),
                clean(request.damagePhoto()),
                request.damageLabel().trim(),
                clean(request.description()));
        return DamageRecordResponse.from(damageRecordRepository.save(record));
    }

    // READ (all, optionally filtered by booking)
    @Transactional(readOnly = true)
    public List<DamageRecordResponse> findAll(Long bookingId) {
        List<DamageRecordEntity> records = (bookingId == null)
                ? damageRecordRepository.findAll()
                : damageRecordRepository.findByBookingId(bookingId);
        return records.stream().map(DamageRecordResponse::from).toList();
    }

    // READ (one)
    @Transactional(readOnly = true)
    public DamageRecordResponse findById(Long id) {
        return DamageRecordResponse.from(getOrThrow(id));
    }

    // UPDATE
    public DamageRecordResponse update(Long id, DamageRecordUpdateRequest request) {
        DamageRecordEntity record = getOrThrow(id);
        record.setBookingId(request.bookingId());
        record.setDamagePhoto(clean(request.damagePhoto()));
        record.setDamageLabel(request.damageLabel().trim());
        record.setDescription(clean(request.description()));
        return DamageRecordResponse.from(damageRecordRepository.save(record));
    }

    // DELETE
    public void delete(Long id) {
        if (!damageRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Damage record not found with id " + id);
        }
        damageRecordRepository.deleteById(id);
    }

    private DamageRecordEntity getOrThrow(Long id) {
        return damageRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Damage record not found with id " + id));
    }

    private String clean(String text) {
        return (text == null || text.isBlank()) ? null : text.trim();
    }
}
