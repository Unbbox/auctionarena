package com.example.auctionarena.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

@Data
public class AuthMemberDto extends User implements OAuth2User {

    private MemberDto memberDto;

    private Map<String, Object> attr;

    private boolean fromSocial;

    public AuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthMemberDto(MemberDto memberDto, boolean fromSocial) {
        super(memberDto.getEmail(), memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDto.getRole())));
        this.memberDto = memberDto;
        this.fromSocial = fromSocial;
    }

    public AuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return this.memberDto.getName();
    }
}
