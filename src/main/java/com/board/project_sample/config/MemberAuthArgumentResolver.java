package com.board.project_sample.config;

import com.board.project_sample.service.JwtService;
import com.board.project_sample.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            throw new RuntimeException("UnauthorizedException");
        }
        String token = authorization.substring(7);
        Map<String, Long> decodedToken = jwtService.decode(token);
        Long memberId = decodedToken.get(JwtService.CLAIM_NAME_MEMBER_ID);

        if(memberId == null){
            throw new RuntimeException("UnauthorizedException");
        }
        return AuthInfo.of(memberId);
    }
}
