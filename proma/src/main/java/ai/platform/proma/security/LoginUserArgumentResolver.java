package ai.platform.proma.security;

import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.domain.User;
import ai.platform.proma.service.AcountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Transactional
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AcountsService acountsService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isBuyer = parameter.hasParameterAnnotation(LoginUser.class) &&
                parameter.getParameterType().isAssignableFrom(User.class);
        return isBuyer;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String socialId = ((CustomUserDetail) userDetails).getUsername();

        if (parameter.hasParameterAnnotation(LoginUser.class)) {
            return acountsService.getUserBySocialId(socialId);
        }
        return null;
    }
}