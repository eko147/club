package org.zerock.club.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll() { // 아무나 접근가능
        log.info("exAll...");
    }
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO member) { // 회원만 접근가능
        log.info("exMember...");
        log.info(member);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin() { // 회원중에 admin권한이 있는 사람만 접근가능
        log.info("exAdmin...");
    }
}
