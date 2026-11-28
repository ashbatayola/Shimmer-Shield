package csit321g10.b1t1.shimmerandshield.controller;

import csit321g10.b1t1.shimmerandshield.dto.DailySlotLimitRequest;
import csit321g10.b1t1.shimmerandshield.dto.DailySlotLimitResponse;
import csit321g10.b1t1.shimmerandshield.service.DailySlotLimitService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/daily-slot-limits")
public class DailySlotLimitController {

    private final DailySlotLimitService service;

    public DailySlotLimitController(DailySlotLimitService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DailySlotLimitResponse> create(@Valid @RequestBody DailySlotLimitRequest request) {
        DailySlotLimitResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/daily-slot-limits/" + created.limitId())).body(created);
    }

    
    @GetMapping
    public List<DailySlotLimitResponse> getAll(@RequestParam(required = false) Long staffId) {
        return service.findAll(staffId);
    }

    @GetMapping("/{id}")
    public DailySlotLimitResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public DailySlotLimitResponse update(@PathVariable Long id, @Valid @RequestBody DailySlotLimitRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
