package api.services;

import api.exceptions.ApiException;
import api.models.User;
import api.repositories.UserRepository;
import api.utils.BcryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class UserServiceTest {
    private UserService userService;

    @Mock
    private final UserRepository userRepository;

    @Mock
    private final BcryptUtil bcryptUtil;

    private static final String EMAIL_NOT_UNIQUE_ERROR = "O email informado já está sendo utilizado por outro usuário.";

    @Autowired
    public UserServiceTest(UserRepository userRepository, BcryptUtil bcryptUtil) {
        this.userRepository = userRepository;
        this.bcryptUtil = bcryptUtil;
    }

    @BeforeEach
    void setUp() {
        this.userService = new UserService(
                this.userRepository,
                this.bcryptUtil
        );
    }

    User buildUserPayload(Long id) {
        return User
                .builder()
                .id(id)
                .name("payload")
                .email("payload@payload.com")
                .password("@payload")
                .build();
    }

    @Test
    void register_should_call_exists_by_email_method_of_user_repository() {
        User payload = this.buildUserPayload(null);
        when(this.userRepository.existsByEmail(payload.getEmail())).thenReturn(false);
        this.userService.register(payload);
        verify(this.userRepository, times(1)).existsByEmail(payload.getEmail());
    }

    @Test
    void register_should_throw_an_exception_when_requested_email_is_already_in_use() {
        User payload = this.buildUserPayload(null);
        when(this.userRepository.existsByEmail(payload.getEmail())).thenReturn(true);
        assertThatThrownBy(() -> this.userService.register(payload))
                .isInstanceOf(ApiException.class)
                .hasMessage(EMAIL_NOT_UNIQUE_ERROR);
    }

    @Test
    void register_should_call_encode_method_of_bcrypt_util() {
        User payload = this.buildUserPayload(null);
        String payloadPassword = payload.getPassword();
        when(this.userRepository.existsByEmail(payload.getEmail())).thenReturn(false);
        this.userService.register(payload);
        verify(this.bcryptUtil, times(1)).encode(payloadPassword);
    }

    @Test
    void register_should_call_save_method_of_user_repository() {
        User payload = this.buildUserPayload(null);
        when(this.userRepository.existsByEmail(payload.getEmail())).thenReturn(false);
        when(this.bcryptUtil.encode(payload.getPassword())).thenReturn("@payload");
        this.userService.register(payload);
        verify(this.userRepository, times(1)).save(payload);
    }

    @Test
    void register_should_set_id_to_null_before_call_save_method_of_user_repository() {
        User expected = this.buildUserPayload(null);
        User payload = this.buildUserPayload(3L);
        when(this.userRepository.existsByEmail(payload.getEmail())).thenReturn(false);
        when(this.bcryptUtil.encode(payload.getPassword())).thenReturn("@payload");
        this.userService.register(payload);
        verify(this.userRepository, times(1)).save(expected);
    }

    @Test
    void create_should_call_save_method_of_user_repository() {
        User payload = this.buildUserPayload(null);
        this.userService.create(payload);
        verify(this.userRepository, times(1))
                .save(payload);
    }

    @Test
    void create_should_set_payload_id_to_null_before_call_save_method() {
        User expected = this.buildUserPayload(null);
        User payload = this.buildUserPayload(3L);
        this.userService.create(payload);
        verify(this.userRepository, times(1))
                .save(expected);
    }

    @Test
    void validate_email_uniqueness_should_call_exists_by_email_method_of_user_repository() {
        String payload = "payload@payload.com";
        when(this.userRepository.existsByEmail(payload)).thenReturn(false);
        this.userService.validateEmailUniqueness(payload);
        verify(this.userRepository, times(1)).existsByEmail(payload);
    }

    @Test
    void validate_email_uniqueness_should_throw_an_exception_when_requested_email_is_already_in_use() {
        String payload = "payload@payload.com";
        when(this.userRepository.existsByEmail(payload)).thenReturn(true);
        assertThatThrownBy(() -> this.userService.validateEmailUniqueness(payload))
                .isInstanceOf(ApiException.class)
                .hasMessage(EMAIL_NOT_UNIQUE_ERROR);
    }

    @Test
    void encode_user_password_should_call_encode_method_of_bcrypt_util() {
        User payload = this.buildUserPayload(null);
        String expected = payload.getPassword();
        this.userService.encodeUserPassword(payload);
        verify(this.bcryptUtil, times(1)).encode(expected);
    }
}