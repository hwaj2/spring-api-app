package com.app.global.config.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {


    @Autowired
    private HttpServletRequest httpServletRequest;     // 반환값으로 등록될 생성자,수정자의 url 정보를 가져오기위한 변수

    @Override
    public Optional<String> getCurrentAuditor() {
        String modifiedBy = httpServletRequest.getRequestURI();
        if(!StringUtils.hasText(modifiedBy)){
            modifiedBy = "unknown";
        }
        return Optional.of(modifiedBy);
    }
}
