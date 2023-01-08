package com.CompD.A.Compage.DTO;

import com.CompD.A.Compage.Entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberLoginRequestDto {

    private String memberId;
    private String password;

}
