package JS;

import DAO.WifiDao;
import DTO.WifiDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/getAroundWifiInfo")
public class AroundWifiInfo extends HttpServlet {

    static class point{
        int x;
        int y;

        public point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static double distance(point p1, point p2){

        double dX = p1.x - p2.x;
        double dY = p1.y - p2.y;

        return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");

        WifiDao wifiDao = new WifiDao();
        List<WifiDto> list;
        try {
            list = wifiDao.selectWifiInfo(Double.parseDouble(lat), Double.parseDouble(lnt));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("list", list);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}