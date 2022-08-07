package DB;

import java.beans.*;
import java.sql.*;
import java.sql.Statement;

public class DbController {
    protected static Connection connection;
    protected Statement statement;

    protected void dbConnect() throws SQLException {
        String url = "jdbc:sqlite:C:/dev/DB.Browser.for.SQLite-3.12.2-win64/DB Browser for SQLite/WIFI.db";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("org.sqlite.JDBC를 찾지못했습니다.");
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("DB 연결에 실패하였습니다.");
            throw new RuntimeException();
        }
    }

    protected void dbDisconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
