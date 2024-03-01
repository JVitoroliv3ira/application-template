package api.services;

import api.dtos.responses.AuthenticatedUserResponseDTO;
import api.exceptions.ApiException;
import api.providers.AuthProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@SpringBootTest
class AuthServiceTest {

    @Mock
    private final AuthProvider authProvider;

    @Mock
    private final TokenService tokenService;
    private AuthService authService;

    private static final String ERROR_INVALID_CREDENTIALS = "Credenciais inválidas";

    @Autowired
    public AuthServiceTest(AuthProvider authProvider, TokenService tokenService) {
        this.authProvider = authProvider;
        this.tokenService = tokenService;
    }

    @BeforeEach
    void setUp() {
        this.authService = new AuthService(this.authProvider, this.tokenService);
    }

    private Authentication createValidAuthenticationPayload() {
        String email = "valid@email.com";
        String password = "validPassword";
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private Authentication create_invalid_authentication_payload() {
        String email = "invalid@email.com";
        String password = "wrongPassword";
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    @Test
    void authenticate_should_return_token_when_credentials_are_valid() {
        Authentication authentication = createValidAuthenticationPayload();
        String expectedToken = "dummyToken";
        String email = authentication.getName();

        when(authProvider.authenticate(authentication)).thenReturn(authentication);
        when(tokenService.generateToken(email)).thenReturn(expectedToken);

        AuthenticatedUserResponseDTO result = authService.authenticate(authentication);

        assertEquals(email, result.email());
        assertEquals(expectedToken, result.token());
    }

    @Test
    void authenticate_should_throw_exception_when_password_is_incorrect() {
        Authentication authentication = create_invalid_authentication_payload();

        when(authProvider.authenticate(authentication)).thenThrow(new ApiException(ERROR_INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST));

        ApiException exception = assertThrows(ApiException.class, () -> authService.authenticate(authentication));
        assertEquals(ERROR_INVALID_CREDENTIALS, exception.getMessage());
    }
}
