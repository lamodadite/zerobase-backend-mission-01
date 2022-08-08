import API.ApiController;
import DAO.HistoryDao;
import DAO.WifiDao;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) throws SQLException {
        HistoryDao historyDao = new HistoryDao();
        historyDao.deleteHistory();
    }
}
