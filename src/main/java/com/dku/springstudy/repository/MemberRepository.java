package com.dku.springstudy.repository;

import com.dku.springstudy.model.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member newMember);
    Optional<Member> findByEmail(String email);
}

