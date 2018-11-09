package fr.si5.cc.td1;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.DateTimeZone;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormat;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormatter;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import fr.si5.cc.td1.files.File;
import fr.si5.cc.td1.files.FileDao;
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
import java.util.ArrayList;
import java.util.Arrays;
//import com.google.cloud.storage.*;

@MultipartConfig
@WebServlet(name = "CreateServlet", value = "/create")
public class CreateServlet extends HttpServlet {


    private static final Storage storage;
    private static final String BUCKET_NAME = "projet-sacc-bucket";
    private UserDao userDao = new UserDao();
    private FileDao fileDao = new FileDao();

    static {
        storage = StorageOptions.getDefaultInstance().getService();

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        req.setAttribute("action", "Create");          // Part of the Header in form.jsp
        req.setAttribute("destination", "/create");  // The urlPattern to invoke (this Servlet)
//        req.setAttribute("page", "form");           // Tells base.jsp to include form.jsp
        req.getRequestDispatcher("WEB-INF/template/create.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String email = req.getParameter("email");
        User user = userDao.getUserByLogin(email);
        if (user == null) {
            resp.sendError(400, "User does not exists.");
        } else {
            Part filePart = req.getPart("file");

            String link = this.uploadFile(filePart);
            fileDao.save(new File(user.getLogin(), filePart.getName(), link));
            resp.sendRedirect("/");
        }

    }


    /**
     * Uploads a file to Google Cloud Storage to the bucket specified in the BUCKET_NAME
     * environment variable, appending a timestamp to end of the uploaded filename.
     */
    @SuppressWarnings("deprecation")
    private String uploadFile(Part filePart) throws IOException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        String dtString = dt.toString(dtf);
        final String fileName = filePart.getSubmittedFileName() + dtString;

        // the inputstream is closed by default, so we don't need to close it here
        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(CreateServlet.BUCKET_NAME, fileName)
                                // Modify access list to allow all users with link to read file
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Role.READER))))
                                .build(),
                        filePart.getInputStream());
        // return the public download link
        return blobInfo.getMediaLink();
    }

}