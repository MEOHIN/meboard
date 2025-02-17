package com.meohin.meboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meohin.meboard.constant.UserRole;
import com.meohin.meboard.entity.SiteUser;
import com.meohin.meboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// 인증 처리가 가능하도록 스프링 시큐리티의 UserDetailsService를 구현한다.
/* 
UserSecurityService 는 스프링 시큐리티 로그인 처리의 핵심 부분이다.

UserDetailService 는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스다.

아래 링크는 스프링 시큐리티의 인증절차를 정리한 블로그 글입니다.
https://blog.naver.com/meohin/223167138602
*/
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin@example.com".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
