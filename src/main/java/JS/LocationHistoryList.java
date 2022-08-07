package JS;

import DAO.HistoryDao;
import DTO.HistoryDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/locationHistoryList")
public class LocationHistoryList extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HistoryDao historyDao = new HistoryDao();

        List<HistoryDto> list = historyDao.selectHistory();

        request.setAttribute("list", list);
        request.getRequestDispatcher("WEB-INF/history.jsp").forward(request, response);
    }
}
