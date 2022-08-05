<%@ page import="com.example.zerobasebackendmission01.WifiService" %>
<%@ page language="Java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.google.gson.Gson" %>
<%--String.valueOf(WifiService.WifiInsertAll())--%>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>
    <%
        out.write("개의 WIFI 정보를 정상적으로 저장하였습니다.");
    %>
</h1>
<div>
    <a href="index.jsp"> 홈으로 가기 </a>
</div>
</body>
</html>