package com.minhnbnt.shopmanager.servlets;

import com.minhnbnt.shopmanager.dao.UserDAO;
import com.minhnbnt.shopmanager.models.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@RequestScoped
@WebServlet("/login")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        var user = User.builder()
            .username(req.getParameter("username"))
            .password(req.getParameter("password"))
            .build();

        var errorMessage = "Username or password does not match.";

        try {

            var isLoginOk = userDAO.checkLogin(user);

            if (isLoginOk) {
                req.getSession().setAttribute("username", user.getUsername());
                resp.sendRedirect(req.getContextPath() + "/customer/functions");
                return;
            }

        } catch (RuntimeException e) {

            var stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));

            errorMessage = stringWriter.toString();
        }

        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }
}
