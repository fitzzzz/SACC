package fr.si5.cc.td1.users;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "User", value = "/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new Gson().fromJson(req.getReader(), User.class);
        UserDao userDao = new UserDao();
        Entity userEntity = userDao.persistUser(user);

        resp.setContentType("text/plain");
        resp.getWriter().print("User created : " + userEntity.getKey());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        List<User> users = userDao.getUsers();
        resp.getWriter().print(new Gson().toJson(users));
    }
}
