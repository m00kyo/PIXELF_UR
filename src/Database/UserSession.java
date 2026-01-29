package Database;

public class UserSession {
    private static UserSession instance;
    private User currentUser;

    private UserSession(){}

    public static UserSession getInstance(){
        if(instance == null){
            instance = new UserSession();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public int getUserID(){
        return this.currentUser.getID();
    }

    public void logout(){
        this.currentUser = null;
    }

    public boolean isLogged(){
        return currentUser != null;
    }
}


