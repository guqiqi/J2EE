<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error</title>
</head>
<body>
<p>用户名或者密码错误!请重新登录!</p>
<form method='GET' action='<%=response.encodeURL(request.getContextPath())%>/login'>
    <input type='submit' name='return' value='return'>
</form>
<jsp:include page="numberStatistic.jsp"/>
</body>
</html>