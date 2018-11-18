package fr.si5.cc.td1.files;

import com.google.cloud.storage.BlobInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreatFakeFile", value = "/createFakeFile")
public class CreateFakeFileServlet extends HttpServlet {
    private FileStorage fileStorage = FileStorage.getInstance();
    private FileDao fileDao = new FileDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        int size = Integer.parseInt(req.getParameter("size"));
        String userName = req.getParameter("userName");

        BlobInfo info = fileStorage.createFakeFile(fileName, size);

        fileDao.save(new File(userName, info.getName(), info.getMediaLink(), info.getName()));

        resp.getWriter().print("http://projet-sacc-si5.appspot.com/download?fileName=" + info.getName() + "&userMail=");

    }
}
