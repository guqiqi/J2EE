package servlet;

import entity.Item;
import entity.ItemListBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.OrderService;

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
    private static int pageSize = 6;
    private static OrderService orderService;
    private static ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        super.init();

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        orderService = (OrderService) applicationContext.getBean("orderService");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (null == session)
            request.getRequestDispatcher("/page/notLoginPage.jsp").forward(request, response);

        ArrayList<String> selectedList = new ArrayList<String>();
        if(session.getAttribute("selectedList")!=null){
            selectedList = (ArrayList<String>)session.getAttribute("selectedList");
        }

        String[] itemLists = request.getParameter("itemList").split(",");

        for(int i = 0; i < itemLists.length; i++){
            if(!selectedList.contains(itemLists[i]))
                selectedList.add(itemLists[i]);
        }

        session.setAttribute("selectedList", selectedList);

        getList(session, request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (null == session)
            request.getRequestDispatcher("/page/notLoginPage.jsp").forward(request, response);

        getList(session, request, response);
    }

    private void getList(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageCount;
        String temp_pageNow = request.getParameter("page");
        if (temp_pageNow != null) {
            page = Integer.parseInt(temp_pageNow);
        }

        ArrayList<String> selectedList = new ArrayList<String>();
        if(session.getAttribute("selectedList")!=null){
            selectedList = (ArrayList<String>)session.getAttribute("selectedList");
        }

        List<Item> list = orderService.getItems();

        pageCount = (list.size() - 1) / pageSize + 1;

        ArrayList<String> selected = new ArrayList<String>();

        List<Item> tempList = new ArrayList<Item>();
        for(int i = 0; i < pageSize; i++){
            if((page-1)*pageSize + i < list.size()){
                tempList.add(list.get((page-1)*pageSize + i));
                if(selectedList.contains(list.get((page-1)*pageSize + i).getName()))
                    selected.add(list.get((page-1)*pageSize + i).getName());
            }
        }

        ItemListBean itemListBean = new ItemListBean(tempList);

        session.setAttribute("selected", selected);
        session.setAttribute("itemList", itemListBean);
        session.setAttribute("page", page);
        session.setAttribute("pageCount", pageCount);

        request.getRequestDispatcher("/page/showItems.jsp").forward(request, response);
    }
}
