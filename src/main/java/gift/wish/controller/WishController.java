package gift.wish.controller;

import gift.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishController {

    private final MemberService memberService;

    public WishController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/wishes")
    public ResponseEntity<Object> wishes() {
        return ResponseEntity.ok(memberService.member());
    }
}
