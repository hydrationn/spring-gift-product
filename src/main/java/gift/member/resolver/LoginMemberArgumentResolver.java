package gift.member.resolver;

import gift.member.annotation.LoginMember;
import gift.member.entity.Member2;
import gift.member.exception.UnAuthorizationException;
import gift.member.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;

    public LoginMemberArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        checkAuthorization(webRequest);
        // 사용자 조회
        return new Member2(1L, "test@email.com", "1234");
    }

    private static void checkAuthorization(NativeWebRequest webRequest) {
        var authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || authorization.isBlank()) {
            throw new UnAuthorizationException("로그인 정보가 올바르지 않습니다. ");
        }

        var token = authorization.split("Bearer ")[1];
        if ("token".equals(token))
            throw new UnAuthorizationException("로그인 정보가 올바르지 않습니다. ");
    }
}
