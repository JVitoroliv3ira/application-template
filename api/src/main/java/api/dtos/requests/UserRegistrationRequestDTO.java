package api.dtos.requests;

import api.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDTO(
        @NotBlank(message = "Informe o seu nome.")
        @Size(min = 5, max = 60, message = "O nome deve conter entre {min} e {max} caracteres.")
        String name,
        @NotBlank(message = "Informe o seu email.")
        @Size(min = 5, max = 80, message = "O email deve conter entre {min} e {max} caracteres.")
        @Email(message = "O email informado é inválido.")
        String email,
        @NotBlank(message = "Informe a sua senha.")
        @Size(min = 6, message = "A senha deve conter no mínimo {min} caracteres.")
        String password
) {
    public User convert() {
        return User
                .builder()
                .name(name())
                .email(email())
                .password(password())
                .build();
    }
}
