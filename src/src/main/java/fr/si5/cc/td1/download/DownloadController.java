package fr.si5.cc.td1.download;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;
import fr.si5.cc.td1.files.File;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserLevel;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class DownloadController {



    public String download(User user, File file) {
        switch (UserLevel.values()[(int)user.getLevel()]) {
            //case NOOB:
              //  return "Not implemented";
            case CASU:
                return downloadOther(user, file);
            case LEET:
                return downloadOther(user, file);
            default:
                return downloadOther(user, file);
                //return "Unknown level";
        }
    }

    private String downloadOther(User user, File file) {
        Queue q = QueueFactory.getQueue("download-casu-leet");

        DownloadPayloadWrapper wrapper = new DownloadPayloadWrapper(user.getLogin(), file.getBlobLink());
        String payload = new Gson().toJson(wrapper);
        q.add(
                TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(payload));

        consumeDownloadQueue();
        return "Demande de lien prise en compte";
    }

    public void consumeDownloadQueue() {
        Queue q = QueueFactory.getQueue("download-casu-leet");
        List<TaskHandle> tasks = q.leaseTasks(3600, TimeUnit.SECONDS, 1);
        if (tasks.size() == 1) {
            TaskHandle task = tasks.get(0);
            String payload = new String(task.getPayload());
            DownloadPayloadWrapper wrapper = new Gson().fromJson(payload, DownloadPayloadWrapper.class);
            new DownloadPullQueueHandler().execute(wrapper.getMail(), wrapper.getBlobLink());
            q.deleteTask(task);
        }
    }


}
