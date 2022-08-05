<%@ page language="Java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        table {
            width: 100%;
        }
        th, td {
            border:solid 1px #000;
        }
    </style>
</head>
<body>
<h1>
    <%
        out.write("와이파이 정보 구하기");
    %>
</h1>
<br/>
<a href="index.jsp">홈</a>
<a href="History.jsp">위치 히스토리 목록</a>
<a href="getInfo.jsp">Open API 와이파이 정보 가져오기</a>
</body>
</html>