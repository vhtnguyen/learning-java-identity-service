package com.devteria.identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserPutRequest {
    @NotNull(message = "FIELD_REQUIRED")
    String password;
    @NotNull(message = "FIELD_REQUIRED")
    String firstName;
    @NotNull(message = "FIELD_REQUIRED")
    String lastName;
    @NotNull(message = "FIELD_REQUIRED")
    LocalDate birthDay;
}
