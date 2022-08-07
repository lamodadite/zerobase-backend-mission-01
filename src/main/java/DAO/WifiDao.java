package DAO;

import API.ApiController;
import DB.DbController;
import DTO.WifiDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


public class WifiDao extends DbController {
    public void insertWifiInfo () throws SQLException {
        dbConnect();
        connection.setAutoCommit(false);
        ApiController apiController = new ApiController();
        List<List<WifiDto>> tmp = apiController.getWifiData();
        String query = "INSERT OR REPLACE INTO WIFI VALUES(0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            for (int i = 0; i < tmp.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                for (int j = 0; j < tmp.get(i).size(); j++) {
                    WifiDto w = tmp.get(i).get(j);
                    preparedStatement.setString(1, w.getX_SWIFI_MGR_NO());
                    preparedStatement.setString(2, w.getX_SWIFI_WRDOFC());
                    preparedStatement.setString(3, w.getX_SWIFI_MAIN_NM());
                    preparedStatement.setString(4, w.getX_SWIFI_ADRES1());
                    preparedStatement.setString(5, w.getX_SWIFI_ADRES2());
                    preparedStatement.setString(6, w.getX_SWIFI_INSTL_FLOOR());
                    preparedStatement.setString(7, w.getX_SWIFI_INSTL_TY());
                    preparedStatement.setString(8, w.getX_SWIFI_INSTL_MBY());
                    preparedStatement.setString(9, w.getX_SWIFI_SVC_SE());
                    preparedStatement.setString(10, w.getX_SWIFI_CMCWR());
                    preparedStatement.setString(11, w.getX_SWIFI_CNSTC_YEAR());
                    preparedStatement.setString(12, w.getX_SWIFI_INOUT_DOOR());
                    preparedStatement.setString(13, w.getX_SWIFI_REMARS3());
                    preparedStatement.setDouble(14, w.getLNT());
                    preparedStatement.setDouble(15, w.getLAT());
                    preparedStatement.setString(16, w.getWORK_DTTM());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbDisconnect();
        }
    }

    public List<WifiDto> selectWifiInfo(double lat, double lnt) throws SQLException {
        List<WifiDto> list = new LinkedList<>();
        String query = "SELECT  round(6371 * acos( cos( radians(LAT) )\n" +
                "                               * cos( radians("+ lat +") )\n" +
                "                               * cos( radians( "+ lnt +" ) - radians(LNT) )\n" +
                "                               + sin( radians(LAT) ) * sin( radians( "+ lat +  ") ) ), 4) as distance,*\n" +
                "from WIFI\n" +
                "order by distance\n" +
                "limit 20";
        dbConnect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                WifiDto wifiDto = new WifiDto(
                        rs.getDouble("DISTANCE"),
                        rs.getString("X_SWIFI_MGR_NO"),
                        rs.getString("X_SWIFI_WRDOFC"),
                        rs.getString("X_SWIFI_MAIN_NM"),
                        rs.getString("X_SWIFI_ADRES1"),
                        rs.getString("X_SWIFI_ADRES2"),
                        rs.getString("X_SWIFI_INSTL_FLOOR"),
                        rs.getString("X_SWIFI_INSTL_TY"),
                        rs.getString("X_SWIFI_INSTL_MBY"),
                        rs.getString("X_SWIFI_SVC_SE"),
                        rs.getString("X_SWIFI_CMCWR"),
                        rs.getString("X_SWIFI_CNSTC_YEAR"),
                        rs.getString("X_SWIFI_INOUT_DOOR"),
                        rs.getString("X_SWIFI_REMARS3"),
                        rs.getDouble("LAT"),
                        rs.getDouble("LNT"),
                        rs.getString("WORK_DTTM")
                );
                list.add(wifiDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbDisconnect();
        }
        return list;
    }

}
