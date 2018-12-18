package servlet;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // match user and password
        request.setCharacterEncoding("utf-8");
        boolean isCorrectPassword = false;

        try {
            Connection connection = ds.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");

            while (rs.next()){
                if(rs.getString("username").equals(request.getParameter("username")) && rs.getString("password").equals(request.getParameter("password"))) {
                    isCorrectPassword = true;
                    rs.close();
                    stmt.close();
                    connection.close();
                    break;
                }
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isCorrectPassword) {
            String username = String.valueOf(request.getParameter("username"));
            HttpSession session = request.getSession(false);
            session.setAttribute("username", username);
            Cookie cookie = new Cookie("LoginCookie", session.getId());
            cookie.setMaxAge(Integer.MAX_VALUE);
            System.out.println("Add cookie");
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath() + "/showItemList");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/page/wrongPage.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }
        request.getRequestDispatcher("/page/login.jsp").forward(request, response);
    }
}
