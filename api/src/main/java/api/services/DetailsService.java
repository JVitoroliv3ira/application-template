package api.services;

import api.exceptions.ApiException;
import api.models.Details;
import api.models.User;
import api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND_ERROR = "Usuário não encontrado na base de dados.";

    @Override
    public Details loadUserByUsername(String email) throws ApiException {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(
                        USER_NOT_FOUND_ERROR,
                        HttpStatus.BAD_REQUEST
                ));
        return new Details(user);
    }
}
