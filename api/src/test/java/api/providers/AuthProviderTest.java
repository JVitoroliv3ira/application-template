package api.providers;

import api.models.User;
import api.providers.AuthProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import api.exceptions.ApiException;
import api.models.Details;
import api.services.DetailsService;
import api.utils.BcryptUtil;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class AuthProviderTest {

    @Mock
    private final DetailsService detailsService;

    @Mock
    private final BcryptUtil bcryptUtil;

    private AuthProvider authProvider;

    private static final String USER_NOT_FOUND_ERROR = "Usuário não encontrado na base de dados.";
    private static final String ERROR_INVALID_CREDENTIALS = "Credenciais inválidas";

    @Autowired
    public AuthProviderTest(DetailsService detailsService, BcryptUtil bcryptUtil) {
        this.detailsService = detailsService;
        this.bcryptUtil = bcryptUtil;
    }

    @BeforeEach
    void setUp() {
        this.authProvider = new AuthProvider(this.detailsService, this.bcryptUtil);
    }

    private Authentication createValidAuthenticationPayload() {
        String email = "valid@email.com";
        String password = "validPassword";
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private Authentication createInvalidAuthenticationPayload() {
        String email = "invalid@email.com";
        String password = "invalidPassword";
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private Details createDetailsPayload(Authentication authentication) {
        User user = User
                .builder()
                .id(1L)
                .name("payload")
                .email(authentication.getPrincipal().toString())
                .password(authentication.getCredentials().toString())
                .build();
        return new Details(user);
    }

    @Test
    void authenticate_should_return_non_null_result_when_credentials_are_valid() {
        Authentication authentication = createValidAuthenticationPayload();
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Details userDetails = this.createDetailsPayload(authentication);
        when(detailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(bcryptUtil.matches(password, userDetails.getPassword())).thenReturn(true);

        Authentication result = authProvider.authenticate(authentication);

        assertNotNull(result);
    }

    @Test
    void authenticate_should_return_correct_principal_when_credentials_are_valid() {
        Authentication authentication = createValidAuthenticationPayload();
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Details userDetails = this.createDetailsPayload(authentication);
        when(detailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(bcryptUtil.matches(password, userDetails.getPassword())).thenReturn(true);

        Authentication result = authProvider.authenticate(authentication);

        assertEquals(userDetails.getUsername(), result.getPrincipal());
    }

    @Test
    void authenticate_should_throw_correct_exception_message_when_credentials_are_invalid() {
        Authentication authentication = createInvalidAuthenticationPayload();
        String email = authentication.getName();

        when(detailsService.loadUserByUsername(email)).thenThrow(new ApiException(USER_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND));

        ApiException exception = assertThrows(ApiException.class, () -> authProvider.authenticate(authentication));
        assertEquals(USER_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    void authenticate_should_throw_exception_when_password_is_incorrect() {
        Authentication authentication = createValidAuthenticationPayload();
        String email = authentication.getName();
        String incorrectPassword = authentication.getCredentials().toString();

        Details userDetails = createDetailsPayload(authentication);
        when(detailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(bcryptUtil.matches(incorrectPassword, userDetails.getPassword())).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> authProvider.authenticate(authentication));
        assertEquals(ERROR_INVALID_CREDENTIALS , exception.getMessage());
    }

    @Test
    void supports_should_return_true_for_supported_authentication() {
        assertTrue(authProvider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void supports_should_return_false_for_unsupported_authentication() {
        assertFalse(authProvider.supports(LdapAuthenticationProviderConfigurer.class));
    }
}