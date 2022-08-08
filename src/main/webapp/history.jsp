<%@ page import="DTO.HistoryDto" %>
<%@ page import="DAO.HistoryDao" %>
<%@ page import="java.util.List" %>

<<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>WIFI | 기록</title>
    <link rel="stylesheet" href="/style.css" type="text/css" media="screen"/>
</head>
<body>
<div>
    <H1>위치 히스토리 목록</H1>
</div>
<div style="margin-bottom: 1em">
    <span>
        <a href="index.jsp">
            홈
        </a>
    </span>
    <span class="navigation_divder">|</span>
    <span>
        <a href="/history.jsp">
            위치 히스토리 목록
        </a>
    </span>
    <span class="navigation_divder">|</span>
    <span>
        <a href="/load-wifi.jsp">
            Open API 와이파이 정보 가져오기
        </a>
    </span>
</div>
<table>
    <tr>
        <th>ID</th>
        <th>X 좌표</th>
        <th>Y 좌표</th>
        <th>작업일자</th>
        <th>비고</th>
    </tr>
    <%
        Class.forName("org.sqlite.JDBC");
        HistoryDao historyDao = new HistoryDao();
        List<HistoryDto> list = historyDao.selectHistory();

        for(HistoryDto x : list){
            out.write("<tr>");
            out.write("<td>"+x.getId()+"</td>");
            out.write("<td>"+x.getLAT()+"</td>");
            out.write("<td>"+x.getLNT()+"</td>");
            out.write("<td>"+x.getDATE()+"</td>");
            out.write("<td>");
            out.write("<button id="+ x.getId()+" class=btn_history_delete>"+"삭제"+"</button>");
            out.write("</td>");
            out.write("</tr>");
        }
    %>
</table>
<script type="text/javascript">
    const btn = document.querySelectorAll(".btn_history_delete");

    const delete_button = (e)=>{
        e.preventDefault();
        console.log(e.target);
        window.location.href = "WEB-INf/history.jsp?id="+e.target.id;
    }

    const main= ()=>{
        btn.forEach(x=>x.addEventListener("click",delete_button));
    }
    main();
</script>
</body>
</html>