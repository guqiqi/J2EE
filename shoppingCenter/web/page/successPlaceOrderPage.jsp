<%@ page import="entity.OrderBean" %>
<%@ page import="java.text.DecimalFormat" %><%--
  Created by IntelliJ IDEA.
  User: kiki
  Date: 2018-12-18
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>下单成功</title>
</head>
<body>
<p>成功下单</p>
<% OrderBean bean = (OrderBean) session.getAttribute("orderBean");%>
<p>此笔原价订单<%=new DecimalFormat("#.00").format(bean.getTotal())%>元，优惠<%=new DecimalFormat("#.00").format(bean.getDiscount())%>元，
    实际需要支付<%=new DecimalFormat("#.00").format(bean.getPay())%>元</p>
<form method='GET' action='<%=response.encodeURL(request.getContextPath())%>/showItemList'>
    <input type='submit' name='return' value='再来一单'>
</form>
<form method='GET' action='<%=response.encodeURL(request.getContextPath())%>/login'>
    <input type='submit' name='return' value='登出'>
</form>
</body>
</html>
