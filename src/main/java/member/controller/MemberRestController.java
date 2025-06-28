package member.controller;

import member.dao.MemberDao2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import member.entity.Member;

@RestController
public class MemberRestController {
    private final MemberDao2 memberDao2;

    public MemberRestController(MemberDao2 memberDao2) {
        this.memberDao2 = memberDao2;
    }

    @GetMapping("/api/members")
    public void insertMember() {
        var member = new Member(1L, "박수화", 20, "test@gmail.com");
        memberDao2.insertMember(member);
    }

    @GetMapping("/api/members/{id}")
    public Member selectMember(@PathVariable Long id) {
        return memberDao2.selectMember(id);
    }
}
