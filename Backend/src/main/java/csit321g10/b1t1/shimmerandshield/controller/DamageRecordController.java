package csit321g10.b1t1.shimmerandshield.controller;

import csit321g10.b1t1.shimmerandshield.dto.DamageRecordCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.DamageRecordResponse;
import csit321g10.b1t1.shimmerandshield.dto.DamageRecordUpdateRequest;
import csit321g10.b1t1.shimmerandshield.service.DamageRecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/damage-records")
public class DamageRecordController {

    private final DamageRecordService damageRecordService;

    public DamageRecordController(DamageRecordService damageRecordService) {
        this.damageRecordService = damageRecordService;
    }

    // POST /api/damage-records
    @PostMapping
    public ResponseEntity<DamageRecordResponse> create(@Valid @RequestBody DamageRecordCreateRequest request) {
        DamageRecordResponse created = damageRecordService.create(request);
        return ResponseEntity.created(URI.create("/api/damage-records/" + created.damageId())).body(created);
    }

    // GET /api/damage-records  or  GET /api/damage-records?bookingId=1
    @GetMapping
    public List<DamageRecordResponse> getAll(@RequestParam(required = false) Long bookingId) {
        return damageRecordService.findAll(bookingId);
    }

    // GET /api/damage-records/{id}
    @GetMapping("/{id}")
    public DamageRecordResponse getById(@PathVariable Long id) {
        return damageRecordService.findById(id);
    }

    // PUT /api/damage-records/{id}
    @PutMapping("/{id}")
    public DamageRecordResponse update(@PathVariable Long id, @Valid @RequestBody DamageRecordUpdateRequest request) {
        return damageRecordService.update(id, request);
    }

    // DELETE /api/damage-records/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        damageRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
