package fr.si5.cc.td1;

public class User {

    private String login;
    private String password;
    private int    level;
    private int    dataUploaded;
    private int    currentUsage;


    public User() {}

    public User(String login, String password, int level, int dataUploaded, int currentUsage) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDataUploaded() {
        return dataUploaded;
    }

    public void setDataUploaded(int dataUploaded) {
        this.dataUploaded = dataUploaded;
    }

    public int getCurrentUsage() {
        return currentUsage;
    }

    public void setCurrentUsage(int currentUsage) {
        this.currentUsage = currentUsage;
    }
    
}
