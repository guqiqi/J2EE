<%--
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
    <title>下单失败</title>
</head>
<body>
<p>下单失败，请返回重试</p>
<form method='GET' action='<%=response.encodeURL(request.getContextPath())%>/showItemList'>
    <input type='submit' name='返回' value='return'>
</form>
</body>
</html>
