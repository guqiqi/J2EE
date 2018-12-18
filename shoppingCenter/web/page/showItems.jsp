<%@ page import="entity.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.ItemListBean" %>
<%--
  Created by IntelliJ IDEA.
  User: kiki
  Date: 2018-12-15
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<body>

<div class="row margin-top-20">
    <form id="listForm">
        <table class="table">
            <thead>
            <tr>
                <th><input type="checkbox" value="all" onClick="selectAll('listForm','all')"></th>
                <th>名称</th>
                <th>价格</th>
            </tr>
            </thead>

            <%
                ItemListBean bean = (ItemListBean) session.getAttribute("itemList");
                List<Item> list = bean.getItemList();
                list.add(new Item("橘子", 40));
                list.add(new Item("apple", 500));
                for (Item item : list) {%>
            <tr>
                <td><input type="checkbox" name="favorite"
                           onClick="selectOne('listForm', <%=list.indexOf(item)%>)"></td>
                <td><%=item.getName()%>
                </td>
                <td><%=item.getPrice()%>
                </td>
            </tr>
            <%}%>
        </table>

        <input type="button" value="提交" onclick="submit('listForm')">
    </form>
</div>

<script language="JavaScript" type="text/JavaScript">
  var checkedAll = false;
  var selected = [];

  /* 全选/取消全选 */
  function selectAll(formName) {
    var form = document.getElementById(formName);
    for (var i = 0; i < form.length; i++) {
      var e = form[i];
      e.checked = !checkedAll;
    }
    checkedAll = !checkedAll;
  }

  function selectOne(formName, index) {
    var size =<%=((ItemListBean) session.getAttribute("itemList")).getItemList().size()%>

    var form = document.getElementById(formName);

    // 原来是全选的
    if (selected.length === size) {
      checkedAll = false;
      form[0].checked = false;
    }

    for (var i = 0; i < selected.length; i++) {
      if (selected[i] === index) {
        selected.splice(index, 1);
        form[index] = !form[i].checked;
        return
      }
    }

    form[index + 1].checked = true;
    selected.push(index);

    // 选择之后变成全选
    if (selected.length === size) {
      checkedAll = true;
      form[0].checked = true;
    }
  }


  /* 检查是否选择内容
  */
  function submit(formName) {
    console.log('hhhhh');
    window.location.href="http://localhost:8099/ShoppingCenter/Login";

    console.log(selected.length);
    var form = document.getElementById(formName);
    form.action="Login";
    form.submit();
  }
</script>
</body>
</html>
