package fr.si5.cc.td1.download;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;
import fr.si5.cc.td1.mailer.Mailer;
import fr.si5.cc.td1.users.DecrementUserCurrentUsageTask;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserDao;

import static fr.si5.cc.td1.users.UserLevel.LEET;

public class DownloadPullQueueHandler {

    private final int CASU_DOWNLOAD_LIMIT = 2;
    private final int LEET_DOWNLAOD_LIMIT = 4;

    private UserDao userDao = new UserDao();

    public void execute(String email, String blobLink) {
        UserDao userDao = new UserDao();
        User user = userDao.getUserByLogin(email);

        if (userCanDownload(user)) {
            sendLinkByMail(user, blobLink);
        } else {
            pushTaskAgain(email, blobLink);
        }
    }

    private void pushTaskAgain(String mail, String blobLink) {
        System.out.println("Task push again to pull queue 'download-casu-leet'");

        Queue q = QueueFactory.getQueue("download-casu-leet");
        DownloadPayloadWrapper wrapper = new DownloadPayloadWrapper(mail, blobLink);
        String payload = new Gson().toJson(wrapper);
        q.add(
                TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(payload));
    }

    private void sendLinkByMail(User user, String blobLink) {
        user.incrementCurrentUsage();
        userDao.updateEntity(user);
        scheduleDecrementCurrentUsageTask(user);
        new Mailer().sendMail(user.getLogin(), "Polyshare - Download Link","Download link : " + blobLink);
    }

    private void scheduleDecrementCurrentUsageTask(User user) {
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(
                TaskOptions.Builder.withPayload(new DecrementUserCurrentUsageTask(user.getLogin()))
                        .etaMillis(System.currentTimeMillis() + (1000 * 60)));
    }

    private boolean userCanDownload(User user) {
        int userUsageLimit = getUserUsageLimit(user);

        return user.getCurrentUsage() < userUsageLimit;
    }


    private int getUserUsageLimit(User user) {
        return user.getLevel() == LEET.ordinal() ?  LEET_DOWNLAOD_LIMIT : CASU_DOWNLOAD_LIMIT;
    }

}