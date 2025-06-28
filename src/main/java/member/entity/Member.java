package member.entity;

public record Member (
        Long id,
        String name,
        int age,
        String email
) {
}
