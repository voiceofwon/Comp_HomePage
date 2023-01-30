package com.CompD.A.Compage;

import com.CompD.A.Compage.Controller.LoginController;
import com.CompD.A.Compage.DTO.MemberJoinRequestDto;
import com.CompD.A.Compage.DTO.TokenInfo;
import com.CompD.A.Compage.Entity.Member;
import com.CompD.A.Compage.JWT.JwtTokenProvider;
import com.CompD.A.Compage.Repository.MemberRepositroy;
import com.CompD.A.Compage.Service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@Transactional
class CompageApplicationTests {

	@Autowired MemberRepositroy memberRepositroy;
	@Autowired PasswordEncoder passwordEncoder;

	@Autowired
	MemberService memberService;

	@Test
	void UserJoinTest() throws Exception {
		MemberJoinRequestDto memberJoinRequestDto = new MemberJoinRequestDto("202312345","202312345","202312345","USER",1);

		Long id = memberService.UserJoin(memberJoinRequestDto);

		assertEquals(id,4);
	}


	@Test
	void EncodingTest() throws Exception {
		String memberId = "202126978";
		String password = "won1102";
	}

	@Test
	void MemberDeleteTest() throws Exception{
		memberService.memberDelete("202126978");
	}


}

@ExtendWith(MockitoExtension.class)
class MockitoTest{


	@InjectMocks
	MemberService memberService;

	@Mock
	MemberRepositroy memberRepositroy;


	@Test
	void Test() throws Exception{
		BDDMockito.given(memberRepositroy.findByMemberId("202126978")).willReturn(Optional.of(Member.builder()
				.id(1L)
				.memberId("202126978")
				.password("202126978")
				.grade(3)
				.roles(List.of("USER"))
				.build()));
		BDDMockito.given(memberRepositroy.existsByMemberId("202126978")).willReturn(true);
		BDDMockito.given(memberRepositroy.deleteByMemberId("202126978")).willReturn(1L);
		//When
		Member testMember = memberRepositroy.findByMemberId("202126978").get();
		boolean delete = memberService.memberDelete(testMember.getMemberId());



		//then
		BDDMockito.then(memberRepositroy).should(BDDMockito.times(1)).deleteByMemberId("202126978");
		BDDMockito.then(memberRepositroy).should(BDDMockito.times(1)).existsByMemberId("202126978");

		assertEquals(testMember.getAuthorities().toString(), "[USER]");
		assertEquals(delete, true);

		List<Integer> a = List.of(1);
		List<Integer> b = List.of(1);
		assertEquals(a,b,"에헤이 조졌네 이거");

	}


}


/*@ExtendWith(MockitoExtension.class)
@WebMvcTest
class MockitoMvcTest{
	@Autowired
	MemberService memberService;

	@Autowired
	MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithUserDetails
	void loginMvcTest() throws Exception{
		mvc.perform(get("/Sos")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}
}*/
