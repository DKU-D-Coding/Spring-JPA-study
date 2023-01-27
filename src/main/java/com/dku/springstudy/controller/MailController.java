package com.dku.springstudy.controller;

import com.dku.springstudy.service.MailServiceImpl;
import com.dku.springstudy.dto.MailDTO;
import com.dku.springstudy.dto.ResponseDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailServiceImpl mailService;
    @PostMapping("/account/mailCheck")
    @ApiOperation(value = "이메일 인증", notes = "이메일 인증을 진행합니다.")
    public ResponseDTO<?> mailConfirm(@RequestBody MailDTO mailDTO) throws Exception {
        String email = mailDTO.getEmail();
        String code = mailService.sendSimpleMessage(email);
        log.info("인증코드 : "+code);
        return new ResponseDTO<>(HttpStatus.OK.value(), code);
    }
}
