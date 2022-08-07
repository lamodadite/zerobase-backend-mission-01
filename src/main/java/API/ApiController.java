package API;

import DTO.WifiDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ApiController {
    int allPage;
    JsonArray[] jsonArray;

    public ApiController() {
        allPage = getAllPage();
        jsonArray = new JsonArray[allPage/1000+1];
//        Arrays.fill(jsonArray, new JsonArray());
    }

    class ApiRunnable implements Runnable {
        int start;
        int end;

        public ApiRunnable(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public synchronized void run() {
            try {
                URL url = new URL("http://openapi.seoul.go.kr:8088/7261674c556e6d62383770515a7a61/json/TbPublicWifiInfo/" + start + "/" + end + "/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");
                conn.setDoOutput(true);
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String result = rd.readLine();

                    JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
                    JsonArray wifiDataArr = jsonObject.get("TbPublicWifiInfo").getAsJsonObject().get("row").getAsJsonArray();
                    jsonArray[(start - 1) / 1000] = wifiDataArr;
                } else {
                    System.out.println("api 연결에 실패했습니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int getAllPage() {
        int page = 0;
        try {
            URL url = new URL("http://openapi.seoul.go.kr:8088/7261674c556e6d62383770515a7a61/json/TbPublicWifiInfo/1/5");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = rd.readLine();

                JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
                String data = jsonObject.get("TbPublicWifiInfo").getAsJsonObject().get("list_total_count").getAsString();
                page = Integer.parseInt(data);
                System.out.println(page);
            } else {
                System.out.println("전체 페이지를 받아오는데 실패했습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
    public List<List<WifiDto>> getWifiData(){
        Gson gson = new Gson();
        ThreadGroup threadGroup = new ThreadGroup("group1");
        List<List<WifiDto>> WifiDtoList = new LinkedList<>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < jsonArray.length; i++) {
            start = i*1000 + 1;
            end = start + 999;
            ApiRunnable apiRunnable = new ApiRunnable(start,end);
            Thread thread = new Thread(threadGroup, apiRunnable);
            thread.start();
        }

        while (threadGroup.activeCount()>0) {

        }

        for (int i = 0; i < jsonArray.length; i++) {
            List<WifiDto> tmp = new LinkedList<>();
            for (int j = 0; j < jsonArray[i].size(); j++) {
                WifiDto tmpWifiDto = gson.fromJson(jsonArray[i].get(j), WifiDto.class);
                tmp.add(tmpWifiDto);
                System.out.println(tmp.toString());
            }
            WifiDtoList.add(tmp);
        }
        return WifiDtoList;
    }

}
