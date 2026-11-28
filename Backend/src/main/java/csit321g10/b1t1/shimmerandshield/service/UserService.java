package csit321g10.b1t1.shimmerandshield.service;

import csit321g10.b1t1.shimmerandshield.dto.UserCreateRequest;
import csit321g10.b1t1.shimmerandshield.dto.UserResponse;
import csit321g10.b1t1.shimmerandshield.dto.UserUpdateRequest;
import csit321g10.b1t1.shimmerandshield.entity.Role;
import csit321g10.b1t1.shimmerandshield.entity.UserEntity;
import csit321g10.b1t1.shimmerandshield.exception.DuplicateEmailException;
import csit321g10.b1t1.shimmerandshield.exception.ResourceNotFoundException;
import csit321g10.b1t1.shimmerandshield.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public UserResponse create(UserCreateRequest request) {
        String email = normalize(request.email());
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
        UserEntity user = new UserEntity(
                request.fullName().trim(),
                email,
                passwordEncoder.encode(request.password()),
                request.role());
        return UserResponse.from(userRepository.save(user));
    }

    // READ (all, optionally filtered by role)
    @Transactional(readOnly = true)
    public List<UserResponse> findAll(Role role) {
        List<UserEntity> users = (role == null) ? userRepository.findAll() : userRepository.findByRole(role);
        return users.stream().map(UserResponse::from).toList();
    }

    // READ (one)
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return UserResponse.from(getOrThrow(id));
    }

    // UPDATE
    public UserResponse update(Long id, UserUpdateRequest request) {
        UserEntity user = getOrThrow(id);
        String email = normalize(request.email());
        if (userRepository.existsByEmailAndUserIdNot(email, id)) {
            throw new DuplicateEmailException(email);
        }
        user.setFullName(request.fullName().trim());
        user.setEmail(email);
        user.setRole(request.role());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        return UserResponse.from(userRepository.save(user));
    }

    // DELETE
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    private UserEntity getOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    private String normalize(String email) {
        return email.trim().toLowerCase();
    }
}
