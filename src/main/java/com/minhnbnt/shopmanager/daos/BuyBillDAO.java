package com.minhnbnt.shopmanager.daos;

import com.minhnbnt.shopmanager.models.*;
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
public class BuyBillDAO {

    private final DataSource dataSource;

    public List<BuyBill> listPendingBills() {
        var sql = """
            SELECT
                bb.id,
                bb.createdOn,
                u.fullName as customerName,
                COALESCE((
                    SELECT SUM(bd.quantity * p.price)
                    FROM tblBuyDetail bd
                    INNER JOIN tblProduct p ON bd.productId = p.id
                    WHERE bd.buyBillId = bb.id
                ), 0) as total
            FROM tblBuyBill bb
            INNER JOIN tblCustomer c ON bb.customerId = c.id
            INNER JOIN tblUser u ON c.userId = u.id
            WHERE bb.deliveryStaffId IS NULL
            ORDER BY bb.id DESC
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {
            var results = new ArrayList<BuyBill>();

            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    var customer = Customer.builder().build();
                    customer.setFullName(resultSet.getString("customerName"));

                    var bill = BuyBill.builder()
                        .id(resultSet.getInt("id"))
                        .createOn(resultSet.getDate("createdOn"))
                        .customer(customer)
                        .total(resultSet.getDouble("total"))
                        .build();

                    results.add(bill);
                }
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public BuyBill getById(int id) {
        var fetchBillDetailQuery = """
            SELECT
                bd.quantity,
                p.name as productName,
                p.price,
                (bd.quantity * p.price) as subTotal
            FROM tblBuyDetail bd
            INNER JOIN tblProduct p ON bd.productId = p.id
            WHERE bd.buyBillId = ?
        """;

        var details = new ArrayList<BuyDetail>();

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(fetchBillDetailQuery)
        ) {
            statement.setInt(1, id);

            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    var product = Product.builder()
                        .name(resultSet.getString("productName"))
                        .build();

                    var detail = BuyDetail.builder()
                        .quantity(resultSet.getInt("quantity"))
                        .price(resultSet.getDouble("price"))
                        .product(product)
                        .subTotal(resultSet.getDouble("subTotal"))
                        .build();

                    details.add(detail);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var fetchBillSql = """
            SELECT
                bb.id,
                bb.createdOn,
                bb.receivedOn,
                u.fullName AS customerName,
                u.address AS customerAddress,
                u.phoneNumber AS customerPhone,
                ds.fullName AS deliveryStaffName,
                ds.phoneNumber AS deliveryStaffPhoneNumber
            FROM tblBuyBill bb
            INNER JOIN tblUser u ON bb.customerId = u.id
            LEFT JOIN tblUser ds ON bb.deliveryStaffId = ds.id
            WHERE bb.id = ?
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(fetchBillSql)
        ) {
            statement.setInt(1, id);

            try (var resultSet = statement.executeQuery()) {
                var found = resultSet.next();
                if (!found) {
                    return null;
                }

                var deliveryStaffPhoneNumber = resultSet.getString(
                    "deliveryStaffPhoneNumber"
                );

                var deliveryStaffExists = deliveryStaffPhoneNumber != null;

                DeliveryStaff deliveryStaff = null;
                if (deliveryStaffExists) {
                    deliveryStaff = DeliveryStaff.builder().build();
                    deliveryStaff.setPhoneNumber(deliveryStaffPhoneNumber);
                    deliveryStaff.setFullName(
                        resultSet.getString("deliveryStaffName")
                    );
                }

                var customer = Customer.builder().build();
                customer.setFullName(resultSet.getString("customerName"));
                customer.setAddress(resultSet.getString("customerAddress"));
                customer.setPhoneNumber(resultSet.getString("customerPhone"));

                var receivedOn = resultSet.getDate("receivedOn");
                var isDelivered = receivedOn != null;

                var status = "Pending";
                if (deliveryStaffExists) {
                    status = "In transit";
                }
                if (isDelivered) {
                    status = "Delivered";
                }

                var total = details
                    .stream()
                    .mapToDouble(BuyDetail::getSubTotal)
                    .sum();

                return BuyBill.builder()
                    .createOn(resultSet.getDate("createdOn"))
                    .deliveryStaff(deliveryStaff)
                    .id(resultSet.getInt("id"))
                    .receivedOn(receivedOn)
                    .customer(customer)
                    .details(details)
                    .status(status)
                    .total(total)
                    .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public void reviewBill(BuyBill buyBill) {

        var sql = """
            UPDATE tblBuyBill
            SET deliveryStaffId = ?,
                saleAgentId = (
                    SELECT u.id
                    FROM tblUser u
                    WHERE u.username = ?
                )
            WHERE id = ?
        """;

        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, buyBill.getDeliveryStaff().getId());
            statement.setString(2, buyBill.getSubmitter().getUsername());
            statement.setInt(3, buyBill.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
