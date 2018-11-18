package fr.si5.cc.td1.download;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import fr.si5.cc.td1.mailer.Mailer;
import fr.si5.cc.td1.users.DecrementUserCurrentUsageTask;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "NoobDownload", value = "/noob/worker")
public class DownloadNoobServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(DownloadNoobServlet.class.getName());

    private final int NOOB_DOWNLOAD_LIMIT = 1;
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = request.getParameter("user");
        String blob = request.getParameter("bloblink");
        execute(mail, blob);
    }

    public void execute(String email, String blobLink) {
        UserDao userDao = new UserDao();
        User user = userDao.getUserByLogin(email);

        if (userCanDownload(user)) {
            new Mailer().sendMail(user.getLogin(), "Polyshare - Download Link","Download link : " + blobLink);
            user.incrementCurrentUsage();
            userDao.updateEntity(user);
            scheduleDecrementCurrentUsageTask(user);
        } else {
            new Mailer().sendMail(user.getLogin(), "Polyshare - Download Link","lol non noob");
        }
    }

    private void scheduleDecrementCurrentUsageTask(User user) {
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(
                TaskOptions.Builder.withPayload(new DecrementUserCurrentUsageTask(user.getLogin()))
                        .etaMillis(System.currentTimeMillis() + (1000 * 60)));
    }

    private boolean userCanDownload(User user) {
        return user.getCurrentUsage() < NOOB_DOWNLOAD_LIMIT;
    }


}
