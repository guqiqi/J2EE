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
                <th><input type="checkbox" value="all" onClick="selectAll()"></th>
                <th>名称</th>
                <th>价格</th>
            </tr>
            </thead>

            <%
                ItemListBean bean = (ItemListBean) session.getAttribute("itemList");
                List<Item> list = bean.getItemList();
                for (Item item : list) {%>
            <tr>
                <td><input type="checkbox" id="<%=item.getName()%>"
                           onClick="selectOne(<%=item.getName()%>)"></td>
                <td><%=item.getName()%>
                </td>
                <td><%=item.getPrice()%>
                </td>
            </tr>
            <%}%>
        </table>

        <%
            int pageNow = (Integer) session.getAttribute("page");
            int pageCount = (Integer) session.getAttribute("pageCount");
            if (pageNow != 1) {
        %>
        <a href='<%=response.encodeURL(request.getContextPath())%>/showItemList?page=<%=pageNow - 1%>'>上一页</a>
        <%}%>
        <%if (pageNow != pageCount) {%>
        <a href='<%=response.encodeURL(request.getContextPath())%>/showItemList?page=<%=pageNow + 1%>'>下一页</a>
        <%}%>

        <input type="button" value="提交" onClick="placeOrder()">
    </form>
    <jsp:include page="numberStatistic.jsp"/>
</div>

<script language="JavaScript" type="text/JavaScript">
  var checkedAll = false;
  var selected = [];

  /* 全选/取消全选 */
  function selectAll() {
    var form = document.getElementById('listForm');
    for (var i = 0; i < form.length; i++) {
      var e = form[i];
      e.checked = !checkedAll;
    }
    checkedAll = !checkedAll;
    // if (checkedAll) {
    //   // 判断是不是在里面，不在的话就加进去
    //   for (var i = 1; i < form.length - 1; i++) {
    //     var e = form[i].id;
    //     if (selected.indexOf(e, 0) == -1)
    //       selected.push(e);
    //   }
    //   console.log(selected)
    // } else {
    //   // 判断是不是在里面，在的话就拿进去
    //   for (var i = 1; i < form.length - 1; i++) {
    //     var e = form[i].id;
    //     var start = selected.indexOf(e, 0);
    //     if (start != -1)
    //       selected.slice(start, start + 1);
    //   }
    //   console.log(selected)
    // }
    if (checkedAll) {
      // 先清空，然后一个一个加
      selected = [];
      for (var i = 1; i < form.length - 1; i++) {
        var e = form[i].id;
        selected.push(e);
      }
      console.log(selected)
    } else
      selected = [];
  }

  function selectOne(name) {
    var size =
    <%=((ItemListBean) session.getAttribute("itemList")).getItemList().size()%>

    var form = document.getElementById("listForm");

    // 这边传过来的是整个dom节点！！！
    console.log(name);

    // 原来是全选的
    if (selected.length === size) {
      checkedAll = false;
      form[0].checked = false;
    }

    // 取消选择
    for (var i = 0; i < selected.length; i++) {
      if (selected[i] === name) {
        selected.splice(i, 1);
        form[i].checked = !form[i].checked;
        return
      }
    }

    // 选择
    name.checked = true;
    selected.push(name.id);

    // 选择之后变成全选
    if (selected.length === size) {
      checkedAll = true;
      form[0].checked = true;
    }
  }

  /* 检查是否选择内容
  */
  function placeOrder() {
    if (selected.length === 0) {
      alert("您的购物车为空, 请添加物品后提交!");
      return
    }
    var myForm = document.createElement("form");
    var params = {"itemList": selected};
    myForm.method = "POST";
    myForm.action = "<%=response.encodeURL(request.getContextPath())%>/order";
    myForm.style.display = "none";
    for (var k in params) {
      var myInput = document.createElement("input");
      myInput.name = k;
      myInput.value = params[k];
      myForm.appendChild(myInput);
    }
    document.body.appendChild(myForm);
    myForm.submit();
  }
</script>
</body>
</html>
