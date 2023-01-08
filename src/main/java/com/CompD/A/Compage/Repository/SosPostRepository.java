package com.CompD.A.Compage.Repository;

import com.CompD.A.Compage.Entity.SosPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SosPostRepository extends JpaRepository<SosPost, Long> {
}
