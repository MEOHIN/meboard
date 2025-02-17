package com.meohin.meboard.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "실제 사용하는 이메일을 입력하세요.")
    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    private String username;

    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_-])[a-zA-Z0-9!@#$%^&*()_-]{8,16}$", 
        message = "비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 모두 포함해야 합니다."
    )
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 항목입니다.")
    private String password2;

    @NotEmpty(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;
}
