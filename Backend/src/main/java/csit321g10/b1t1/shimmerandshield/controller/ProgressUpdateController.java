package csit321g10.b1t1.shimmerandshield.controller;

import csit321g10.b1t1.shimmerandshield.dto.ProgressUpdateCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.ProgressUpdateResponse;
import csit321g10.b1t1.shimmerandshield.dto.ProgressUpdateUpdateRequest;
import csit321g10.b1t1.shimmerandshield.service.ProgressUpdateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/progress-updates")
public class ProgressUpdateController {

    private final ProgressUpdateService progressUpdateService;

    public ProgressUpdateController(ProgressUpdateService progressUpdateService) {
        this.progressUpdateService = progressUpdateService;
    }

    // POST /api/progress-updates
    @PostMapping
    public ResponseEntity<ProgressUpdateResponse> create(@Valid @RequestBody ProgressUpdateCreateRequest request) {
        ProgressUpdateResponse created = progressUpdateService.create(request);
        return ResponseEntity.created(URI.create("/api/progress-updates/" + created.updateId())).body(created);
    }

    // GET /api/progress-updates  or  GET /api/progress-updates?bookingId=1
    @GetMapping
    public List<ProgressUpdateResponse> getAll(@RequestParam(required = false) Long bookingId) {
        return progressUpdateService.findAll(bookingId);
    }

    // GET /api/progress-updates/{id}
    @GetMapping("/{id}")
    public ProgressUpdateResponse getById(@PathVariable Long id) {
        return progressUpdateService.findById(id);
    }

    // PUT /api/progress-updates/{id}
    @PutMapping("/{id}")
    public ProgressUpdateResponse update(@PathVariable Long id, @Valid @RequestBody ProgressUpdateUpdateRequest request) {
        return progressUpdateService.update(id, request);
    }

    // DELETE /api/progress-updates/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        progressUpdateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
