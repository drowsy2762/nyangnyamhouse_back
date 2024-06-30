package dto;

public record JwtDto(
        String accessToken,
        String refreshToken,
        String grantType
) {
}
