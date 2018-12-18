package servlet;

import entity.Item;
import entity.ItemListBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/showItemList")
public class ShowItemListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNow = 1;
        int pageCount;
        int pageSize = 5;
//        String temp_pageNow = request.getParameter("pageNow");
//        if (temp_pageNow != null) {
//            pageNow = Integer.parseInt(temp_pageNow);
//        }
//        int rowCount = orderService.findTotalOrder(String.valueOf(request.getAttribute("login")));
//        pageCount = (rowCount - 1) / pageSize + 1;

        List<Item> list = new ArrayList<Item>();
        list.add(new Item("橘子", 20));
        list.add(new Item("apple", 200));

        ItemListBean itemListBean = new ItemListBean(list);
        HttpSession session = request.getSession(false);
        if(null == session)
            request.getRequestDispatcher("/page/notLoginPage.jsp").forward(request, response);

        session.setAttribute("itemList", itemListBean);
        session.setAttribute("pageNow", pageNow);
//        session.setAttribute("pageCount", pageCount);

        try {
            request.getRequestDispatcher("/page/showItems.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("/page/notLoginPage.jsp").forward(request, response);
        }
    }
}
