package com.rapitskyi.carsharing.dto.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email must be not null")
    private String email;
    @NotBlank(message = "Password must be not null")
    private String password;
    @NotBlank(message = "Firstname must be not null")
    private String firstName;
    @NotBlank(message = "Lastname must be not null")
    private String lastName;
    @Pattern(regexp = "^\\+380\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "Phone must be not null")
    private String phone;
}
