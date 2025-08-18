package com.minhnbnt.shopmanager.controllers;

import com.minhnbnt.shopmanager.services.UserService;
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
@WebServlet("/home")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HomeController extends HttpServlet {

    private final UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var user = userService.getUserBySession(req.getSession());
        req.setAttribute("user", user.orElse(null));

        req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
    }
}
