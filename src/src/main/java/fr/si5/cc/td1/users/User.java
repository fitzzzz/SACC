package fr.si5.cc.td1.users;

public class User {

    private String login;
    private String password;
    private long level;
    private long dataUploaded;
    private long currentUsage;


    public User() {
    }

    public User(String login, String password, long level, long dataUploaded, long currentUsage) {
        this.login = login;
        this.password = password;
        this.level = level;
        this.dataUploaded = dataUploaded;
        this.currentUsage = currentUsage;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getDataUploaded() {
        return dataUploaded;
    }

    public void setDataUploaded(long dataUploaded) {
        this.dataUploaded = dataUploaded;
    }

    public long getCurrentUsage() {
        return currentUsage;
    }

    public void setCurrentUsage(long currentUsage) {
        this.currentUsage = currentUsage;
    }

    public void addUpload(Long size) {
        this.currentUsage += (size / 1000000);
        if (currentUsage > 201) {
            this.level = 2;
            return;
        }
        if (currentUsage > 100) {
            this.level = 1;
            return;
        }
    }
}
