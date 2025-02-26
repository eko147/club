package org.zerock.club.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.repository.ClubMemberRepository;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername:"+username);
        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email or Social");
        }
        ClubMember member = result.get();
        // entity -> dto(UserDetails)
        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member
                        .getRoleSet()
                        .stream()
                        .map(role-> {
                            return new SimpleGrantedAuthority("ROLE_"+role.name());
                        }).collect(Collectors.toSet())
        );
        clubAuthMemberDTO.setName(member.getName());
        clubAuthMemberDTO.setFromSocial(member.isFromSocial());

        return clubAuthMemberDTO;
    }
}
