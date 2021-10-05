package UI;

import SBAdaos.MoneyDAO;
import SBAdaos.UserDAO;
import SBAmodels.User;
import SBAutils.ConnMan;
import SBAutils.Spacer;
import SBAutils.Validator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private final Connection conn = ConnMan.getConn();
    private int response = 0;
    private final Scanner sc = new Scanner(System.in);
    private final UserDAO d = new UserDAO(conn);
    private final Spacer sp = new Spacer();
    private final MoneyDAO md = new MoneyDAO(conn);
    private final Validator v = new Validator();
    private final ManagerMethods mm = new ManagerMethods();


    public MainMenu() throws SQLException, IOException {
    }

    //Initiates the User Interface, Main Menu.
    public void initiate() throws SQLException, IOException {
        System.out.println("Welcome User!");
        response = 0; //Response Tracker that falls through a switch to act
        do {
            sp.border();
            System.out.println("\n1) Create an Account\n" +
                    "2) Manage an Account\n3) Recover Account\n4) Exit");
            sp.border();
            sp.newL();
            try {
                response = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Option, please enter a valid number\n");
                continue;
            }
            switch (response) {
                case 1:
                    this.newAcc(); //New Account method
                    break;
                case 2:
                    this.login(); //Goes to Login Method
                    break;
                case 3:
                    System.out.println("Running Recovery Protocol");
                    break;
            }
            sc.nextLine();


        } while (response != 4);
        System.out.println("Have a nice day!");
    }

    //Creates a new account
    private void newAcc() throws SQLException, IOException {

        sp.border();
        sp.newL();
        System.out.print("Please enter a Username: ");
        String use = sc.next(); //Receives a username to make a new account
        d.getAll();
        try {
            //Check to see if the username is in use
            if (v.userValid(use, d.getLisRet()) < 0) {
                d.input(use); //After validation, username is added to table
                sc.nextLine();
                System.out.print("Please enter a Password: ");
                String pass = sc.next();
                sc.nextLine();
                d.update(use, "password", pass); //No validation for password, it is immediately put into table
                System.out.print("Would you like to link an email to your account?\ny/n:");
                String email = sc.next(); //Checks if they want to add an email to their account
                if (email.equals("y") || email.equals("Y")) {
                    System.out.print("Please enter a valid email: ");
                    sc.nextLine();
                    email = sc.next();
                    d.update(use, "email", email); //email entered into table
                }
                sp.border();
                sp.newL();
                System.out.println("Account Created Successfully");
            } else {
                throw new SQLException(); //Manually throw exception for username
            }

        } catch (SQLException e) {
            System.out.println("Username Is Already in Use");
        }
    }

    //Login using an existing username and password
    private void login() throws SQLException, IOException {

        System.out.print("Please Enter a Valid Username: ");
        String use = sc.next();

        d.getAll(); //Retrieve data from the table

        if (v.userValid(use, d.getLisRet()) >= 0) { //Validate that the user exists
            sc.nextLine();
            System.out.print("Please Enter Your Password: ");
            String pass = sc.next();

            //validate that the password matches the user
            if (v.passValid(v.userValid(use, d.getLisRet()), pass, d.getLisRet())) {
                sp.border();
                sp.newL();
                System.out.println("Welcome " + use + "!");

                manager(use); //initiate the account manager ui
            } else {
                System.out.println("Incorrect Password");
            }
        } else {
            System.out.println("Not a Valid User!");
        }

    }

    //Display Account managing options
    private void manager(String use) throws SQLException, IOException {
        response = 0; //Response Tracker
        do {
            System.out.println("What would you like to do?");
            sp.border();
            sp.newL();
            System.out.println("1) Get Account Details\n" +
                    "2) Manage Finance\n3) Update Account Info\n4) Delete Account\n5) Exit");
            sp.border();
            sp.newL();
            try {
                response = sc.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                sc.nextLine();
            }

            switch (response) {
                case 1:
                    d.get(use); //Get Row In table based on username
                    User ret = d.getUseRet();
                    sp.border();
                    sp.newL();
                    ret.fullInfo(); //Display Full User Info
                    sp.border();
                    sp.newL();
                    break;
                case 2:
                    mm.moneyUI(use); //Call Money Managing Method
                    break;
                case 3:
                    sp.border();
                    sp.newL();
                    mm.infoUpdater(use); //Call Info Updating Method
                    break;
                case 4:
                    System.out.print("Are You Sure You wish to Delete this Account\ny/n: ");
                    String ans = sc.next();
                    if (ans.equals("y") || ans.equals("Y")) {
                        mm.deletU(use); //Call User Deleting Method
                    } else {
                        response = 0;
                        sp.border();
                        sp.newL();
                    }
                    break;
            }
        } while (response != 5 && response != 4);
        response = 4;
    }
}