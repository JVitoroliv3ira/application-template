package api.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public record UserAuthenticationRequestDTO(
        @NotBlank(message = "Informe o seu email.")
        @Size(min = 5, max = 80, message = "O email informado é inválido.")
        @Email(message = "O email informado é inválido.")
        String email,
        @NotBlank(message = "Informe a sua senha.")
        @Size(min = 6, message = "A senha informada é inválida.")
        String password
) {
    public Authentication convert() {
        return new UsernamePasswordAuthenticationToken(
                email(),
                password()
        );
    }
}
