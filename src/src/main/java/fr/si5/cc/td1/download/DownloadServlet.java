package fr.si5.cc.td1.download;

import com.google.gson.Gson;
import fr.si5.cc.td1.files.FileDao;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Download", value = "/download")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DownloadRequest downloadRequest = new Gson().fromJson(req.getReader(), DownloadRequest.class);

        UserDao userDao = new UserDao();
        User user = userDao.getUserByLogin(downloadRequest.getUserMail());
        

        resp.setContentType("application/json");
        if (user == null) {
            sendUserNotFound(resp);
        } else {
            String response = new DownloadController().download(user, null);
            resp.getWriter().print(response);
        }

    }

    private void sendUserNotFound(HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
    }

}
