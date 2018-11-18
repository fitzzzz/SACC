package fr.si5.cc.td1.files;

import com.google.cloud.storage.BlobInfo;
import fr.si5.cc.td1.mailer.Mailer;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserDao;

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
    private UserDao userDao = new UserDao();

    private static final long[] DELAY = {5, 10, 30};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("utf-8");
        String email = req.getParameter("email");
        String fileName = req.getParameter("fileName");
        int size = Integer.parseInt(req.getParameter("size"));
        User user = userDao.getUserByLogin(email);
        if (user == null) {
            resp.sendError(400, "User does not exists.");
        } else {

            FileStorage fileStorage = FileStorage.getInstance();
            BlobInfo blobInfo = fileStorage.createFakeFile(fileName, size);
            File file = new File(user.getLogin(), blobInfo.getName(), blobInfo.getMediaLink(), blobInfo.getName());
            fileDao.save(file);
            DeleteFile.getInstance().deleteFileAfterDelay(file, CreateFakeFileServlet.DELAY[(int) user.getLevel()]);

            user.addUpload((long)size);
            userDao.updateEntity(user);

            new Mailer().sendMail(user.getLogin(), "Polyshare - Share link",
                    "Veuillez ajouter votre email à la fin du lien. \n" + "http://projet-sacc-si5.appspot.com/download?fileName=" + file.getFileName() +
                            "&userMail=");

            resp.getWriter().print("File uploaded:\"" + blobInfo.getName() + "\"\nA mail was send to the Author : " + user.getLogin());

        }

    }
}
