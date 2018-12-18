<%--
  Created by IntelliJ IDEA.
  User: kiki
  Date: 2018-12-15
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method='POST' action="<%=response.encodeURL(request.getContextPath())%>/login">
    用户名: <input type='text' name='username' value=''>
    密码: <input type='password' name='password' value=''>
    <input type='submit' name='Submit' value='提交'>
</body>
</html>
