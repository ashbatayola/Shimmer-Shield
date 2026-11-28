package csit321g10.b1t1.shimmerandshield.controller;

import csit321g10.b1t1.shimmerandshield.dto.ServiceCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.ServiceResponse;
import csit321g10.b1t1.shimmerandshield.dto.ServiceUpdateRequest;
import csit321g10.b1t1.shimmerandshield.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // POST /api/services
    @PostMapping
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody ServiceCreateRequest request) {
        ServiceResponse created = serviceService.create(request);
        return ResponseEntity.created(URI.create("/api/services/" + created.serviceId())).body(created);
    }

    // GET /api/services  or  GET /api/services?search=wax
    @GetMapping
    public List<ServiceResponse> getAll(@RequestParam(required = false) String search) {
        return serviceService.findAll(search);
    }

    // GET /api/services/{id}
    @GetMapping("/{id}")
    public ServiceResponse getById(@PathVariable Long id) {
        return serviceService.findById(id);
    }

    // PUT /api/services/{id}
    @PutMapping("/{id}")
    public ServiceResponse update(@PathVariable Long id, @Valid @RequestBody ServiceUpdateRequest request) {
        return serviceService.update(id, request);
    }

    // DELETE /api/services/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
