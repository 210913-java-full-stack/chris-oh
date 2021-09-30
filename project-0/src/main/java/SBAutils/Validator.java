package SBAutils;

import SBAdaos.MoneyDAO;
import SBAmodels.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Validator {

    public Validator() throws SQLException {
    }

    public int userValid(String username, CusLisInterface<User> dataset) {
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i).getUserName().equals(username)) {
                return i;
            }
        }
        return -1;
    }
    public boolean passValid(int index, String pass, CusArrLis<User> dataset) {
        boolean valid = false;
        try{
            if (dataset.get(index).getPassword().equals(pass)) {
                valid = true;
            }
        } catch (NullPointerException e) {
            System.out.println("This Account has no Password, Please go to 'Recover Account'");
        }
        return valid;
    }
    public boolean accountExist(String use, Connection conn) throws SQLException {
        boolean exist = false;
        MoneyDAO md = new MoneyDAO(conn);
        md.get(use);
        if (md.lReturner().size() > 0) {
            exist = true;
        }
        return exist;
    }
}
