package csit321g10.b1t1.shimmerandshield.dto;

import csit321g10.b1t1.shimmerandshield.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Password is optional on update: leave it out (or null) to keep the current one. */
public record UserUpdateRequest(
        @NotBlank(message = "Full name is required") String fullName,
        @NotBlank(message = "Email is required") @Email(message = "Email is not valid") String email,
        @Size(min = 8, message = "Password must be at least 8 characters") String password,
        @NotNull(message = "Role is required (CUSTOMER or STAFF)") Role role
) {
}
