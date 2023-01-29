package com.dku.springstudy.domain.user.repository;

import com.dku.springstudy.domain.user.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser,String> {
    Optional<SecurityUser> findByEmail(String email);
}
