package csit321g10.b1t1.shimmerandshield.service;

import csit321g10.b1t1.shimmerandshield.dto.ProgressUpdateCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.ProgressUpdateResponse;
import csit321g10.b1t1.shimmerandshield.dto.ProgressUpdateUpdateRequest;
import csit321g10.b1t1.shimmerandshield.entity.ProgressUpdateEntity;
import csit321g10.b1t1.shimmerandshield.exception.ResourceNotFoundException;
import csit321g10.b1t1.shimmerandshield.repository.ProgressUpdateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProgressUpdateService {

    private final ProgressUpdateRepository progressUpdateRepository;

    public ProgressUpdateService(ProgressUpdateRepository progressUpdateRepository) {
        this.progressUpdateRepository = progressUpdateRepository;
    }

    // CREATE
    public ProgressUpdateResponse create(ProgressUpdateCreateRequest request) {
        ProgressUpdateEntity update = new ProgressUpdateEntity(request.bookingId(), request.stage().trim());
        return ProgressUpdateResponse.from(progressUpdateRepository.save(update));
    }

    // READ (all, optionally filtered by booking)
    @Transactional(readOnly = true)
    public List<ProgressUpdateResponse> findAll(Long bookingId) {
        List<ProgressUpdateEntity> updates = (bookingId == null)
                ? progressUpdateRepository.findAll()
                : progressUpdateRepository.findByBookingIdOrderByUpdatedAtAsc(bookingId);
        return updates.stream().map(ProgressUpdateResponse::from).toList();
    }

    // READ (one)
    @Transactional(readOnly = true)
    public ProgressUpdateResponse findById(Long id) {
        return ProgressUpdateResponse.from(getOrThrow(id));
    }

    // UPDATE
    public ProgressUpdateResponse update(Long id, ProgressUpdateUpdateRequest request) {
        ProgressUpdateEntity update = getOrThrow(id);
        update.setBookingId(request.bookingId());
        update.setStage(request.stage().trim());
        return ProgressUpdateResponse.from(progressUpdateRepository.saveAndFlush(update));
    }

    // DELETE
    public void delete(Long id) {
        if (!progressUpdateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Progress update not found with id " + id);
        }
        progressUpdateRepository.deleteById(id);
    }

    private ProgressUpdateEntity getOrThrow(Long id) {
        return progressUpdateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress update not found with id " + id));
    }
}
