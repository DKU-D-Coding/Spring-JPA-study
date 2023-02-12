package com.dku.springstudy.model;

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
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    @Column(name = "Image")
    private String imageURL;
    @Column(name = "Created")
    private Timestamp created;
    @Column(name = "Updated")
    private Timestamp updated;
    @Column(name = "Status")
    private String status;
}
