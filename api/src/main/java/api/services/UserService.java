package api.services;

import api.exceptions.ApiException;
import api.models.User;
import api.repositories.UserRepository;
import api.utils.BcryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BcryptUtil bcryptUtil;

    private final String EMAIL_NOT_UNIQUE_ERROR = "O email informado já está sendo utilizado por outro usuário.";

    public User register(User entity) {
        this.validateEmailUniqueness(entity.getEmail());
        this.encodeUserPassword(entity);
        return this.create(entity);
    }

    public User create(User entity) {
        entity.setId(null);
        return this.userRepository.save(entity);
    }

    public void validateEmailUniqueness(String email) {
        if (Boolean.TRUE.equals(this.userRepository.existsByEmail(email))) {
            throw new ApiException(
                    EMAIL_NOT_UNIQUE_ERROR,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public void encodeUserPassword(User user) {
        user.setPassword(this.bcryptUtil.encode(user.getPassword()));
    }
}
