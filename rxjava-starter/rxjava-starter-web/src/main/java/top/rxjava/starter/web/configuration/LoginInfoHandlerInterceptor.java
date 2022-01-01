package top.rxjava.starter.web.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import top.rxjava.apikit.annotation.Login;
import top.rxjava.common.core.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInfoHandlerInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Login methodAnnotation = handlerMethod.getMethodAnnotation(Login.class);
        if (methodAnnotation == null || methodAnnotation.value()) {
            throw UnauthorizedException.of();
        }
        return true;
    }
}
