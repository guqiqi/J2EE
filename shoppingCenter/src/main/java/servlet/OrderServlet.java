package servlet;

import entity.OrderBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/orders");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (null == session)
            request.getRequestDispatcher("/page/notLoginPage.jsp").forward(request, response);

        request.setCharacterEncoding("utf-8");

        String[] itemLists = request.getParameter("itemList").split(",");

        double total = 0.0;

        try {
            Connection connection = ds.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM item");

            while (rs.next()) {
                for (int i = 0; i < itemLists.length; i++)
                    if (rs.getString("name").equals(itemLists[i]))
                        total += rs.getDouble("price");
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OrderBean orderBean = new OrderBean(total, total > 15 ? total * 0.05 : 0.0);
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
