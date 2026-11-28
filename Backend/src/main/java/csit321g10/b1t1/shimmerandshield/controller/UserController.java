package csit321g10.b1t1.shimmerandshield.controller;

import csit321g10.b1t1.shimmerandshield.dto.UserCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.UserResponse;
import csit321g10.b1t1.shimmerandshield.dto.UserUpdateRequest;
import csit321g10.b1t1.shimmerandshield.entity.Role;
import csit321g10.b1t1.shimmerandshield.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserResponse created = userService.create(request);
        return ResponseEntity.created(URI.create("/api/users/" + created.userId())).body(created);
    }

    // GET /api/users  or  GET /api/users?role=CUSTOMER  or  ?role=STAFF
    @GetMapping
    public List<UserResponse> getAll(@RequestParam(required = false) Role role) {
        return userService.findAll(role);
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.update(id, request);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
