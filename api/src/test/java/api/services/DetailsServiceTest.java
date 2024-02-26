package api.services;

import api.exceptions.ApiException;
import api.models.Details;
import api.models.User;
import api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class DetailsServiceTest {
    private DetailsService detailsService;
    @Mock
    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND_ERROR = "Usuário não encontrado na base de dados.";

    @Autowired
    public DetailsServiceTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        this.detailsService = new DetailsService(this.userRepository);
    }

    User buildUserPayload() {
        return User
                .builder()
                .id(5L)
                .name("payload")
                .email("payload@payload.com")
                .password("@payload")
                .build();
    }

    @Test
    void load_user_by_username_should_call_find_by_email_method_of_user_repository() {
        User payload = this.buildUserPayload();
        when(this.userRepository.findByEmail(payload.getEmail())).thenReturn(Optional.of(payload));
        this.detailsService.loadUserByUsername(payload.getEmail());
        verify(this.userRepository, times(1)).findByEmail(payload.getEmail());
    }

    @Test
    void load_user_by_username_should_throw_an_exception_when_requested_user_does_not_exists() {
        String payload = "payload@payload.com";
        when(this.userRepository.findByEmail(payload)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.detailsService.loadUserByUsername(payload))
                .isInstanceOf(ApiException.class)
                .hasMessage(USER_NOT_FOUND_ERROR);
    }

    @Test
    void load_user_by_username_should_return_details_of_requested_user() {
        User payload = this.buildUserPayload();
        Details expected = new Details(payload);
        when(this.userRepository.findByEmail(payload.getEmail())).thenReturn(Optional.of(payload));
        Details result = this.detailsService.loadUserByUsername(payload.getEmail());
        assertEquals(expected, result);
    }
}