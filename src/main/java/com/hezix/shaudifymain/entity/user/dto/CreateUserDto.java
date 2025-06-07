package com.hezix.shaudifymain.entity.user.dto;

import com.hezix.shaudifymain.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto  implements Serializable {
    @Size(min = 4, max = 32, message = "поле username должно быть от 4 до 32 символов")
    @NotBlank(message = "поле username не должно быть пустым")
    private String username;
    @Size(min = 8, max = 32, message = "поле password должно быть от 8 до 32 символов")
    @NotBlank(message = "поле password не должно быть пустым")
    private String password;
    @Size(min = 8, max = 32, message = "поле passwordConfirm должно быть от 8 до 32 символов")
    @NotBlank(message = "поле passwordConfirm не должно быть пустым")
    private String passwordConfirm;
    @NotBlank(message = "поле email не должно быть пустым")
    @Email(message = "поле email должно иметь формат почты")
    private String email;
    @NotBlank(message = "поле firstName не должно быть пустым")
    @Size(min = 2, max = 32, message = "поле firstName должно быть от 2 до 32 символов")
    private String firstName;
    @NotBlank(message = "поле lastName не должно быть пустым")
    @Size(min = 2, max = 32, message = "поле lastName должно быть от 2 до 32 символов")
    private String lastName;
}
