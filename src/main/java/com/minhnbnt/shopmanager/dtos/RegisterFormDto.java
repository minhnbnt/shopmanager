package com.minhnbnt.shopmanager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterFormDto {

    @NotBlank
    @Size(min = 8, max = 50, message = "Username's length must be between 8 and 50.")
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String password;
}
