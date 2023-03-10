package com.CompD.A.Compage.Repository;


import com.CompD.A.Compage.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface MemberRepositroy extends JpaRepository<Member,Long> {
    Optional<Member> findByMemberId(String memberId);

    boolean existsByMemberId(String memberId);

    Long deleteByMemberId(String memberId);

    List<Member> findByMemberIdContains(String search);

}