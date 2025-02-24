package com.meohin.meboard.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.meohin.meboard.service.UserSecurityService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MySQLAuthenticationProvider implements AuthenticationProvider {
    
    private final UserSecurityService userSecurityService;
    private final PasswordEncoder passwordEncoder;

    /*
     * Spring Security에 의해서 사용자가 전송한 id와 pw가 자동 매핑되는데, 기본값으로 설정되어야 하는 폼 이름이 있음.
     *      username, password 임. 
     * 따라서 login.html 에서 post로 보낼 input의 이름을 위와 동일하게 해주어야, Spring Security가 자동으로 Authentication의 인스턴스로 매핑해서 아래 메서드에 주입한다.
     * 
     * Security 환경설정을 통해 이 이름을 바꿀 수도 있다.
     *      하지만 시간이 지나서 기본값이 username과 password 였다는걸 까마득히 잊거나, 잘 모르는 개발자가 코드를 보면, 인지하기 어려우므로,명시적으로 환경설정에 추가해주는게 좋을 수 있다.
     *      또는 문서화 시키거나. (온 보딩, 팀에 합류하는데 필요한 어떤 학습. 프로젝트의 구조, 소스코드의 구조 등등.)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 입력받은 ID와 비밀번호를 변수에 할당
        String userId = authentication.getName();
        String pw = (String) authentication.getCredentials();

        // 데이터베이스에 저장된 회원 정보를 입력받은 ID로 조회
        UserDetails mySQLUser = this.userSecurityService.loadUserByUsername(userId);

        // passwordEncoder의 matches() 메서드를 사용해서 입력받은 비밀번호(암호화되지 않은 비밀번호)와 저장된 비밀번호(암호화된 비밀번호)를 비교
        if (!passwordEncoder.matches(pw, mySQLUser.getPassword())) {
            // 만약에 입력받은 비밀번호와 저장된 비밀번호가 다르면 예외 발생
            throw new BadCredentialsException(authentication.getName());
        }

        // 입력받은 ID, 비밀번호, 로그인한 사용자에게 부여할 권한을 담아서 UsernamePasswordAuthenticationToken 생성
        // UsernamePasswordAuthenticationToken 객체는 곧 Authentication 객체다.
        return new UsernamePasswordAuthenticationToken(userId, pw, mySQLUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 주어진 authentication 파라미터가 UsernamePasswordAuthenticationToken 클래스 또는 그 하위 클래스인지 확인
        // UsernamePasswordAuthenticationToken 클래스 또는 그 하위 클래스인 경우에만 true를 반환하여 해당 토큰이 이 AuthenticationProvider에서 지원되도록 설정
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
