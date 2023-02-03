package com.project.carrot.repository;

import com.project.carrot.domain.Member;
import com.project.carrot.domain.MemberItem;

import java.util.Optional;

public interface MemberItemRepository {
    MemberItem save(MemberItem memberItem);
    Optional<MemberItem> findById(int Id);
    Optional<MemberItem> findByName(String name);
    Optional<MemberItem> findByPass(String pass);
}