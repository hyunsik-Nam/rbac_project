package com.project.rbac.auth.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    public interface LoginGroup {}
    public interface RegisterGroup {}

    private Long id;

    @NotBlank(message = "유저이름은 필수 입력값입니다.", groups = {RegisterGroup.class})
    private String userName;

    @NotBlank(message = "ID는 필수 입력값입니다.", groups = {RegisterGroup.class, LoginGroup.class})
    @Size(min=3, max=20, message = "ID는 3~20자 사이여야 합니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.", groups = {RegisterGroup.class, LoginGroup.class})
    @Pattern(
            regexp = "^(?=(.*[a-z]){2,})(?=(.*[A-Z]){2,})(?=(.*\\d){1,})(?=(.*[@$!%*?&]){2,}).{8,}$",
            message = "비밀번호는 최소 2개의 대문자, 2개의 소문자, 1개의 숫자, 2개의 특수문자를 포함해야 하며 최소 8자 이상이어야 합니다."
            , groups = {RegisterGroup.class, LoginGroup.class}
    )
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.", groups = {RegisterGroup.class})
    private String passwordConfirm;

    // 비밀번호와 비밀번호 확인이 일치하는지 확인
    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.", groups = {RegisterGroup.class})
    public boolean isPasswordMatching() {
        return password != null && password.equals(passwordConfirm);
    }

    @NotBlank(message = "권한은 필수 입력값입니다.", groups = {RegisterGroup.class})
    private String auth = "USER";

    public void setAuth(String auth) {
        if (auth != null && !auth.isEmpty()) {
            this.auth = auth; // auth 값이 입력된 경우에만 변경
        }
    }
}
