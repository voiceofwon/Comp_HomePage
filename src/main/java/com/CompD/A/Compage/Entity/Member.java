package com.CompD.A.Compage.Entity;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    private long S_id;

    private String role;
}
