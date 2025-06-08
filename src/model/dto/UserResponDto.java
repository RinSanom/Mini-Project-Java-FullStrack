package model.dto;

public record UserResponDto(
        Integer userId,
        String uuid,
        String email
) {
}
