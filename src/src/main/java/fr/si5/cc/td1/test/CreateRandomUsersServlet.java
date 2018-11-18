package fr.si5.cc.td1.test;

import com.google.gson.Gson;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "RandomUsers", value = "/randomUsers")
public class CreateRandomUsersServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userNb = null;
        try {

            userNb = Long.valueOf(req.getParameter("userNb"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "'" + req.getParameter("userNb") + "' is not a number");
        }
        String baseEMail = req.getParameter("baseEmail");

        if (baseEMail == null || "".equals(baseEMail)) {
            resp.sendError(400, "Missing 'baseEmail' parameter.");
        } else {
            String[] email = baseEMail.split("@");
            if (email.length < 2) {
                resp.sendError(400, "Email format is incorrect");
            }
            List<User> users = new ArrayList<>();
            for (int i = 0; i < userNb; i++) {
                int dataUploaded = new Random().nextInt(300);
                long level = 0;
                if (dataUploaded > 100) level = 1;
                if (dataUploaded > 201) level = 2;
                User user = new User(email[0] + '+' + i + email[1], "123456", level, dataUploaded, 0);
                userDao.persistUser(user);
                users.add(user);
            }

            resp.getWriter().print("Created users :\n" + new Gson().toJson(users));

        }

    }
}
