package com.meohin.meboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meohin.meboard.entity.SiteUser;
import com.meohin.meboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 조회
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    // 회원가입
    public SiteUser register(String username, String nickname, String password) {
        // 1. 이메일 중복 검사 (옵션)
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 닉네임 중복 검사
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 3. 비밀번호 암호화 후 저장
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }
    
    // 아이디(이메일) 중복 여부 확인
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    // 닉네임 중복 여부 확인
    public boolean isNicknameTaken(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // 회원정보 수정
    // 1. 닉네임만 수정
    public SiteUser modifyNickname(String username, String nickname) {
        SiteUser user = getUser(username);
        
        // 현재 사용자의 닉네임과 같다면 중복 검사 건너뛰기
        if (!user.getNickname().equals(nickname) && userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        user.setNickname(nickname);
        return userRepository.save(user);
    }

    // 2. 비밀번호만 수정
    public SiteUser modifyPassword(String username, String currentPassword, String newPassword) {
        SiteUser user = getUser(username);
        
        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}