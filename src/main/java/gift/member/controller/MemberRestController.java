package gift.member.controller;

import gift.member.dao.MemberDao2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gift.member.entity.Member;

@RestController
@RequestMapping("/api/members")
public class MemberRestController {
    private final MemberDao2 memberDao2;

    public MemberRestController(MemberDao2 memberDao2) {
        this.memberDao2 = memberDao2;
    }

    @PostMapping
    public ResponseEntity<Void> insertMember() {
        var member = new Member(1L, "park", 20, "test@gmail.com");
        memberDao2.insertMember(member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> selectMember(@PathVariable Long id) {
        return ResponseEntity.of(memberDao2.selectMember(id));

        /* 방법 1
        try {
            return ResponseEntity.ok(memberDao2.selectMember(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }*/

        /* 방법 2
        var response = memberDao2.selectMember(id);
        if (response.isPresent()) {
            return ResponseEntity.of(response); // .of()는 있으면 ok, 없으면 not found를 의미
        }
        return ResponseEntity.notFound().build();*/
    }
}
