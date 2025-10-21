package com.minhnbnt.shopmanager.servlets;

import com.minhnbnt.shopmanager.dao.ProductDAO;
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
@WebServlet("/customer/product/*")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ProductDetailServlet extends HttpServlet {

    private final ProductDAO productDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.getWriter().println("No product ID provided");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.getWriter().println("Product with provided id not found.");
            return;
        }

        var product = productDAO.findById(id);
        if (product == null) {
            resp.getWriter().println("Product with provided id not found.");
            return;
        }

        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/pages/customers/productDetail.jsp")
            .forward(req, resp);
    }
}
