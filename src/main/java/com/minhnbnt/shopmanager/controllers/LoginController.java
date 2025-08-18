package com.minhnbnt.shopmanager.controllers;

import com.minhnbnt.shopmanager.dtos.LoginFormDto;
import com.minhnbnt.shopmanager.services.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.stream.Collectors;

@RequestScoped
@WebServlet("/login")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoginController extends HttpServlet {

    private final Validator validator;
    private final UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        var form = new LoginFormDto(
            req.getParameter("username"),
            req.getParameter("password")
        );

        var violations = validator.validate(form);

        if (!violations.isEmpty()) {

            var messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));

            resp.setContentType("text/plain");
            resp.getWriter().write(messages);

            return;
        }

        if (!userService.isLoginOk(form)) {
            resp.setContentType("text/plain");
            resp.getWriter().write("Username or password does not matches.");
            return;
        }

        req.getSession().setAttribute("username", form.getUsername());
        resp.sendRedirect(req.getContextPath());
    }
}
