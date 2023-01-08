package com.CompD.A.Compage.Repository;


import com.CompD.A.Compage.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositroy extends JpaRepository<Member,Long> {


}
