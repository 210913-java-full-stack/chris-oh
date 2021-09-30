package SBAdaos;

import SBAmodels.User;
import SBAutils.CusArrLis;

import java.sql.*;
import java.util.Scanner;

public class UserDAO implements CRUDs{
   private final Connection conn;
   private User useRet;
   private CusArrLis<User> lisRet = new CusArrLis<>();
   private Scanner scd = new Scanner(System.in);

   public User uReturner() {
       return useRet;
   }

   public CusArrLis<User> lReturner() {
       return lisRet;
   }
   public UserDAO (Connection conn) {
       this.conn = conn;
   }



    @Override
    public void input(String use) throws SQLException {
        String sql = "INSERT INTO user_info (username) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, use);
        ps.executeUpdate();

    }

    @Override
    public void get(String user) throws SQLException {

        String sql = "SELECT * FROM user_info WHERE username = ?";
        PreparedStatement prep = conn.prepareStatement(sql);
        prep.setString(1, user);

        ResultSet rs = prep.executeQuery();

        try {
            if (rs.next()) {
                useRet = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("account_id"),
                        rs.getString("email"));
            } else {
                System.out.println("Invalid Username Input");
            }
        } catch (SQLException e) {
            System.out.println("This Account Has no Password, Go to Recover Account.");
        }
    }

    @Override
    public void remove(String use) throws SQLException {
        String sql = "DELETE FROM user_info WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, use);
        ps.executeUpdate();
    }

    @Override
    public void update(String use, String field, String change) throws SQLException {
       String sql = "UPDATE user_info SET " + field + " = ? WHERE username = ?";
       PreparedStatement ps = conn.prepareStatement(sql);
       //ps.setString(1, field);
       ps.setString(1, change);
       ps.setString(2, use);
       ps.executeUpdate();
    }

    @Override
    public void getAll() throws SQLException {
       lisRet.clear();
       Statement s = conn.createStatement();
       String sql = "SELECT * FROM user_info";
       ResultSet rs = s.executeQuery(sql);

       while (rs.next()) {
           String user = rs.getString("username");
           String pass = rs.getString("password");
           int aid  = rs.getInt("account_id");
           String mail = rs.getString("email");
           lisRet.add(new User(user, pass, aid, mail));
       }
    }

}
