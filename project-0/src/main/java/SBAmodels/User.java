package SBAmodels;

public class User {
    private String userName;
    private String password;
    private int account_id;
    private String email;

    public User(String userName, String password, int account_id, String email) {
        this.userName = userName;
        this.password = password;
        this.account_id = account_id;
        this.email = email;
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String toString() {
        return "U: " + this.userName + " P: " + this.password;
    }

    //Displays All Available info and or informs what is missing
    public void fullInfo() {
        System.out.println("U: " + this.userName +"\nP: "
                + this.password
        );
        if (account_id != 0) {
            System.out.println("Account ID: " + this.account_id);
        } else {
            System.out.println("No Accounts Created Yet");
        }
        if (this.email != null) {
                System.out.println("E-Mail: " + this.email);
        } else {
            System.out.println("No E-Mail Added Yet");
        }

    }
}
