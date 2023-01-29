package com.cha.carrotApi.service;

import com.cha.carrotApi.repository.MemberSignUpRequestDto;

import java.util.Map;

public interface MemberService {
    public Long signUp(MemberSignUpRequestDto memberSignUpRequestDto) throws Exception;

    public String login(Map<String, String> members);
}
