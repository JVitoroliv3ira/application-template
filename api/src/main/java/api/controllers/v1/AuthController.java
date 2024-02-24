package api.controllers.v1;

import api.dtos.requests.UserRegistrationRequestDTO;
import api.dtos.responses.ApiResponse;
import api.models.User;
import api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {
    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody UserRegistrationRequestDTO request) {
        User createdUser = this.userService.register(request.convert());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        createdUser,
                        "Usuário cadastrado com sucesso.",
                        null
                ));
    }
}
