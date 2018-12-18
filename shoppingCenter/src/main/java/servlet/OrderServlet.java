package servlet;

import entity.OrderBean;
import serviceImpl.OrderServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if(null == session)
            request.getRequestDispatcher("/page/notLoginPage.jsp").forward(request, response);

        String[] indexes = request.getParameterValues("itemList");
        int[] itemIndex = new int[indexes.length];
        for(int i = 0; i < indexes.length; i++){
            itemIndex[i] = Integer.parseInt(indexes[i]);
        }
        OrderBean orderBean = new OrderServiceImpl().placeOrder(itemIndex);

        if (null != orderBean) {
            session.setAttribute("orderBean", orderBean);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/successPlaceOrderPage.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/failPlaceOrderPage.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
