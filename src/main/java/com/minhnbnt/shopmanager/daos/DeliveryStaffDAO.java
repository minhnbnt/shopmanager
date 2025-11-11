package com.minhnbnt.shopmanager.daos;

import com.minhnbnt.shopmanager.models.DeliveryStaff;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

@Dependent
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class DeliveryStaffDAO {

    private final DataSource dataSource;

    public List<DeliveryStaff> listAll() {
        var sql = """
            SELECT u.id, u.username, u.fullName, u.phoneNumber, u.email
            FROM tblDeliveryStaff ds
            INNER JOIN tblUser u ON ds.staffUserId = u.id
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {
            var results = new ArrayList<DeliveryStaff>();

            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    var staff = DeliveryStaff.builder()
                        .id(resultSet.getInt("id"))
                        .username(resultSet.getString("username"))
                        .fullName(resultSet.getString("fullName"))
                        .phoneNumber(resultSet.getString("phoneNumber"))
                        .email(resultSet.getString("email"))
                        .build();

                    results.add(staff);
                }
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public DeliveryStaff getById(int id) {
        var sql = """
            SELECT u.id, u.username, u.fullName, u.phoneNumber, u.email
            FROM tblDeliveryStaff ds
            INNER JOIN tblUser u ON ds.staffUserId = u.id
            WHERE u.id = ?
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            try (var resultSet = statement.executeQuery()) {

                var exists = resultSet.next();
                if (!exists) {
                    return null;
                }

                return DeliveryStaff.builder()
                    .id(resultSet.getInt("id"))
                    .username(resultSet.getString("username"))
                    .fullName(resultSet.getString("fullName"))
                    .phoneNumber(resultSet.getString("phoneNumber"))
                    .email(resultSet.getString("email"))
                    .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
