package it.distributedsystems.web;

import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.ejb.CartBean;
import it.distributedsystems.model.ejb.EJB3CartBean;

/**
 * CartServlet
 */
@WebServlet(name = "CartServlet", urlPatterns = { "cartServlet" }, loadOnStartup = 1)
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        CartBean cartBeanLocal = (CartBean) session.getAttribute("CartBeanReference");
        if (cartBeanLocal == null) {
            try {
                Context context = new InitialContext();
                cartBeanLocal = (CartBean) context.lookup("java:global/distributed-systems-demo/distributed-systems-demo.war/"+EJB3CartBean.class.getSimpleName()+"!"+CartBean.class.getName());
                session.setAttribute("CartBeanReference", cartBeanLocal);
            } catch (NamingException ne) {
                ne.printStackTrace();
            }
        }
        String operation = request.getParameter("operation");
        if (operation.equals("insertProductToCart")) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cartBeanLocal.addProduct(productId);
        } else if (operation.equals("deleteProductFromCart")) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cartBeanLocal.deleteProduct(productId);
        }else if (operation.equals("persistCart")) {
            String customerName=request.getParameter("customerName");
            cartBeanLocal.persistPurchase(customerName);
            session.setAttribute("customerName", customerName);
        }
        request.setAttribute("cart", cartBeanLocal.getCart());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cart.jsp");
        dispatcher.forward(request, response);

    }

}