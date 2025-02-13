package com.devteria.identity_service.dto.request;

import com.devteria.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;
    @Size(min = 6, message = "PASSWORD_TOO_SHORT")
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min=18,message = "INVALID_DOB")
    LocalDate birthDay;
}
