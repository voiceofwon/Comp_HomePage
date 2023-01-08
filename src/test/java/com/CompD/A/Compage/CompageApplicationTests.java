package com.CompD.A.Compage;

import com.CompD.A.Compage.DTO.MemberJoinRequestDto;
import com.CompD.A.Compage.Entity.Member;
import com.CompD.A.Compage.Repository.MemberRepositroy;
import com.CompD.A.Compage.Service.MemberService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional
class CompageApplicationTests {

	@Autowired MemberRepositroy memberRepositroy;
	@Autowired PasswordEncoder passwordEncoder;

	@Autowired
	MemberService memberService;

	@Test
	void UserJoinTest() throws Exception {
		MemberJoinRequestDto memberJoinRequestDto = new MemberJoinRequestDto("202126978","won1102","won1102","USER",3);

		Long id = memberService.UserJoin(memberJoinRequestDto);

		assertEquals(id,1);
	}

	@Test
	void EncodingTest() throws Exception {
		String memberId = "202126978";
		String password = "won1102";
		passwordEncoder.matches(password,memberRepositroy.findByMemberId(memberId).get().getPassword());
	}



}
