package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.Role;
import csit321g10.b1t1.shimmerandshield.entity.UserEntity;

/** Never exposes the password. */
public record UserResponse(Long userId, String fullName, String email, Role role) {

    public static UserResponse from(UserEntity user) {
        return new UserResponse(user.getUserId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
