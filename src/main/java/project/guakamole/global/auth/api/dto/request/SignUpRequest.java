package project.guakamole.global.auth.api.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequest {
    @Pattern(regexp = ".*@.*", message = "이메일은 '@'를 반드시 포함해야 합니다.")
    private String email;

    @Size(min = 8, message = "비밀번호는 8자 이상 입력해주세요.")
    private String password;
}
