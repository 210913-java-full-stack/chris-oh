package UI;

import SBAdaos.JuncDAO;
import SBAdaos.MoneyDAO;
import SBAdaos.UserDAO;
import SBAutils.ConnMan;
import SBAutils.Spacer;
import SBAutils.Validator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MoneyManager {
    private final Connection conn = ConnMan.getConn();
    private final Spacer sp = new Spacer();
    private int response = 0;
    private final UserDAO d = new UserDAO(conn);
    private final Scanner sc = new Scanner(System.in);
    private final JuncDAO jd = new JuncDAO(conn);
    private final Validator v = new Validator();
    private final MoneyDAO md = new MoneyDAO(conn);

    public MoneyManager() throws SQLException, IOException {
    }

    //Create New Bank Account Method
    protected void createBacc(String user) throws SQLException {
        int Aid = 0;
        boolean running = true;
        d.get(user);

        //Check to see if user has an account_id
        if (d.getUseRet().getAccount_id() == 0){
            sp.border();
            sp.newL();

            //If no account ID, allows user to set one
            System.out.print("Please Enter a 4 digit number to be Your Permanent Account ID: ");
            do {
                try {
                    Aid = sc.nextInt();
                    sc.nextLine();
                    if (v.aidEx(Aid, conn) != true) { //Make sure account_id has not been taken
                        jd.input(Aid + ""); //Updates junction table with account_id
                        d.updateAID(user, Aid); //Updates User table with new account_id
                        jd.get(user);
                        md.input(jd.getLisRet().get(0).getMoney_id() + ""); //Updates Money Table with new account
                        running = false;
                    } else {
                        sp.surround("That ID already Exists");
                    }
                } catch (InputMismatchException e) {
                   sp.surround("Invalid Input!");
                }
            } while (running);
            sp.surround("New Account Successfully Created!");

            //If user already has account_id, they are prompted to enter it
        } else {
            sp.border();
            sp.newL();
            System.out.print("Enter Your Account ID: ");

            do {
                try {
                    Aid = sc.nextInt();
                    sc.nextLine();
                    if (v.aidValid(user, Aid, conn)) { //If account_id is valid
                        jd.input(Aid + ""); //Add new Junction
                        jd.get(user);
                        running = false;
                        for (int i = 0; i < jd.getLisRet().size(); i++) { //Loop through user's accounts on junction table
                            if (v.monEx(jd.getLisRet().get(i).getMoney_id(), conn) != true) { //Adds account to money table if doesn't already exist
                                md.input(jd.getLisRet().get(i).getMoney_id() + ""); //Create new row in money table
                            } else {
                                sp.surround("Authentication Failed");
                            }
                            sp.surround("New Account Successfully Created");
                        }
                    }
                } catch (InputMismatchException e) {
                    sp.surround("Invalid Input!");
                }
            }while (running) ;
        }
    }

    //Withdraw from account method
    protected void withdraw(String user) throws SQLException {
        boolean running = true;
        int mid = 0;
        double withdraw = 0;
        double change = 0;
        do {
            sp.border();
            sp.newL();
            System.out.print("Enter Your Bank Account Number: ");
            try {
                mid = sc.nextInt();
                sc.nextLine();
                if (v.myAccVal(mid, user, conn)) { //Checks to make sure input Bank Account belongs to user
                   sp.border();
                   sp.newL();
                   System.out.print("How much Money Would you like to Withdraw: ");

                   do{
                       try {
                           withdraw = Double.parseDouble(sc.next());
                           sc.nextLine();
                           withdraw = Math.round(withdraw*100.0)/100.0; //Convert User input to standard money form
                           if (v.enoughMoney(mid, withdraw, conn) && withdraw >= 0) { //Check if enough money is in account
                               md.get(mid+"");
                               change = md.getmRet().getBalance() - withdraw; //Withdraw money
                               md.update(mid+"", "balance", change+""); //Change the table
                               running = false;
                               sp.surround("$" + change + " Is in this Account."); //Report Money Left over

                           } else {
                               if (withdraw < 0){
                                   sp.surround("Cannot Withdraw a Negative Number");
                               } else {
                                   sp.surround("Not Enough Money in Account");
                               }
                               running = false;
                           }

                       } catch (NumberFormatException e) {
                           sp.surround("Invalid Input!");
                       }
                   } while (running);

                } else {
                    sp.surround("This Account Doesn't Belong to you!");
                    running = false;
                }
            } catch (InputMismatchException e) {
                sp.surround("Invalid Input!");
                sc.nextLine();
            }
        } while (running);
    }


    //Deposit Money
    public void deposit(String user){
        boolean running = true;
        int mid = 0;
        double deposit = 0;
        double change = 0;
        do {
            sp.border();
            sp.newL();
            System.out.print("Enter Your Bank Account Number: ");
            try {
                mid = sc.nextInt();
                sc.nextLine();
                if (v.myAccVal(mid, user, conn)) { //Check to see if the Bank Account belongs to the user
                    sp.border();
                    sp.newL();
                    System.out.print("How much Money Would you like to Deposit: ");

                    do{
                        try {
                            deposit = Double.parseDouble(sc.next());
                            sc.nextLine();
                            deposit = Math.round(deposit*100.0)/100.0; //Convert user input to standard money form

                            if (deposit >= 0) {
                                md.get(mid+"");
                                change = md.getmRet().getBalance() + deposit; //Deposit
                                md.update(mid+"", "balance", change+""); //Update money table with change
                                running = false;
                                sp.surround("$" + change + " Is in this Account.");

                            } else {
                                sp.surround("Cannot Deposit a Negative Number");
                                }
                                running = false;

                        } catch (NumberFormatException e) {
                            sp.surround("Invalid Input!");
                        }
                    } while (running);

                } else {
                    sp.surround("This Account Doesn't Belong to you!");
                    running = false;
                }
            } catch (InputMismatchException | SQLException e) {
                sp.surround("Invalid Input!");
                sc.nextLine();
            }
        } while (running);
    }


    // Link Accounts
    public void linkAcc(String user) throws SQLException {
        int joinAcc;
        String fUse;
        String fPass;
        try {
            System.out.print("What is the Bank Account ID of the Account You Want to Join: ");
            joinAcc = sc.nextInt(); //Get the bank account number of account you want to join
            System.out.print("Please Enter Your Username: "); //Validate that you can access this account
            fUse = sc.next();
            d.getAll();
            if (v.userValid(fUse, d.getLisRet()) >= 0) { //Check Validation
                sc.nextLine();
                System.out.print("Please Enter Your Password: ");
                fPass = sc.next();

                if (v.passValid(v.userValid(fUse, d.getLisRet()), fPass, d.getLisRet())) { //Validate Password
                    if (v.myAccVal(joinAcc, fUse, conn)) {
                        d.get(user);
                        jd.link(d.getUseRet().getAccount_id(), joinAcc); //Add Junction with current user account_id and different money_id
                    } else {
                        sp.surround("This account does not belong to you!");
                    }
                } else {
                    System.out.println("Incorrect Password");
                }
            } else {
                System.out.println("Not a Valid User!");
            }
        } catch (InputMismatchException e) {
            sp.surround("Invalid Input!");
        }


    }


}
