package com.project.carrot.repository;

import com.project.carrot.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository{
    Member save(Member member);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByName(String name);
}
