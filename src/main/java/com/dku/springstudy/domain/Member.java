package com.dku.springstudy.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter //Lombok
@Setter //Lombok
@Table(name = "MEMBER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Timestamp created;

}
