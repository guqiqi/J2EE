package servlet;

import factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ServiceFactory serviceFactory = new ServiceFactory();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // match user and password
        System.out.println(request.getParameter("username"));

        boolean isCorrectPassword = serviceFactory.getUserService().Login(request.getParameter("username"), request.getParameter(
                "password"));

        if (isCorrectPassword) {
            String username = String.valueOf(request.getParameter("username"));
            HttpSession session = request.getSession(false);
            session.setAttribute("username", username);
            Cookie cookie = new Cookie("LoginCookie", session.getId());
            cookie.setMaxAge(Integer.MAX_VALUE);
            System.out.println("Add cookie");
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath() + "/showItemList?page=1");
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
