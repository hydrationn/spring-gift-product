package gift.member.dto;

public record CreateMemberRequest(
        String name,
        Integer age,
        String email
) {}
