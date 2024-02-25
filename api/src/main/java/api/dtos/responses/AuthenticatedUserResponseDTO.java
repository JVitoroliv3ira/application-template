package api.dtos.responses;

public record AuthenticatedUserResponseDTO(
        String email,
        String token
) {

}
