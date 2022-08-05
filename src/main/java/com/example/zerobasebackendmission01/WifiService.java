package com.example.zerobasebackendmission01;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WifiService {
    public int WifiInsert(WifiDto[] wifiDtos) throws SQLException {

        String url = "jdbc:sqlite:C:/dev/DB.Browser.for.SQLite-3.12.2-win64/DB Browser for SQLite/WIFI.db";

        //1. 드라이버 로드
        //2. 커넥션 객체 생성
        //3. 스테이트먼트 객체 생성
        //4. 쿼리 실행
        //5. 결과 수행
        //6. 객체 연결 해제 (close)

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("org.sqlite.JDBC를 찾지못했습니다.");
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        try {
            connection = DriverManager.getConnection(url);


            String sql = "insert or replace into WIFI (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < wifiDtos.length; i++) {
                preparedStatement.setString(1, wifiDtos[i].getMgrNo());
                preparedStatement.setString(2, wifiDtos[i].getWrdofc());
                preparedStatement.setString(3, wifiDtos[i].getMainNm());
                preparedStatement.setString(4, wifiDtos[i].getAdres1());
                preparedStatement.setString(5, wifiDtos[i].getAdres2());
                preparedStatement.setString(6, wifiDtos[i].getInstlFloor());
                preparedStatement.setString(7, wifiDtos[i].getInstlTy());
                preparedStatement.setString(8, wifiDtos[i].getInstlMby());
                preparedStatement.setString(9, wifiDtos[i].getSvcSe());
                preparedStatement.setString(10, wifiDtos[i].getCmcwr());
                preparedStatement.setString(11, wifiDtos[i].getCnstcYear());
                preparedStatement.setString(12, wifiDtos[i].getInoutDoor());
                preparedStatement.setString(13, wifiDtos[i].getRemars3());
                preparedStatement.setDouble(14, wifiDtos[i].getLat());
                preparedStatement.setDouble(15, wifiDtos[i].getLnt());
                preparedStatement.setString(16, wifiDtos[i].getWorkDttm());

                if (preparedStatement.executeUpdate() > 0) {
                    result += 1;
                }
            }
            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
    // 넣은 데이터의 전체 개수를 리턴
    public static int WifiInsertAll() throws SQLException, IOException, ExecutionException, InterruptedException {
        APIController apiController = new APIController();

        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int start = 1, end = 1000;
                int result = 0;
                while (true) {
                    result += apiController.APIConnect(start, end);
                    start = end + 1;
                    end = end + 1000;
                    if (start == 17001) {
                        end = 17795;
                        result += apiController.APIConnect(start, end);
                        break;
                    }
                }
                return result;
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(10);
        return service.submit(task).get();
    }


}
