package servlet;

import serviceImpl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // match user and password
        request.setCharacterEncoding("utf-8");
        boolean isCorrectPassword = new UserServiceImpl().Login(String.valueOf(request.getParameter("username")),
                String.valueOf(request.getParameter("password")));

        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("password"));

        if (isCorrectPassword) {
            String username = String.valueOf(request.getParameter("username"));
            HttpSession session = request.getSession(false);
            session.setAttribute("username", username);
            Cookie cookie = new Cookie("LoginCookie", session.getId());
            cookie.setMaxAge(Integer.MAX_VALUE);
            System.out.println("Add cookie");
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath() + "/showItemList");
        }
        else {
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
