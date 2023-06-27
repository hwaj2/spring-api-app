package com.app.global.config.web;

import com.app.global.interceptor.AuthenticationInterceptor;
import com.app.global.resolver.memberinfo.MemberInfoArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final MemberInfoArgumentResolver memberInfoArgumentResolver;

    // CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/api/**") // 해당 path pattern으로 오는 요청의 경우 CORS적용
               .allowedOrigins("*") // 허용되는 리소스(요청사이트의 url)
               .allowedMethods(     // 허용되는 http method
                       HttpMethod.GET.name(),
                       HttpMethod.POST.name(),
                       HttpMethod.PUT.name(),
                       HttpMethod.PATCH.name(),
                       HttpMethod.DELETE.name(),
                       HttpMethod.OPTIONS.name()
                       );
    }

    // API 인증 인터셉터
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/oauth/login", "/api/access-token/issue", "/api/logout", "/api/health");
    }

    // ArgumentResolvers
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
    }
}
