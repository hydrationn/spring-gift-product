package gift.member.entity;

public class Member2 {
    private Long id;
    private String email;
    private String password;

    public Member2(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
