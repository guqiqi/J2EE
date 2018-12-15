<%@ page import="entity.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.ItemListBean" %><%--
  Created by IntelliJ IDEA.
  User: kiki
  Date: 2018-12-15
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<body>

<div class="row margin-top-20">
    <table class="table">
        <thead>
        <tr>
            <th>名称</th>
            <th>价格</th>
        </tr>
        </thead>

        <%
            ItemListBean bean = (ItemListBean) session.getAttribute("itemList");
            List<Item> list = bean.getItemList();
            list.add(new Item("橘子", 40));
            list.add(new Item("apple", 500));
        for(Item tl:list)
      {%>
            <tr>
                <%--<td>${loop.index + 1}</td>--%>
                <td><%=tl.getName()%></td>
                <td><%=tl.getPrice()%></td>
            </tr>
        <%}%>
    </table>
</div>
</body>
</html>
