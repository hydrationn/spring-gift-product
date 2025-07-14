package gift.member.dto;

public record CreateWishRequest (
        Long productId,
        int quantity
) {
}
