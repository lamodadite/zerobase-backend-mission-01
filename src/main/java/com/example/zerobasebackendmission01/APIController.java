package com.example.zerobasebackendmission01;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;

public class APIController {
    public int APIConnect(int start, int end) throws IOException, SQLException {
        String START_INDEX = String.valueOf(start);
        String END_INDEX = String.valueOf(end);

        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("7261674c556e6d62383770515a7a61", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(START_INDEX, "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode(END_INDEX, "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setDoOutput(true);
        // System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        JsonObject jsonData = (JsonObject) JsonParser.parseString(sb.toString());
        JsonObject TbPublicWifiInfo = (JsonObject) jsonData.get("TbPublicWifiInfo");
        JsonArray jsonArray = (JsonArray) TbPublicWifiInfo.get("row");

        WifiService wifiService = new WifiService();
        WifiDto[] wifiDtos = new WifiDto[end - start + 1];

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jo = (JsonObject) jsonArray.get(i);
            WifiDto tmp = new WifiDto();
            tmp.setMgrNo(jo.get("X_SWIFI_MGR_NO").getAsString());
            tmp.setWrdofc(jo.get("X_SWIFI_WRDOFC").getAsString());
            tmp.setMainNm(jo.get("X_SWIFI_MAIN_NM").getAsString());
            tmp.setAdres1(jo.get("X_SWIFI_ADRES1").getAsString());
            tmp.setAdres2(jo.get("X_SWIFI_ADRES2").getAsString());
            tmp.setInstlFloor(jo.get("X_SWIFI_INSTL_FLOOR").getAsString());
            tmp.setInstlTy(jo.get("X_SWIFI_INSTL_TY").getAsString());
            tmp.setInstlMby(jo.get("X_SWIFI_INSTL_MBY").getAsString());
            tmp.setSvcSe(jo.get("X_SWIFI_SVC_SE").getAsString());
            tmp.setCmcwr(jo.get("X_SWIFI_CMCWR").getAsString());
            tmp.setCnstcYear(jo.get("X_SWIFI_CNSTC_YEAR").getAsString());
            tmp.setInoutDoor(jo.get("X_SWIFI_INOUT_DOOR").getAsString());
            tmp.setRemars3(jo.get("X_SWIFI_REMARS3").getAsString());
            tmp.setLat(jo.get("LAT").getAsDouble());
            tmp.setLnt(jo.get("LNT").getAsDouble());
            tmp.setWorkDttm(jo.get("WORK_DTTM").getAsString());
            wifiDtos[i] = tmp;
        }

        int r = wifiService.WifiInsert(wifiDtos);
        return r;
    }
}
