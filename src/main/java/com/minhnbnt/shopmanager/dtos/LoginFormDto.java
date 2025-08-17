package com.minhnbnt.shopmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginFormDto {

    @NotBlank(message = "Username is required")
    @Size(min = 8, max = 50, message = "Username's length must be between 8 and 50.")
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;
}
