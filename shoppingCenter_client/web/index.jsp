<%--
  Created by IntelliJ IDEA.
  User: kiki
  Date: 2018-12-15
  Time: 09:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
  </head>
  <body>
  <p>您尚未登录，请前往登录本网站</p>
  <form method='GET' action='<%=response.encodeURL(request.getContextPath())%>/login'>
    <input type='submit' name='return' value='登录'>
  </form>
  </body>
</html>
