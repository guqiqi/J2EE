package servlet;

import entity.OrderBean;
import serviceImpl.OrderServiceImpl;
import serviceImpl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // match user and password
//        boolean isCorrectPassword = new UserServiceImpl().Login(String.valueOf(request.getParameter("")),
//                String.valueOf(request.getParameter("password")));
        OrderBean orderBean = new OrderServiceImpl().placeOrder(new int[]{1, 2});

        if (null != orderBean) {
            HttpSession session = request.getSession(false);
            session.setAttribute("orderBean", orderBean);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/successPlaceOrderPage.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/failPlaceOrderPage.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // match user and password
//        boolean isCorrectPassword = new UserServiceImpl().Login(String.valueOf(request.getParameter("")),
//                String.valueOf(request.getParameter("password")));
        OrderBean orderBean = new OrderServiceImpl().placeOrder(new int[]{1, 2});

        if (null != orderBean) {
            HttpSession session = request.getSession(false);
            session.setAttribute("orderBean", orderBean);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/successPlaceOrderPage.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/failPlaceOrderPage.jsp");
            dispatcher.forward(request, response);
        }
    }
}
