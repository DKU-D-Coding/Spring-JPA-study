package com.dku.springstudy.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Value("${mail.email}")
    private String email;

    @Value("${mail.name}")
    private String name;

    private String confirmNumber; // 인증번호

    // 메일 내용 작성
    @Override
    public MimeMessage createMessage(String receiver) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(RecipientType.TO, receiver); //받는사람
        message.setSubject("당근마켓 회원가입 이메일 인증");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 당근 마켓 서지현입니다.</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += confirmNumber + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress(email,name));// 보내는 사람
        return message;
    }
    @Override
    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0:
                    key.append((char) ((int) (random.nextInt(26)) + 97)); //소문자
                    break;
                case 1:
                    key.append((char) ((int) (random.nextInt(26)) + 65)); //대문자
                    break;
                case 2:
                    key.append((random.nextInt(10))); //숫자
                    break;
            }
        }
        return key.toString();
    }
    @Override
    public String sendSimpleMessage(String receiver) throws Exception {
        confirmNumber = createKey();

        MimeMessage message = createMessage(receiver); // 메일 발송
        try {
            mailSender.send(message);
        } catch (MailException es) {
            throw new IllegalArgumentException();
        }
        return confirmNumber;
    }
}