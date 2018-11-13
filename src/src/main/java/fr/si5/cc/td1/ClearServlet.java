package fr.si5.cc.td1;

import fr.si5.cc.td1.files.FileDao;
import fr.si5.cc.td1.files.FileStorage;
import fr.si5.cc.td1.users.UserDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Clear", value = "/clear")
public class ClearServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private FileDao fileDao = new FileDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        this.clearFiles(writer);
        this.clearUsers(writer);
        writer.println();
    }

    private void clearUsers(PrintWriter writer) {
        userDao.clear();
        writer.append("User cleared.\n");
    }

    private void clearFiles(PrintWriter writer) {
        List<String> blobNames = new ArrayList<>();
        fileDao.findAll().forEach(file -> blobNames.add(file.getBlobStoreId()));
        FileStorage.getInstance().clear(blobNames);
        writer.append("Files deleted.\n");

        fileDao.clear();
        writer.append("Files database cleared.\n");


    }

}
