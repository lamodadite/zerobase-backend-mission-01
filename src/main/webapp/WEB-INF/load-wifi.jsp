<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="DAO.WifiDao" %>
<%@ page import="DB.DbController" %>
<%@ page import="API.ApiController" %>


<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<div style="display: flex; justify-content: center; align-items: center; flex-direction: column">
    <%
        Class.forName("org.sqlite.JDBC");
        WifiDao wifiDao = new WifiDao();
        wifiDao.insertWifiInfo();
        ApiController apiController = new ApiController();
        out.write("<h1>"+ apiController.getAllPage() + " 개 의 정보를 정상적으로 저장 하였습니다.." +"</h1>");
    %>
    <div>
        <a href="../index.jsp"> 🏠 으로가기</a>
    </div>
</div>
</body>
</html>