package com.minhnbnt.shopmanager.daos;

import com.minhnbnt.shopmanager.models.Product;
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
public class ProductDAO {

    private final DataSource dataSource;

    public List<Product> findByKeyword(String keyword) {
        var sql = """
            SELECT id, name, producer, type, description, price FROM tblProduct
            WHERE name        LIKE CONCAT('%', ?, '%')
               OR description LIKE CONCAT('%', ?, '%')
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, keyword);
            statement.setString(2, keyword);

            var results = new ArrayList<Product>();

            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    var product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .producer(resultSet.getString("producer"))
                        .type(resultSet.getString("type"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .build();

                    results.add(product);
                }
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public Product findById(int id) {
        var sql = """
            SELECT id, name, producer, type, description, price
            FROM tblProduct WHERE id = ?
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            try (var resultSet = statement.executeQuery()) {
                var found = resultSet.next();
                if (!found) {
                    return null;
                }

                return Product.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .producer(resultSet.getString("producer"))
                    .type(resultSet.getString("type"))
                    .description(resultSet.getString("description"))
                    .price(resultSet.getDouble("price"))
                    .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
