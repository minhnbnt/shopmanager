package com.minhnbnt.shopmanager.servlets;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RequestScoped
@WebServlet("/customer/functions")
public class CustomerFunctionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var username = req.getSession().getAttribute("username");

        if (username instanceof String) {
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/pages/functions.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
