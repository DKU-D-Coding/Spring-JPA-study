package com.dku.springstudy.controller;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.*;
import com.dku.springstudy.enums.Role;
import com.dku.springstudy.repository.jpa.MemberRepository;
import com.dku.springstudy.security.AuthenticationProvider;
import com.dku.springstudy.service.ItemService;
import com.dku.springstudy.service.MemberService;
import com.dku.springstudy.service.S3Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final ItemService itemService;
    private final S3Upload s3Upload;

    @PostMapping("/join")
    public JoinDto join(@RequestBody JoinDto joinDto){
        log.info("Join Request={}", joinDto);
        Member joinMember = Member.createMember(
                joinDto.getEmail(),
                joinDto.getPassword(),
                joinDto.getName(),
                joinDto.getPhone(),
                joinDto.getNickname(),
                Role.USER
        );
        memberService.join(joinMember);
        return joinDto;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto){
        log.info("login request={}", loginDto);
        return memberService.login(loginDto.getEmail(), loginDto.getPassword());
    }
    @PostMapping("/reissue")
    public TokenDto reissue(@RequestBody TokenDto tokenDto){
        log.info("Reissue={}", tokenDto);
        return memberService.reissue(tokenDto);
    }

    @GetMapping("/mypage")
    public MyPageDto myPageHome(){
        return memberService.myPageHome();
    }

    @PostMapping("/mypage/update-profiles")
    public void updateProfiles(@RequestPart("data") UpdateProfilesDto updateProfilesDto,
                               @RequestPart("images") MultipartFile multipartFile) throws IOException {


        String uploadImageUri = s3Upload.upload(multipartFile);
        ImageFile profileImageFile = ImageFile.createImageFile(uploadImageUri);

        memberService.updateProfiles(updateProfilesDto.getNickname(), profileImageFile);
    }

    @GetMapping("/mypage/sales-details")
    public List<ItemDto> salesDetails(){
        Member currentMember = AuthenticationProvider.getCurrentMember();
        return itemService.findByMember(currentMember);
    }

    @PostMapping("/mypage/update-sales-status")
    public void updateSalesStatus(@RequestBody UpdateItemStatusDto updateItemStatusDto){
        itemService.updateItemStatus(updateItemStatusDto.getId(), updateItemStatusDto.getItemStatus());
    }

    @GetMapping("/mypage/favorite")
    public List<ItemDto> favoriteItemList(){
        return itemService.findFavoriteItems();
    }




}
