package fr.si5.cc.td1.files;

import com.google.cloud.storage.BlobInfo;
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

                fileDao.save(new File(user.getLogin(), filePart.getSubmittedFileName(), blobInfo.getMediaLink(), blobInfo.getName()));
                fileStorage.deleteFileAfterDelay(blobInfo.getName(), CreateServlet.DELAY[(int) user.getLevel()]);

                user.addUpload(filePart.getSize());


                resp.getWriter().print("File uploaded:\"" + blobInfo.getName() + "\"\nAuthor : " + user.getLogin());
            }
        }

    }


}
