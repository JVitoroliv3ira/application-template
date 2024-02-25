package api.services;

import api.dtos.responses.AuthenticatedUserResponseDTO;
import api.providers.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthProvider authProvider;
    private final TokenService tokenService;

    public AuthenticatedUserResponseDTO authenticate(Authentication authentication) {
        Authentication result = this.authProvider.authenticate(authentication);
        String email = result.getPrincipal().toString();
        String token = this.tokenService.generateToken(email);
        return new AuthenticatedUserResponseDTO(
                email,
                token
        );
    }

}
