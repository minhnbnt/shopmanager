package com.minhnbnt.shopmanager.dao;


import com.minhnbnt.shopmanager.models.User;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.SQLException;

@Dependent
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserDAO {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public boolean checkLogin(User user) {

        var sql = "SELECT password FROM tblUser WHERE username = ?";

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, user.getUsername());

            try (var resultSet = statement.executeQuery()) {

                var userExists = resultSet.next();
                if (!userExists) {
                    return false;
                }

                var hashedPassword = resultSet.getString("password");
                return passwordEncoder.matches(
                    user.getPassword(),
                    hashedPassword
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
