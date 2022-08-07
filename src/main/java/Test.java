import API.ApiController;
import DAO.WifiDao;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) throws SQLException {
        WifiDao wifiDao = new WifiDao();
        System.out.println(wifiDao.selectWifiInfo(3.33, 5.24).toString());
    }
}
