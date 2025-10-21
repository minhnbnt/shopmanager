package com.minhnbnt.shopmanager.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class User {

    private int id;

    private String fullName;
    private Date birth;
    private String email;
    private String phoneNumber;
    private String address;

    private String username;
    private String password;
}
