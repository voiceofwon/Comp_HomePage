package com.CompD.A.Compage;

import com.CompD.A.Compage.Entity.Member;
import com.CompD.A.Compage.Repository.MemberRepositroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CompageApplicationTests {

	@Autowired MemberRepositroy memberRepositroy;

	@Test
	void contextLoads() {
		Member member = new Member();
		member.setS_id(202126978);

		memberRepositroy.save(member);
		assertEquals(202126978,member.getS_id());
	}

}
