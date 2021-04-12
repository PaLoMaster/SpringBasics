package ru.geekbrains.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/prod_servlet")
public class ProductServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(ProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        List<Product> prodList = new ArrayList<>(10);
        prodList.add(new Product(0, "Zero", 111));
        prodList.add(new Product(1, "One", 100));
        prodList.add(new Product(2, "Two", 103));
        prodList.add(new Product(3, "Three", 102));
        prodList.add(new Product(4, "Four", 120));
        prodList.add(new Product(5, "Five", 130));
        prodList.add(new Product(6, "Six", 136));
        prodList.add(new Product(7, "Seven", 120));
        prodList.add(new Product(8, "Eight", 114));
        prodList.add(new Product(9, "Nine", 132));
        resp.getWriter().println("<h3>" + prodList.toString() + "</h3>");
    }
}
