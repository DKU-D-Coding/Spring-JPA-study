package com.dku.spring.project.carrot.repository;

import com.dku.spring.project.carrot.entity.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom{

  public UserRepositoryImpl(){
    super(User.class);
  }

}
