package com.minhnbnt.shopmanager.servlets;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequestScoped
@WebServlet("/saleAgent/functions")
public class SaleAgentFunctionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        var username = req.getSession().getAttribute("username");

        if (username instanceof String) {

            var functions = Map.of(
                "Review order",
                "/saleAgent/billSearching"
            );

            req.setAttribute("username", username);
            req.setAttribute("functions", functions);
            req
                .getRequestDispatcher("/WEB-INF/pages/common/functions.jsp")
                .forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
