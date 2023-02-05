package com.dku.spring.project.carrot.repository;

import com.dku.spring.project.carrot.dto.UserDto;
import com.dku.spring.project.carrot.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {

  Optional<UserDto> findByLoginId(String loginId);
}
