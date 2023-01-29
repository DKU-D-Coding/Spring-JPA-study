package com.dku.springstudy.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter //Lombok
@Setter //Lombok
@ToString //Lombok
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "IMAGEURL")
    private String imageURL;
    @Column(name = "CREATED")
    private Timestamp created;
    @Column(name = "UPDATED")
    private Timestamp updated;
    @Column(name = "STATUS")
    private String status;

}
