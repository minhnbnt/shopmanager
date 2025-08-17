package com.minhnbnt.shopmanager.models;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {

    private long id;
    private String username;
    private String passwordHash;
    private String email;
    private Timestamp createdAt;

    public static User fromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password_hash"),
            resultSet.getString("email"),
            resultSet.getTimestamp("created_at")
        );
    }
}
