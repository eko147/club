package org.zerock.club.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final PasswordEncoder passwordEncoder;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("----------------------------");
        log.info("onAuthenticationSuccess");

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO) authentication.getPrincipal();
        boolean fromSocial = authMember.isFromSocial();
        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());
        log.info("fromSocial: " + fromSocial);
        log.info("passwordResult: " + passwordResult);
        log.info("password:"+authMember.getPassword());
        if (fromSocial && passwordResult) {
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }
    }
}
