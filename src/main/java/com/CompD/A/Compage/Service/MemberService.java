package com.CompD.A.Compage.Service;

import com.CompD.A.Compage.DTO.MemberJoinRequestDto;
import com.CompD.A.Compage.DTO.MemberLoginRequestDto;
import com.CompD.A.Compage.DTO.RegenerateTokenDTO;
import com.CompD.A.Compage.DTO.TokenInfo;
import com.CompD.A.Compage.Entity.Member;
import com.CompD.A.Compage.JWT.JwtTokenProvider;
import com.CompD.A.Compage.Repository.MemberRepositroy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService{


    private final MemberRepositroy memberRepositroy;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public TokenInfo login(MemberLoginRequestDto memberLoginRequestDto){

        Member member = memberRepositroy.findByMemberId(memberLoginRequestDto.getMemberId())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 사용자입니다."));
        if(!memberLoginRequestDto.getPassword().equals(member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginRequestDto.getMemberId(), memberLoginRequestDto.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue()
                .set("RefreshToken"+ authentication.getName(), tokenInfo.getRefreshToken(),
                        86400000, TimeUnit.MILLISECONDS);




        return tokenInfo;
    }

    @Transactional
    public void logout(TokenInfo tokenInfo){
        if(!jwtTokenProvider.validateToken(tokenInfo.getAccessToken())){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");


        }
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenInfo.getAccessToken());

        if(redisTemplate.opsForValue().get("RefreshToken"+authentication.getName())!=null){
            redisTemplate.delete("RefreshToken" + authentication.getName());
        }

        //accessToken을 블랙리스트에 추가
        redisTemplate.opsForValue().set(tokenInfo.getAccessToken(),"logout",86400000,TimeUnit.MILLISECONDS);
    }

    public TokenInfo regenerateToken(RegenerateTokenDTO regenerateTokenDTO) throws Exception{
        String  refreshToken = regenerateTokenDTO.getRefreshToken();

        if(!jwtTokenProvider.validateToken(refreshToken)){
            throw new Exception("Invalid refresh Token");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

        String savedToken = redisTemplate.opsForValue().get("RefreshToken"+ authentication.getName()).toString();
        if(!refreshToken.equals(savedToken)){
            throw new Exception("Refresh Token doesn't match");
        }

        TokenInfo newToken = jwtTokenProvider.generateToken(authentication);
        redisTemplate.opsForValue().set(authentication.getName(), newToken.getRefreshToken()
                ,86400000,TimeUnit.MILLISECONDS);

        return newToken;
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

    public Boolean memberDelete(String memberId) throws Exception{
        if(memberRepositroy.existsByMemberId(memberId)){
            memberRepositroy.deleteByMemberId(memberId);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Member> memberSearch(String memberId) throws Exception{
        List<Member> searchList = memberRepositroy.findByMemberIdContains(memberId);
        if(!searchList.isEmpty()){
            return searchList;
        }
        else{
            throw new Exception("검색된 회원이 없습니다.");
        }
    }

    public int membercount(String memberId){
        return memberRepositroy.findByMemberIdContains(memberId).size();
    }
}
