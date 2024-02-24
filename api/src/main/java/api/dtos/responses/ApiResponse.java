package api.dtos.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Objects;

public record ApiResponse<C>(C content, String message, List<String> errors) {
    @JsonSerialize
    public Boolean hasErrors() {
        return Objects.nonNull(errors()) && !errors().isEmpty();
    }
}
