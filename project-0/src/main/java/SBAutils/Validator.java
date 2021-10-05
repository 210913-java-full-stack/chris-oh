package SBAutils;

import SBAdaos.JuncDAO;
import SBAdaos.MoneyDAO;
import SBAdaos.UserDAO;
import SBAmodels.Junc;
import SBAmodels.Money;
import SBAmodels.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Validator {

    //Constructor for Validator
    public Validator() throws SQLException {
    }

    //Returns the index where the username argument exists
    public int userValid(String username, CusLisInterface<User> dataset) {
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i).getUserName().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    //Checks to see if the password of a given username is correct
    public boolean passValid(int index, String pass, CusArrLis<User> dataset) {
        boolean valid = false;
        try {
            if (dataset.get(index).getPassword().equals(pass)) {
                valid = true;
            }
        } catch (NullPointerException e) {
            System.out.println("This Account has no Password, Please go to 'Recover Account'");
        }
        return valid;
    }

    //Checks to see if a username has any bank accounts
    public boolean accountExist(String use, Connection conn) throws SQLException {
        boolean exist = false;
        MoneyDAO md = new MoneyDAO(conn);
        md.getAccs(use);
        if (md.getLisRet().size() > 0) {
            exist = true;
        }
        return exist;
    }

    //Checks to see if a given account_id already exists
    public boolean aidEx(int aid, Connection conn) throws SQLException {
        boolean ex = false;
        UserDAO d = new UserDAO(conn);
        d.getAll();
        for (int i = 0; i < d.getLisRet().size(); i++) {
            if (d.getLisRet().get(i).getAccount_id() == aid) {
                ex = true;
            }
        }
        return ex;
    }

    //Checks to see if an account_id matches its username in the database
    public boolean aidValid(String user, int aid, Connection conn) throws SQLException {
        boolean valid = false;
        UserDAO d = new UserDAO(conn);
        d.get(user);
        if (d.getUseRet().getAccount_id() == aid) {
            valid = true;
        }
        return valid;
    }

    //Checks to see if a money_id exists in the database
    public boolean monEx(int mid, Connection conn) throws SQLException {
        boolean ex = false;
        MoneyDAO md = new MoneyDAO(conn);
        md.getAll();
        for (int i = 0; i < md.getLisRet().size(); i++) {
            if (md.getLisRet().get(i).getMoney_id() == mid) {
                ex = true;
            }
        }
        return ex;
    }

    //Checks if the money_id of an account matches the username of an account
    public boolean myAccVal(int mid, String user, Connection conn) throws SQLException {
        boolean valid = false;
        JuncDAO jd = new JuncDAO(conn);
        jd.get(user);
        for (int i = 0; i < jd.getLisRet().size(); i++) {
            if (jd.getLisRet().get(i).getMoney_id() == mid) {
                valid = true;
            }
        }
        return valid;
    }

    //Checks to see if an account has aenough balance for a given withdraw
    public boolean enoughMoney(int mid, double withdraw, Connection conn) throws SQLException {
        boolean en = true;
        Spacer sp = new Spacer();
        MoneyDAO md = new MoneyDAO(conn);
        md.get(mid + "");
        if (withdraw > md.getmRet().getBalance()) {
            en = false;
        }
        return en;
    }

    //Checks to see if a money_id is only linked to one username
    public boolean onlyMine(int mid, Connection conn) throws SQLException {
        boolean mine = true;
        JuncDAO jd = new JuncDAO(conn);
        jd.selectByMid(mid);
        if (jd.getLisRet().size() > 1) {
            mine = false;
        }
        return mine;
    }

}

