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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "회원가입", notes = "회원가입한 회원 리턴")
    @ApiResponse(code = 200, message = "API 정상 작동")
    @PostMapping("/join")
    public JoinDto join(@RequestBody JoinDto joinDto){
        log.info("Join Request={}", joinDto);
        memberService.join(
                joinDto.getEmail(),
                joinDto.getPassword(),
                joinDto.getName(),
                joinDto.getPhone(),
                joinDto.getNickname(),
                Role.USER);
        return joinDto;
    }


    @ApiOperation(value = "로그인", notes = "회원 로그인 후, Token 정보 리턴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동"),
            @ApiResponse(code = 401, message = "토큰 정보 오류"),
            @ApiResponse(code = 404, message = "토큰 만료 오류")
        }
    )
    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto){
        log.info("login request={}", loginDto);
        return memberService.login(loginDto.getEmail(), loginDto.getPassword());
    }

    @ApiOperation(value = "토큰 재발행", notes = "accessToken 유효기간 만료되었는데 refreshToken은 아직 유효기간 만료되지 않았으면 토큰 재발행")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동"),
            @ApiResponse(code = 401, message = "토큰 정보 오류"),
            @ApiResponse(code = 404, message = "토큰 만료 오류, 회원 존재하지 않음")
    }
    )
    @PostMapping("/reissue")
    public TokenDto reissue(@RequestBody TokenDto tokenDto){
        log.info("Reissue={}", tokenDto);
        return memberService.reissue(tokenDto);
    }


    @ApiOperation(value = "마이페이지", notes = "마이페이지 홈에서는 프로필 이미지 링크와 회원 닉네임 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @GetMapping("/mypage")
    public MyPageDto myPageHome(){
        return memberService.myPageHome();
    }

    @ApiOperation(value = "마이페이지의 프로필 수정", notes = "마이페이지 내의 프로필 수정 기능. 프로필 사진과 닉네임 변경 가능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @PostMapping("/mypage/update-profiles")
    public void updateProfiles(@RequestPart("data") UpdateProfilesDto updateProfilesDto,
                               @RequestPart("images") MultipartFile multipartFile) throws IOException {


        String uploadImageUri = s3Upload.upload(multipartFile);
        ImageFile profileImageFile = ImageFile.createImageFile(uploadImageUri);

        memberService.updateProfiles(updateProfilesDto.getNickname(), profileImageFile);
    }

    @ApiOperation(value = "내 판매내역 조회", notes = "마이페이지에서 나의 판매내역 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @GetMapping("/mypage/sales-details")
    public List<ItemDto> salesDetails(){
        Member currentMember = AuthenticationProvider.getCurrentMember();
        return itemService.findByMember(currentMember);
    }

    @ApiOperation(value = "나의 판매 상품 중에서 판매상태 변경", notes = "판맵중, 예약중, 거래완료로 상태변경 가능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @PostMapping("/mypage/update-sales-status")
    public void updateSalesStatus(@RequestBody UpdateItemStatusDto updateItemStatusDto){
        itemService.updateItemStatus(updateItemStatusDto.getId(), updateItemStatusDto.getItemStatus());
    }


    @ApiOperation(value = "좋아요 누른 상품 리스트 확인", notes = "좋아요 누른 상품 리스트 리턴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )

    @GetMapping("/mypage/favorite")
    public List<ItemDto> favoriteItemList(){
        return itemService.findFavoriteItems();
    }




}
