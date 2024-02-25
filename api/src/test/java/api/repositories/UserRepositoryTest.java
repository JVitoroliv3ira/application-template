package api.repositories;

import api.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = "h2")
@DataJpaTest
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        this.userRepository.deleteAll();
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
    void save_should_return_non_null_user() {
        User payload = this.buildUserPayload(null);
        User result = this.userRepository.save(payload);
        assertNotNull(result);
    }

    @Test
    void save_should_return_user_with_non_null_id() {
        User payload = this.buildUserPayload(null);
        User result = this.userRepository.save(payload);
        assertNotNull(result.getId());
    }

    @Test
    void save_should_insert_new_user_in_database() {
        User payload = this.buildUserPayload(null);
        User createdUser = this.userRepository.save(payload);
        boolean result = this.userRepository.existsById(createdUser.getId());
        assertTrue(result);
    }

    @Test
    void exists_by_email_should_return_false_when_user_does_not_exists() {
        String payload = "payload@payload.com";
        boolean result = this.userRepository.existsByEmail(payload);
        assertFalse(result);
    }

    @Test
    void exists_by_email_should_return_true_when_user_exists() {
        User payload = this.buildUserPayload(null);
        User createdUser = this.userRepository.save(payload);
        boolean result = this.userRepository.existsByEmail(createdUser.getEmail());
        assertTrue(result);
    }
}