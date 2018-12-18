package servlet;

import entity.Item;
import entity.ItemListBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/showItemList")
public class ShowItemListServlet extends HttpServlet {
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context ctx = new InitialContext();
            ds= (DataSource) ctx.lookup("java:comp/env/jdbc/orders");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (null == session)
            request.getRequestDispatcher("/page/notLoginPage.jsp").forward(request, response);
//        int pageNow = 1;
//        int pageCount;
//        int pageSize = 5;
//        String temp_pageNow = request.getParameter("pageNow");
//        if (temp_pageNow != null) {
//            pageNow = Integer.parseInt(temp_pageNow);
//        }
//        int rowCount = orderService.findTotalOrder(String.valueOf(request.getAttribute("login")));
//        pageCount = (rowCount - 1) / pageSize + 1;
        List<Item> list = new ArrayList<Item>();
        try {
            Connection connection = ds.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM item");

            while (rs.next()){
                list.add(new Item(rs.getString("name"), rs.getDouble("price")));
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ItemListBean itemListBean = new ItemListBean(list);

        session.setAttribute("itemList", itemListBean);
//        session.setAttribute("pageNow", pageNow);
//        session.setAttribute("pageCount", pageCount);


        request.getRequestDispatcher("/page/showItems.jsp").forward(request, response);
    }
}
