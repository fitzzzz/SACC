package fr.si5.cc.td1.download;

import fr.si5.cc.td1.files.File;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserLevel;

public class DownloadController {

    private final int NOOB_DOWNLOAD_LIMIT = 1;
    private final int CASU_DOWNLOAD_LIMIT = 2;
    private final int LEET_DOWNLAOD_LIMIT = 4;


    public String download(User user, File file) {
        switch (UserLevel.values()[(int)user.getLevel()]) {
            case NOOB:
                return "Not implemented";
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
            return "Casu can download";
        } else {
            return "Casu can't downlaod";
        }
    }

    private String downloadLeet(User user, File file) {
        if (user.getCurrentUsage() < LEET_DOWNLAOD_LIMIT) {
            return "LEET can download";
        } else {
            return "LEET can't download";
        }
    }


}
