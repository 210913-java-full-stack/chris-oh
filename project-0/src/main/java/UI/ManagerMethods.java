package UI;

import SBAdaos.JuncDAO;
import SBAdaos.MoneyDAO;
import SBAdaos.UserDAO;
import SBAmodels.Money;
import SBAutils.ConnMan;
import SBAutils.CusArrLis;
import SBAutils.Spacer;
import SBAutils.Validator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagerMethods {
    private final Connection conn = ConnMan.getConn();
    private int response = 0;
    private final Scanner sc = new Scanner(System.in);
    private final Spacer sp = new Spacer();
    private final UserDAO d = new UserDAO(conn);
    private final Validator v = new Validator();
    private final MoneyDAO md = new MoneyDAO(conn);
    private final MoneyManager mone = new MoneyManager();
    private final JuncDAO jd = new JuncDAO(conn);

    public ManagerMethods() throws SQLException, IOException {
    }

    //Method to update any User info in the User Table
    public void infoUpdater(String use) throws SQLException {
        response = 0;
        do {
            System.out.println("What Info Would You Like to Update?");
            sp.surround("1) Username\n2) Password\n3) E-mail\n4) Exit");
            try {
                response = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                sc.nextLine();
            }
            switch (response) {
                case 1:
                    System.out.print("Enter a New Username: ");
                    String nu = sc.next();
                    d.update(use, "username", nu); //Update Username
                    sc.nextLine();
                    sp.border();
                    sp.newL();
                    break;
                case 2:
                    System.out.print("Enter a New Password: ");
                    String np = sc.next();
                    d.update(use, "password", np); //Update Password
                    sc.nextLine();
                    sp.border();
                    sp.newL();
                    break;
                case 3:
                    System.out.print("Enter a New Email: ");
                    String ne = sc.next();
                    d.update(use, "email", ne); //Update Email
                    sc.nextLine();
                    sp.border();
                    sp.newL();
                    break;
            }
        } while (response != 4);
        sp.surround("Updates Successful!");
        response = 0;
    }

    //Completely Delete an Account and All Joined Accounts
    public void deletU(String use) throws SQLException {
        int uid;
        CusArrLis<Money> store = new CusArrLis<>(); //New Storage ArrayList

        if (v.accountExist(use, conn)) {
            md.getAccs(use);
            for (int i = 0; i < md.getLisRet().size(); i++) {

                //Check to see if the Bank Account Only Belongs to the User
                if (v.onlyMine(md.getLisRet().get(i).getMoney_id(), conn)) {
                    md.remove(md.getLisRet().get(i).getMoney_id()+""); //Delete the Bank Account
                } else {

                //If it does not, it will record all accounts that belong to multiple people in the store array
                    store.add(new Money(md.getLisRet().get(i).getMoney_id(), md.getLisRet().get(i).getBalance()));
                    md.remove(md.getLisRet().get(i).getMoney_id()+""); //Delete the Bank Account
                }
            }
            d.get(use);
            uid = d.getUseRet().getAccount_id();
            d.remove(use);
            jd.remove(uid+"");

            //Return the Bank Accounts that belong to multiple people to the database
            for (int i = 0; i < store.size(); i++) {
                md.fullIn(store.get(i).getMoney_id(), store.get(i).getBalance());
            }
        }


    }
    //Start the UI for managing bank accounts
    public void moneyUI(String use) throws SQLException {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        sp.border();
        sp.newL();
        do {
            //Display Accounts if they Exist
            if (v.accountExist(use, conn)) {
                md.getAccs(use);
                System.out.println(use + " Has " + md.getLisRet().size() + " Accounts:");
                for (int i = 0; i < md.getLisRet().size(); i++) {
                    System.out.println("Bank Account Number: " + md.getLisRet().get(i).getMoney_id() +
                            " and Balance: $" + df.format(md.getLisRet().get(i).getBalance()));
                }
            }
            response = 0;
            sp.surround("What Would You Like to Do?");

            //UI
            System.out.println("1) Withdraw Money\n2) Deposit Money\n" +
                    "3) Create New Account\n4) Link Existing Account\n" +
                    "5) Transfer Money to Existing Account\n" +
                    "6) Close Account\n7) View Transaction History of an Account\n8) Exit");
            sp.border();
            sp.newL();
            try {
                response = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                sc.nextLine();
            }
            switch (response) {
                case 1:
                    mone.withdraw(use); //Call Withdraw Method
                    break;
                case 2:
                    mone.deposit(use); //Call Deposit Method
                    break;
                case 3:
                    mone.createBacc(use); //Call Account Creation Method
                    break;
                case 4:
                    mone.linkAcc(use); //Call Linking Method
                case 5:
                    System.out.println("Money Transfer Protocol");
                    break;
                case 6:
                    System.out.println("Account Closing Protocol");
                    break;
                case 7:
                    System.out.println("Account History Protocol");
                    break;
                case 8:
            }
        } while (response != 8);
        response = 0;
    }
}
