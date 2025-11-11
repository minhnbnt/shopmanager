package com.minhnbnt.shopmanager.servlets;

import com.minhnbnt.shopmanager.daos.ProductDAO;
import com.minhnbnt.shopmanager.models.Product;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequestScoped
@WebServlet("/customer/productSearching")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var username = req.getSession().getAttribute("username");

        if (!(username instanceof String)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        var keyword = req.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        System.out.println(keyword);

        List<Product> results = List.of();
        if (!keyword.isBlank()) {
            results = productDAO.findByKeyword(keyword);
        }

        req.setAttribute("keyword", keyword);
        req.setAttribute("searchResult", results);

        req.getRequestDispatcher("/WEB-INF/pages/customers/productSearching.jsp")
            .forward(req, resp);
    }
}
