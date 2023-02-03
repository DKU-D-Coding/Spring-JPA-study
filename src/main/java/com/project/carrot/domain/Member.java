package com.project.carrot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity //객체라는 것을 알림
@ToString
@Builder
public class Member implements UserDetails{
    @Id //Id는 pk
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserId;
    private String UserEmail;
    private String UserPass;
    private String UserTel;
    private String UserName;
    private String NickName;
    private String Address;
    @Builder.Default private double MannerTemp=36.5;
    @Builder.Default
    private String ProfilePhotoURL=null;



    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="UserEmail")
    private Collection<MemberItem> memberItem;

    @Override
    public String getPassword() {
        return UserPass;
    }

    @Override
    public String getUsername() {
        return UserEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public void addMemberItem(MemberItem m){
        if(memberItem == null)
            memberItem=new ArrayList<MemberItem>();
        memberItem.add(m);
    }

//    public static MemberBuilder builder(PostSaveDTO postSaveDTO) {
//        return new MemberBuilder()
//                .UserEmail(postSaveDTO.getEMAIL())
//                .UserPass(postSaveDTO.getPASS())
//                .UserTel(postSaveDTO.getTEL())
//                .UserName(postSaveDTO.getUSERNAME())
//                .NickName(postSaveDTO.getNICKNAME())
//                .Address(postSaveDTO.getADDRESS());
//    }

}

