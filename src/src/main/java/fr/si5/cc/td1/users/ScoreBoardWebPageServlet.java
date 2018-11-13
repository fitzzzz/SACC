package fr.si5.cc.td1.users;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ScoreBoard", value = "/scoreboardweb")
public class ScoreBoardWebPageServlet extends HttpServlet{
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.getUsersOrderByScore();
        req.setAttribute("users", users);          // Part of the Header in form.jsp
        req.getRequestDispatcher("WEB-INF/template/scoreboard.jsp").forward(req, resp);
    }
}
