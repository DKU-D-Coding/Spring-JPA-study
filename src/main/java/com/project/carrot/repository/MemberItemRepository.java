package com.project.carrot.repository;

import com.project.carrot.domain.Member;
import com.project.carrot.domain.MemberItem;

import java.util.List;
import java.util.Optional;

public interface MemberItemRepository {
    MemberItem save(MemberItem memberItem);
    List<MemberItem> findAll();
    List<MemberItem> findByCategory(String category);
    Optional<MemberItem> findByTitle(String title);
    Optional<MemberItem> findByContent(String content);


}