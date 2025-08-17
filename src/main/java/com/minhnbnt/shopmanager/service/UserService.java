package com.minhnbnt.shopmanager.service;

import com.minhnbnt.shopmanager.dtos.LoginFormDto;
import com.minhnbnt.shopmanager.models.User;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.SQLException;

@Dependent
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserService {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Nullable
    private User getUserByUsername(String username) {

        var sql = """
            SELECT id, username, password_hash, email, created_at
            FROM users WHERE username = ?
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return User.fromResultSet(resultSet);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLoginOk(LoginFormDto dto) {

        var user = getUserByUsername(dto.getUsername());
        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(
            dto.getPassword(),
            user.getPasswordHash()
        );
    }

    @Nullable
    public User getUserBySession(HttpSession session) {
        if (session.getAttribute("username") instanceof String username) {
            return getUserByUsername(username);
        } else {
            return null;
        }
    }
}
