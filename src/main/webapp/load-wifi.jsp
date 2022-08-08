<%@ page import="DAO.WifiDao" %>
<%@ page import="API.ApiController" %>
<%@ page import="DB.DbController" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<html>
<head>
    <link rel="stylesheet" href="/style.css" type="text/css" media="screen" />
    <title>WIFI 정보 가져오기</title>
</head>
<body>
<div style="display: flex; justify-content: center; align-items: center; flex-direction: column">
    <%
        Class.forName("org.sqlite.JDBC");
        ApiController apiController = new ApiController();
        out.write("<h1>"+ apiController.getAllPage() + " 개 의 정보를 정상적으로 저장 하였습니다." +"</h1>");

        WifiDao wifiDao = new WifiDao();
        wifiDao.insertWifiInfo();

    %>
    <div>
        <a href="/index.jsp"> 홈으로가기</a>
    </div>
</div>
</body>
</html>