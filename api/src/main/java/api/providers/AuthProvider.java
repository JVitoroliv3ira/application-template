package api.providers;

import api.exceptions.ApiException;
import api.models.Details;
import api.services.DetailsService;
import api.utils.BcryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthProvider implements AuthenticationProvider {

    private final DetailsService detailsService;
    private final BcryptUtil bcryptUtil;

    private static final String ERROR_INVALID_CREDENTIALS = "Credenciais inválidas";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Details details = this.detailsService.loadUserByUsername(email);
        this.validateUserPassword(password, details.getPassword());

        return new UsernamePasswordAuthenticationToken(
                details.getUsername(),
                details.getPassword(),
                details.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void validateUserPassword(String rawPassword, String encodedPassword) {
        if (Boolean.FALSE.equals(this.bcryptUtil.matches(rawPassword, encodedPassword))) {
            throw new ApiException(
                    ERROR_INVALID_CREDENTIALS,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
