package gift.wish.controller;

import gift.member.annotation.LoginMember;
import gift.member.dto.CreateWishRequest;
import gift.member.entity.Member2;
import gift.wish.service.WishService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@Controller
public class WishController {

    /*private final MemberService memberService;

    public WishController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/wishes")
    public ResponseEntity<Object> wishes() {
        return ResponseEntity.ok(memberService.member());
    }*/

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/api/wishes")
    public ResponseEntity<Object> createWish(
            @RequestBody CreateWishRequest request,
            @LoginMember(admin = false) Member2 member2
            ) {
        var response = wishService.create(request, member2.getId());
        return ResponseEntity.created(URI.create("/api/wishes/" + response.id()))
                .body(response);
    }
}
