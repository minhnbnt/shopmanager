package com.minhnbnt.shopmanager.controllers;

import com.minhnbnt.shopmanager.dtos.RegisterFormDto;
import com.minhnbnt.shopmanager.services.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@WebServlet("/signup")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SignupController extends HttpServlet {

    private final Validator validator;
    private final UserService userService;

    private Optional<String> getErrorMessage(RegisterFormDto dto) {

        var violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"))
                .describeConstable();
        }

        try {
            userService.createUser(dto);
        } catch (SQLIntegrityConstraintViolationException e) {
            var message = String.format("Failed to create user: %s", e.getMessage());
            return Optional.of(message);
        }

        return Optional.empty();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {

        var form = new RegisterFormDto(
            req.getParameter("username"),
            req.getParameter("email"),
            req.getParameter("password")
        );

        var error = getErrorMessage(form);
        if (error.isPresent()) {

            req.setAttribute("validationErrors", error.get());

            req.getRequestDispatcher("/signup.jsp")
                .forward(req, resp);

            return;
        }

        req.getSession().setAttribute("username", form.getUsername());
        resp.sendRedirect(req.getContextPath());
    }
}
