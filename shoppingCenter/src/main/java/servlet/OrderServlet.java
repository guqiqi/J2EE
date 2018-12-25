package servlet;

import entity.OrderBean;
import serviceImpl.OrderServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
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

        OrderBean orderBean = new OrderServiceImpl().placeOrder(selectedList, username);

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
