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
    private static int pageSize = 6;


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

        pageCount = (list.size() - 1) / pageSize + 1;

        List<Item> tempList = new ArrayList<Item>();
        for(int i = 0; i < pageSize; i++){
            if((page-1)*pageSize + i < list.size()){
                tempList.add(list.get((page-1)*pageSize + i));
            }
        }

        ItemListBean itemListBean = new ItemListBean(tempList);

        session.setAttribute("itemList", itemListBean);
        session.setAttribute("page", page);
        session.setAttribute("pageCount", pageCount);

        request.getRequestDispatcher("/page/showItems.jsp").forward(request, response);
    }
}
