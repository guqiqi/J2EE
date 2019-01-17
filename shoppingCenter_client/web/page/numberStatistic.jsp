<%--
  Created by IntelliJ IDEA.
  User: kiki
  Date: 2018-12-21
  Time: 08:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    ServletContext Context = session.getServletContext();
    int visitorNum = (Integer) Context.getAttribute("VisitorCount");
    int loginNum = (Integer) Context.getAttribute("OnlineCount");
    int totalNum = visitorNum + loginNum;
%>

<p>当前在线总人数: <%=totalNum%>人, 已登录人数: <%=loginNum%>人, 游客人数: <%=visitorNum%>人</p>
</html>