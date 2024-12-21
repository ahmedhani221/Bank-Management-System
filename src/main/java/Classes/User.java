package Classes;

public abstract class User {
    private String ID;
    private String firstName;
    private String lastName;
    private String Username;
    private String Password;
    private String UserType;

    public User(String ID, String firstName, String lastName, String username, String password, String UserType) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Username = username;
        this.Password = password;
        this.UserType = UserType;
    }

    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getFullName(){
        return firstName + ' ' + lastName;
    }

    public String getUserType() { return UserType; }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
