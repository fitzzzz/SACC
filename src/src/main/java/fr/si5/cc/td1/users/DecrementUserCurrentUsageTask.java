package fr.si5.cc.td1.users;

import com.google.appengine.api.taskqueue.DeferredTask;

public class DecrementUserCurrentUsageTask implements DeferredTask {

    private final String userId;

    public DecrementUserCurrentUsageTask(String userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        UserDao userDao = new UserDao();
        User userSynchronized = userDao.getUserByLogin(userId);
        System.out.println("[DecrementUserCurrentUsageTask] user : " + userId
                + "new current usage : " + userSynchronized.decrementCurrentUsage());
        userDao.updateEntity(userSynchronized);
    }

}
