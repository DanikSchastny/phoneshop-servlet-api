package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.CartService.*;
import com.es.phoneshop.logic.ViewedProducts;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;

public class ProductListPageDetailsServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) {
        ProductDao productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String productCode = uri.substring(uri.lastIndexOf("/") + 1);
        Product product = productDao.getProduct(Long.parseLong(productCode));
        req.setAttribute("product", product);
        req.setAttribute("quantity_answer", req.getParameter("quantity_answer"));
        req.setAttribute("quantity", req.getParameter("quantity"));
        HttpSession session = req.getSession();
        ViewedProducts viewedProducts = (ViewedProducts) session.getAttribute("viewed_products");
        if (viewedProducts == null) {
            viewedProducts = new ViewedProducts();
            viewedProducts.add(product);
            session.setAttribute("viewed_products", viewedProducts);

        } else {
            viewedProducts.add(product);
            session.removeAttribute("viewed_products");
            session.setAttribute("viewed_products", viewedProducts);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Product product = productDao.getProduct(Long.parseLong(req.getParameter("product_id")));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        if (cart == null) {
            cart = new Cart();
            cart.save(new CartItem(product, quantity));
            session.setAttribute("cart", cart);

            String path = req.getContextPath() + "/products/" + req.getParameter("product_id") + "?quantityAnswer=" + "Product was added!" + "&quantity=" + quantity;
            resp.sendRedirect(path);

        } else {

            CartService cartService = new CartService(cart);
            CartItem cartItem = new CartItem(product, quantity);

            try {
                cartService.add(cartItem);
            } catch (RuntimeException ex) {
                String path = req.getContextPath() + "/products/" + req.getParameter("product_id") + "?quantityAnswer=" + "Not enough stock" + "&quantity=" + quantity;
                resp.sendRedirect(path);
            }

            session.removeAttribute("cart");
            session.setAttribute("cart", cartService.getCart());

            String path = req.getContextPath() + "/products/" + req.getParameter("product_id") + "?quantityAnswer=" + "Product was added!" + "&quantity=" + quantity;
            resp.sendRedirect(path);
        }
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }
}

