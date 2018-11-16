package fr.si5.cc.td1.files;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeleteFile {
    private FileDao fileDao;
    private FileStorage fileStorage;

    private final ScheduledExecutorService scheduler;
    private static DeleteFile instance = null;

    private DeleteFile(FileDao fileDao) {
        this.fileDao = fileDao;
        this.fileStorage = FileStorage.getInstance();
        this.scheduler = Executors.newScheduledThreadPool(1);

    }

    public static DeleteFile getInstance() {
        if (instance == null) {
            instance = new DeleteFile(new FileDao());
        }
        return instance;
    }

    public void deleteFileAfterDelay(fr.si5.cc.td1.files.File file, long delay) {
        scheduler.schedule(new DeleteTask(file), delay, TimeUnit.MINUTES);
    }


    public class DeleteTask extends TimerTask {

        private File file;

        public DeleteTask(fr.si5.cc.td1.files.File file) {
            this.file = file;
        }

        @Override
        public void run() {
            fileStorage.deleteFile(file.getBlobName());
            fileDao.delete(file);
        }
    }
}
