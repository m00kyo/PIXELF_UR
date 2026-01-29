package Database;

public class User {
    public String getLogin() {
        return login;
    }

    public int getID() {
        return ID;
    }

    private String login;
    private int ID;


    public User(int ID, String login) {
        this.ID = ID;
        this.login = login;
    }
}
