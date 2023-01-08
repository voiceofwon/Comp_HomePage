package com.CompD.A.Compage.Controller;

import com.CompD.A.Compage.DTO.MemberJoinRequestDto;
import com.CompD.A.Compage.DTO.MemberLoginRequestDto;
import com.CompD.A.Compage.DTO.TokenInfo;
import com.CompD.A.Compage.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private final MemberService memberService;

    @PostMapping
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId,password);

        return tokenInfo;
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody MemberJoinRequestDto request) throws Exception{
        return memberService.UserJoin(request);
    }
}
