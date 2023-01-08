package com.CompD.A.Compage.DTO;

import com.CompD.A.Compage.Entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberJoinRequestDto {

    @NotBlank(message = "학번을 입력해주세요")
    private String memberId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    private String checkedPassword;

    private String role;

    private int grade;

    public Member toEntity(){
        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(password);
        member.setGrade(grade);
        member.getRoles().add(role);

        return member;
    }
}
