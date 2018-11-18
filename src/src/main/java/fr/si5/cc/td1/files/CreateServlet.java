package fr.si5.cc.td1.files;

import com.google.cloud.storage.BlobInfo;
import fr.si5.cc.td1.mailer.Mailer;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@MultipartConfig
@WebServlet(name = "CreateServlet", value = "/create")
public class CreateServlet extends HttpServlet {

    private UserDao userDao = new UserDao();
    private FileDao fileDao = new FileDao();

    private static final long[] DELAY = {5, 10, 30};


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        req.setAttribute("action", "Create");          // Part of the Header in form.jsp
        req.setAttribute("destination", "http://projet-sacc-si5.appspot.com/create");  // The urlPattern to invoke (this Servlet)
//        req.setAttribute("page", "form");           // Tells base.jsp to include form.jsp
        req.getRequestDispatcher("WEB-INF/template/create.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("utf-8");
        String email = req.getParameter("email");
        User user = userDao.getUserByLogin(email);
        if (user == null) {
            resp.sendError(400, "User does not exists.");
        } else {
            Part filePart = req.getPart("file");

            if (filePart == null) {
                resp.sendError(400, "thing to upload.");
            } else {
                FileStorage fileStorage = FileStorage.getInstance();
                BlobInfo blobInfo = fileStorage.uploadFile(filePart);
                File file = new File(user.getLogin(), blobInfo.getName(), blobInfo.getMediaLink(), blobInfo.getName());
                fileDao.save(file);
                DeleteFile.getInstance().deleteFileAfterDelay(file, CreateServlet.DELAY[(int) user.getLevel()]);

                user.addUpload(filePart.getSize());
                userDao.updateEntity(user);

                new Mailer().sendMail(user.getLogin(), "Polyshare - Share link",
                        "Veuillez ajouter votre email à la fin du lien. \n" + "http://projet-sacc-si5.appspot.com/download?fileName=" + file.getFileName() +
                                "&userMail=");

                resp.getWriter().print("File uploaded:\"" + blobInfo.getName() + "\"\nA mail was send to the Author : " + user.getLogin());
            }
        }

    }


}
