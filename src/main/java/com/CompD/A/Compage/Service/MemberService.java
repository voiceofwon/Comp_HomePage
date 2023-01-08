package com.CompD.A.Compage.Service;

import com.CompD.A.Compage.DTO.MemberJoinRequestDto;
import com.CompD.A.Compage.DTO.MemberLoginRequestDto;
import com.CompD.A.Compage.DTO.TokenInfo;
import com.CompD.A.Compage.Entity.Member;
import com.CompD.A.Compage.JWT.JwtTokenProvider;
import com.CompD.A.Compage.Repository.MemberRepositroy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService{


    private final MemberRepositroy memberRepositroy;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenInfo login(String memberId, String password){
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        //Redis에 업로드 (미완성)
        String accessToken = tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getRefreshToken();

        return tokenInfo;
    }

    public Long UserJoin (MemberJoinRequestDto memberJoinRequestDto) throws Exception{
        if(memberRepositroy.findByMemberId(memberJoinRequestDto.getMemberId()).isPresent()){
            throw new Exception("이미 존재하는 학번입니다.");
        }

        if(!memberJoinRequestDto.getPassword().equals(memberJoinRequestDto.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        Member member = memberJoinRequestDto.toEntity();
        memberRepositroy.save(member);
        member.encodePassword(passwordEncoder);

        return member.getId();
    }
}
