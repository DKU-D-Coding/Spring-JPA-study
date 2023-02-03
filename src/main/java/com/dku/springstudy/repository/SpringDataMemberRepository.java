package com.dku.springstudy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dku.springstudy.domain.Member;

public interface SpringDataMemberRepository extends JpaRepository<Member, Long>, MemberRepository{

    @Override
    public Optional<Member> findByName(String name);
}