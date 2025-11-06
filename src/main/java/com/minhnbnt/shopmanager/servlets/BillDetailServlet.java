package com.minhnbnt.shopmanager.servlets;

import com.minhnbnt.shopmanager.dao.BuyBillDAO;
import com.minhnbnt.shopmanager.dao.DeliveryStaffDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@WebServlet("/saleAgent/billDetail")
@RequestScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BillDetailServlet extends HttpServlet {

    private final BuyBillDAO buyBillDAO;
    private final DeliveryStaffDAO deliveryStaffDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        var idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Bill ID is required"
            );
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid bill ID format"
            );
            return;
        }

        var bill = buyBillDAO.getById(id);
        if (bill == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
            return;
        }

        req.setAttribute("bill", bill);
        req.setAttribute("deliveryStaffs", deliveryStaffDAO.listAll());

        req
            .getRequestDispatcher("/WEB-INF/pages/saleAgents/billDetail.jsp")
            .forward(req, resp);
    }
}
