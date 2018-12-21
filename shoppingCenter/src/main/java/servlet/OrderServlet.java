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
import java.sql.*;
import java.util.ArrayList;

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

        String username = (String) session.getAttribute("username");

        if(request.getParameter("itemList").equals("")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/failPlaceOrderPage.jsp");
            dispatcher.forward(request, response);
        }

        String[] itemLists = request.getParameter("itemList").split(",");

        ArrayList<String> selectedList = new ArrayList<String>();
        if(session.getAttribute("selectedList")!=null){
            selectedList = (ArrayList<String>)session.getAttribute("selectedList");
        }

        for(int i = 0; i < itemLists.length; i++){
            if(!selectedList.contains(itemLists[i]))
                selectedList.add(itemLists[i]);
        }

        double total = 0.0;

        try {
            Connection connection = ds.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM item");

            while (rs.next()) {
                for (int i = 0; i < selectedList.size(); i++) {
                    if (selectedList.contains(rs.getString("name"))) {
                        total += rs.getDouble("price");
                        selectedList.remove(rs.getString("name"));
                    }
                }
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OrderBean orderBean = new OrderBean(total, total > 15 ? total * 0.05 : 0.0);

        try {
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders.order (username, " +
                    "total, discount, pay) VALUES(?,?,?,?);");
            preparedStatement.setString(1, username);
            preparedStatement.setDouble(2, orderBean.getTotal());
            preparedStatement.setDouble(3, orderBean.getDiscount());
            preparedStatement.setDouble(4, orderBean.getPay());
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (null != orderBean) {
            session.removeAttribute("selectedList");
            session.removeAttribute("selected");

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
