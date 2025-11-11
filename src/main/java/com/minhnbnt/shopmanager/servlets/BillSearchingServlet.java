package com.minhnbnt.shopmanager.servlets;

import com.minhnbnt.shopmanager.daos.BuyBillDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RequestScoped
@WebServlet("/saleAgent/billSearching")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BillSearchingServlet extends HttpServlet {

    private final BuyBillDAO buyBillDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        var username = req.getSession().getAttribute("username");
        var isAuthenticated = username instanceof String;

        if (!isAuthenticated) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        var bills = buyBillDAO.listPendingBills();

        req.setAttribute("searchResult", bills);

        req
            .getRequestDispatcher("/WEB-INF/pages/saleAgents/billSearching.jsp")
            .forward(req, resp);
    }
}
