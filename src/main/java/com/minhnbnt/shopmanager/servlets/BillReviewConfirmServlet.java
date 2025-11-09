package com.minhnbnt.shopmanager.servlets;

import com.minhnbnt.shopmanager.dao.BuyBillDAO;
import com.minhnbnt.shopmanager.dao.DeliveryStaffDAO;
import com.minhnbnt.shopmanager.models.BuyBill;
import com.minhnbnt.shopmanager.models.DeliveryStaff;
import com.minhnbnt.shopmanager.models.SaleAgent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequestScoped
@WebServlet("/saleAgent/billReview")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BillReviewConfirmServlet extends HttpServlet {

    private final BuyBillDAO buyBillDAO;
    private final DeliveryStaffDAO deliveryStaffDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        var billIdParam = req.getParameter("billId");
        var deliveryStaffIdParam = req.getParameter("deliveryStaffId");

        if (billIdParam == null || billIdParam.trim().isEmpty()) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Bill ID is required"
            );
            return;
        }

        if (
            deliveryStaffIdParam == null ||
                deliveryStaffIdParam.trim().isEmpty()
        ) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Delivery Staff ID is required"
            );
            return;
        }

        int billId;
        int deliveryStaffId;

        try {
            billId = Integer.parseInt(billIdParam);
            deliveryStaffId = Integer.parseInt(deliveryStaffIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid ID format"
            );
            return;
        }

        var bill = buyBillDAO.getById(billId);
        if (bill == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
            return;
        }

        var deliveryStaff = deliveryStaffDAO.getById(deliveryStaffId);
        if (deliveryStaff == null) {
            resp.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "Delivery Staff not found"
            );
            return;
        }

        if (!bill.getStatus().equals("Pending")) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Bill is not pending"
            );
            return;
        }

        req.setAttribute("bill", bill);
        req.setAttribute("deliveryStaff", deliveryStaff);

        req
            .getRequestDispatcher(
                "/WEB-INF/pages/saleAgents/billReviewConfirm.jsp"
            )
            .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        var billIdParam = req.getParameter("billId");
        var deliveryStaffIdParam = req.getParameter("deliveryStaffId");

        if (billIdParam == null || billIdParam.trim().isEmpty()) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Bill ID is required"
            );
            return;
        }

        if (
            deliveryStaffIdParam == null ||
                deliveryStaffIdParam.trim().isEmpty()
        ) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Delivery Staff ID is required"
            );
            return;
        }

        int billId;
        int deliveryStaffId;

        try {
            billId = Integer.parseInt(billIdParam);
            deliveryStaffId = Integer.parseInt(deliveryStaffIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid ID format"
            );
            return;
        }

        var username = req.getSession().getAttribute("username");
        var isLoggedIn = username instanceof String;
        if (!isLoggedIn) {
            resp.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "User not logged in"
            );
            return;
        }


        var submitter = SaleAgent.builder().build();
        submitter.setUsername((String) username);

        var deliveryStaff = DeliveryStaff.builder()
            .id(deliveryStaffId)
            .build();

        var billToReview = BuyBill.builder()
            .id(billId)
            .deliveryStaff(deliveryStaff)
            .submitter(submitter)
            .build();

        buyBillDAO.reviewBill(billToReview);

        var url = req.getContextPath() + "/saleAgent/billDetail?id=" + billId;
        resp.sendRedirect(url);
    }
}
