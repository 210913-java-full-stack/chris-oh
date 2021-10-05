package SBAdaos;

import SBAmodels.User;
import SBAutils.CusArrLis;
import java.sql.*;

public class UserDAO implements CRUDs{
   private final Connection conn; //Instantiate Connection
   private User useRet; //User object for single Return
   private final CusArrLis<User> lisRet = new CusArrLis<>(); //List of User objects for group return

   public User getUseRet() {
       return useRet;
   } //Getter for User Object

   public CusArrLis<User> getLisRet() {
       return lisRet;
   } //Getter for User Array List

    //Constructor for DAO, requires connection
    public UserDAO (Connection conn) {
       this.conn = conn;
   }

    //New Row, Only allows for the insertion of a username. UI handles account creation
    @Override
    public void input(String use) throws SQLException {
        String sql = "INSERT INTO user_info (username) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, use);
        ps.executeUpdate();

    }

    //Gets Information from table based on username (Primary Key)
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

    //Remove from the table based on username (Primary Key)
    @Override
    public void remove(String use) throws SQLException {
        String sql = "DELETE FROM user_info WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, use);
        ps.executeUpdate();
    }

    //Update information. Updates one column at a time.
    @Override
    public void update(String use, String field, String change) throws SQLException {
       String sql = "UPDATE user_info SET " + field + " = ? WHERE username = ?";
       PreparedStatement ps = conn.prepareStatement(sql);
       ps.setString(1, change);
       ps.setString(2, use);
       ps.executeUpdate();
    }

    //Assigns lisRet as an Array List of all current Users
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

    //Update account_id specifically
    public void updateAID(String user, int aid) throws SQLException {
       String sql = "UPDATE user_info SET account_id = ? WHERE username = ?";
       PreparedStatement ps = conn.prepareStatement(sql);
       ps.setInt(1, aid);
       ps.setString(2, user);
       ps.executeUpdate();
    }

}
