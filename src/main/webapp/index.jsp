<%@ page import="DTO.WifiDto" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.HistoryDao" %>
<%@ page import="DB.DbController" %>


<%@ page import="API.ApiController" %>
<%@ page import="DAO.WifiDao" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/style.css" type="text/css"/>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<div>
    <H1>와이파이 정보 구하기</H1>
</div>
<div style="margin-bottom: 1em">
    <span>
        <a href="${pageContext.request.contextPath}/index.jsp">
            홈
        </a>
    </span>
    <span class="navigation_divder">|</span>
    <span>
        <a href="${pageContext.request.contextPath}/history.jsp">
            위치 히스토리 목록
        </a>
    </span>
    <span class="navigation_divder">|</span>
    <span>
        <a href="${pageContext.request.contextPath}/load-wifi.jsp">
            Open API 와이파이 정보 가져오기
        </a>
    </span>
</div>
<div class="btn_div">
    <span>Lat : </span>
    <input class="coordinate_box crd_latitude" placeholder="0.0">
    <span class="btn_div_lnt">LNT : </span>
    <input class="coordinate_box crd_longitude" placeholder="0.0"/>
    <button class="btn get_current_position">내 위치 가져오기</button>
    <button class="btn get_around_wifi">근처 WIPI 정보 보기</button>
</div>
<div style="overflow-x:auto;">
    <table class="table table-striped table-bordered">
        <thead class="thead-light">
            <tr>
                <th>거리(Km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명주소</th>
                <th>상세주소</th>
                <th>설치위치(층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스구분</th>
                <th>망종류</th>
                <th>설치년도</th>
                <th>실내외구분</th>
                <th>WIFI접속환경</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>작업일자</th>
            </tr>
        </thead>
        <tbody>
        <%
            String lat = request.getParameter("lat");
            String lng = request.getParameter("lng");
            if(lat != null && lng != null){
                Class.forName("org.sqlite.JDBC");
                WifiDao wifiDao = new WifiDao();
                HistoryDao historyDao = new HistoryDao();
                List<WifiDto> list = wifiDao.selectWifiInfo(Double.parseDouble(lat),Double.parseDouble(lng));
                for(WifiDto x : list){
                    out.write("<tr>");
                    out.write("<td>"+x.getDistance()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_MGR_NO()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_WRDOFC()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_MAIN_NM()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_ADRES1()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_ADRES2()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_INSTL_FLOOR()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_INSTL_TY()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_INSTL_MBY()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_SVC_SE()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_CMCWR()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_CNSTC_YEAR()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_INOUT_DOOR()+"</td>");
                    out.write("<td>"+x.getX_SWIFI_REMARS3()+"</td>");
                    out.write("<td>"+x.getLAT()+"</td>");
                    out.write("<td>"+x.getLNT()+"</td>");
                    out.write("<td>"+x.getWORK_DTTM()+"</td>");
                    out.write("</tr>");
                }
                historyDao.InsertHistory(Double.parseDouble(lat),Double.parseDouble(lng));
            }
        %>
        </tbody>
    </table>
    <%
        if(lat == null || lng == null){
            out.write("<h4 class="+"info_home"+">"+ "위치 정보를 입력한 후에 조회해 주세요."+"</h4>");
        }
    %>
</div>
<script type="text/javascript">
    const getCurrentMyPosition = document.querySelector(".get_current_position");
    const getAroundWifi = document.querySelector(".get_around_wifi");
    const coordinateBox = document.querySelectorAll(".coordinate_box");
    const latitudeBox = document.querySelector(".crd_latitude");
    const longitudeBox = document.querySelector(".crd_longitude");

    const options = {
        enableHighAccuracy: true,
    };

    const error =  (err) => {
        console.warn(`ERROR(${err.code}): ${err.message}`);
        getCurrentMyPosition.disabled = false;
        getCurrentMyPosition.classList.remove("noHover");
    }

    const sucess = async (pos)=>{
        const crd = await pos.coords;
        latitudeBox.value = crd.latitude;
        longitudeBox.value = crd.longitude;
        coordinateBox.forEach(e=>e.style.color = "#00a587");
    }

    const getCurrentLocation = (e)=>{
        navigator.geolocation.getCurrentPosition(sucess,error,options);
    }

    const handleClick =(e)=>{
        e.preventDefault();
        e.target.disabled = true;
        getCurrentLocation();
        e.target.classList.add("noHover");
    }

    const handleGetAroundWifi =(e)=>{
        e.preventDefault();
        if(latitudeBox.value.length <=0 || longitudeBox.value.length <= 0){
            return null;
        }
        window.location.href = "index.jsp?lat="+latitudeBox.value+"&lng="+longitudeBox.value;
    }

    const main = ()=>{
        const params = new URLSearchParams(window.location.search);
        for(const param of params){
            param[0] == 'lat' ?latitudeBox.value = param[1] :null;
            param[0] == 'lng' ? longitudeBox.value = param[1]:null;
        }
        getCurrentMyPosition.addEventListener("click",handleClick);
        getAroundWifi.addEventListener("click",handleGetAroundWifi);
    }
    main();
</script>
</body>
</html>