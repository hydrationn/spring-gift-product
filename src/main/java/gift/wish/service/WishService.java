package gift.wish.service;

import gift.member.dto.CreateWishRequest;
import gift.member.dto.CreateWishResponse;
import gift.member.service.MemberService;
import gift.member.service.ProductService;
import gift.wish.entity.Wish;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final ProductService productService;
    private final MemberService memberService;

    public WishService(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    public CreateWishResponse create(CreateWishRequest request, Long memberId) {
        var wish = new Wish(request.productId(), memberId, request.quantity());
        // var admin = adminService.getById(memberId);
        // 저장
        return new CreateWishResponse(wish.getId(), request.productId(), memberId);
    }
}
