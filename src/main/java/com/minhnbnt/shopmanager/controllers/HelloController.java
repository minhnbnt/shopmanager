package com.minhnbnt.shopmanager.controllers;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

@ApplicationScoped
@WebServlet("/hello")
public class HelloController extends HttpServlet {

    @Resource(lookup = "jdbc/myDB")
    private DataSource myDB;

    private String getName() {

        var query = "SELECT 'world' AS name";

        try (
            var connection = myDB.getConnection();
            var statement = connection.prepareStatement(query);
            var result = statement.executeQuery()
        ) {

            result.next();
            return result.getString("name");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", getName());
        req.getRequestDispatcher("/WEB-INF/templates/hello.jsp").forward(req, resp);
    }
}
