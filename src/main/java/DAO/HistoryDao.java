package DAO;

import DB.DbController;
import DTO.HistoryDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class HistoryDao extends DbController {
    public void InsertHistory (double lat, double lnt) throws SQLException {

        String query = "INSERT OR REPLACE INTO HISTORY (X_POINT, Y_POINT, SEARCHDATE) VALUES (?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        String searchDate = now.toString();
        dbConnect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            preparedStatement.setString(3, searchDate);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbDisconnect();
        }
    }
    public List<HistoryDto> selectHistory(){
        List<HistoryDto> list = new ArrayList<>();
        String query = "SELECT * FROM HISTORY;";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                HistoryDto historyDto = new HistoryDto();
                historyDto.setId(Integer.parseInt(rs.getString("id")));
                historyDto.setLAT(Double.parseDouble(rs.getString("X_POINT")));
                historyDto.setLNT(Double.parseDouble(rs.getString("Y_POINT")));
                historyDto.setDATE(rs.getString("SEARCHDATE"));
                list.add(historyDto);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            dbDisconnect();
        }

        return list;
    }
}
