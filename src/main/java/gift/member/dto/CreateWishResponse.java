package gift.member.dto;

public record CreateWishResponse (
        Long id,
        Long productId,
        Long memberId
) {
}
