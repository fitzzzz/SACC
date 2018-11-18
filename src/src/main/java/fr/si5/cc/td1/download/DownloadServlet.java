package fr.si5.cc.td1.download;

import fr.si5.cc.td1.files.File;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userMail = req.getParameter("userMail");
        String fileName = req.getParameter("fileName");
        DownloadRequest downloadRequest = new DownloadRequest(userMail, fileName);

        UserDao userDao = new UserDao();
        User user = userDao.getUserByLogin(downloadRequest.getUserMail());

        FileDao fileDao = new FileDao();
        File file = fileDao.findByFilename(downloadRequest.getFileName());

        resp.setContentType("application/json");
        if (user == null) {
            sendUserNotFound(resp);
        } else if (file == null) {
            sendFileNotFound(resp);
        } else {
            String response = new DownloadController().download(user, file);
            resp.getWriter().print(response);
        }
    }


    private void sendFileNotFound(HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
    }

    private void sendUserNotFound(HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
    }

}
