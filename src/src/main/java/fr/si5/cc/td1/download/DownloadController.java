package fr.si5.cc.td1.download;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import fr.si5.cc.td1.files.File;
import fr.si5.cc.td1.users.DecrementUserCurrentUsageTask;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserDao;
import fr.si5.cc.td1.users.UserLevel;


public class DownloadController {

    private final int NOOB_DOWNLOAD_LIMIT = 1;
    private final int CASU_DOWNLOAD_LIMIT = 2;
    private final int LEET_DOWNLAOD_LIMIT = 4;

    private UserDao userDao = new UserDao();


    public String download(User user, File file) {
        switch (UserLevel.values()[(int)user.getLevel()]) {
            //case NOOB:
              //  return "Not implemented";
            case CASU:
                return downloadCasu(user, file);
            case LEET:
                return downloadLeet(user, file);
            default:
                return downloadCasu(user, file);
                //return "Unknown level";
        }
    }

    private String downloadCasu(User user, File file) {
        if (user.getCurrentUsage() < CASU_DOWNLOAD_LIMIT) {
            return executeDownload(user, file);
        } else {
            return "Casu can't downlaod";
        }
    }

    private String downloadLeet(User user, File file) {
        if (user.getCurrentUsage() < LEET_DOWNLAOD_LIMIT) {
            return executeDownload(user, file);
        } else {
            return "LEET can't download";
        }
    }

    private String executeDownload(User user, File file) {
        User userSynchronized = userDao.getUserByLogin(user.getLogin());
        userSynchronized.incrementCurrentUsage();
        userDao.updateEntity(userSynchronized);
        scheduleDecrementCurrentUsageTask(user);
        return file.getBlobLink();
    }

    private void scheduleDecrementCurrentUsageTask(User user) {
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(
                TaskOptions.Builder.withPayload(new DecrementUserCurrentUsageTask(user.getLogin()))
                        .etaMillis(System.currentTimeMillis() + (1000 * 60)));
    }

}
