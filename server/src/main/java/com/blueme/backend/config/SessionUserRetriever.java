package com.blueme.backend.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.blueme.backend.model.entity.Users;

// SpringSecurity 사용안하고 세션값 가져올경우 AuditorAware Config
/*
@Component
@RequestScope
public class SessionUserRetriever {
    private Long user_id;

    @Autowired
    public SessionUserRetriever(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                this.user_id = user.getId();
            }
        }
    }

    public Long getId() {
        return user_id;
    }
}*/