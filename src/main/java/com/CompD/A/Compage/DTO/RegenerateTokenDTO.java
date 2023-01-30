package com.CompD.A.Compage.DTO;

import lombok.Data;

@Data
public class RegenerateTokenDTO {
    private String refreshToken;
    private String memberId;
}
