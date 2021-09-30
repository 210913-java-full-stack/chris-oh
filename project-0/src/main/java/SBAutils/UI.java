package SBAutils;

import SBAdaos.MoneyDAO;
import SBAdaos.UserDAO;
import SBAmodels.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private final Connection conn = ConnMan.getConn();
    private int response = 0;
    private final Scanner sc = new Scanner(System.in);
    private final UserDAO d = new UserDAO(conn);
    private final Spacer sp = new Spacer();
    private final MoneyDAO md = new MoneyDAO(conn);
    public UI() throws SQLException, IOException {
    }

    public void initiate() throws SQLException, IOException {
        System.out.println("Welcome User!");
        response = 0;
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
                    this.newAcc();
                    break;
                case 2:
                    this.login();
                    break;
                case 3:
                    System.out.println("Running Recovery Protocol");
                    break;
            }
            sc.nextLine();


        } while (response != 4);
        System.out.println("Have a nice day!");
    }


    private void newAcc() throws SQLException, IOException {
        Validator v = new Validator();
        sp.border();
        sp.newL();
        System.out.print("Please enter a Username: ");
        String use = sc.next();
        d.getAll();
        try{
            if (v.userValid(use, d.lReturner()) < 0) {
                d.input(use);
                sc.nextLine();
                System.out.print("Please enter a Password: ");
                String pass = sc.next();
                sc.nextLine();
                d.update(use, "password", pass);
                System.out.print("Would you like to link an email to your account?\ny/n:");
                String email = sc.next();
                if (email.equals("y") || email.equals("Y")) {
                    System.out.print("Please enter a valid email: ");
                    sc.nextLine();
                    email = sc.next();
                    d.update(use, "email", email);
                }
                sp.border();
                sp.newL();
                System.out.println("Account Created Successfully");
            } else {
                throw new SQLException();
            }

        } catch (SQLException e) {
            System.out.println("Username Is Already in Use");
        }
    }

    private void login() throws SQLException, IOException {
        Validator v = new Validator();
        System.out.print("Please Enter a Valid Username: ");
        String use = sc.next();

        d.getAll();

        if (v.userValid(use, d.lReturner()) >= 0) {
            sc.nextLine();
            System.out.print("Please Enter Your Password: ");
            String pass = sc.next();

            if (v.passValid(v.userValid(use, d.lReturner()), pass, d.lReturner())) {
                sp.border();
                sp.newL();
                System.out.println("Welcome " + use + "!");

                manager(use);
            } else {
                System.out.println("Incorrect Password");
            }
        } else {
            System.out.println("Not a Valid User!");
        }

    }


    private void manager(String use) throws SQLException, IOException {
        response = 0;
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
                    d.get(use);
                    User ret = d.uReturner();
                    sp.border();
                    sp.newL();
                    ret.fullInfo();
                    sp.border();
                    sp.newL();
                    break;
                case 2:
                    System.out.println("Money Protocol");
                    break;
                case 3:
                    sp.border();
                    sp.newL();
                    infoUpdater(use);
                    break;
                case 4:
                    System.out.print("Are You Sure You wish to Delete this Account\ny/n: ");
                    String ans = sc.next();
                    if (ans.equals("y") || ans.equals("Y")) {
                        deletU(use);
                    } else {
                        response = 0;
                        sp.border();
                        sp.newL();
                    }
                    break;
            }
        } while (response != 5 && response != 4);
        response = 0;
    }
    public void infoUpdater(String use) throws SQLException {
        response = 0;
        do {
            System.out.println("What Info Would You Like to Update?");
            sp.border();
            sp.newL();
            System.out.println("1) Username\n2) Password\n3) E-mail\n4) Exit");
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
                    System.out.print("Enter a New Username: ");
                    String nu = sc.next();
                    d.update(use, "username", nu);
                    sc.nextLine();
                    sp.border();
                    sp.newL();
                    break;
                case 2:
                    System.out.print("Enter a New Password: ");
                    String np = sc.next();
                    d.update(use, "password", np);
                    sc.nextLine();
                    sp.border();
                    sp.newL();
                    break;
                case 3:
                    System.out.print("Enter a New Email: ");
                    String ne = sc.next();
                    d.update(use, "password", ne);
                    sc.nextLine();
                    sp.border();
                    sp.newL();
                    break;
            }
        } while (response != 4);
        sp.border();
        sp.newL();
        System.out.println("Updates Successful!");
        response = 0;
        sp.border();
        sp.newL();
    }
    public void deletU(String use) throws SQLException {
        d.remove(use);
    }
    public void moneyUI(String use) throws SQLException {
        sp.border();
        sp.newL();
        md.get(use);

        }
    }




