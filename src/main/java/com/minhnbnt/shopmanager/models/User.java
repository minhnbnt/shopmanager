package com.minhnbnt.shopmanager.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@SuperBuilder
public class User {

    private int id;

    private String fullName;
    private Date birth;
    private String email;
    private String phoneNumber;
    private String address;

    private String username;
    private String password;

    private String role;
}
