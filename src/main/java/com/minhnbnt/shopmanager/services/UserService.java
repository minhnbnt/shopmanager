package com.minhnbnt.shopmanager.services;

import com.minhnbnt.shopmanager.dtos.LoginFormDto;
import com.minhnbnt.shopmanager.dtos.RegisterFormDto;
import com.minhnbnt.shopmanager.models.User;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Dependent
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserService {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    private Optional<User> getUserByUsername(String username) {

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
                    return Optional.of(
                        User.fromResultSet(resultSet)
                    );
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(RegisterFormDto dto)
        throws SQLIntegrityConstraintViolationException {

        var sql = """
            INSERT INTO users (username, password_hash, email)
            VALUES (?, ?, ?)
        """;

        var passwordHash = passwordEncoder.encode(dto.getPassword());

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, dto.getUsername());
            statement.setString(2, passwordHash);
            statement.setString(3, dto.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {

            if (e instanceof SQLIntegrityConstraintViolationException e2) {
                throw e2;
            }

            throw new RuntimeException(e);
        }
    }

    public boolean isLoginOk(LoginFormDto dto) {

        var user = getUserByUsername(dto.getUsername());
        //noinspection OptionalIsPresent
        if (user.isEmpty()) {
            return false;
        }

        return passwordEncoder.matches(
            dto.getPassword(),
            user.get().getPasswordHash()
        );
    }

    public Optional<User> getUserBySession(HttpSession session) {
        if (session.getAttribute("username") instanceof String username) {
            return getUserByUsername(username);
        } else {
            return Optional.empty();
        }
    }
}
