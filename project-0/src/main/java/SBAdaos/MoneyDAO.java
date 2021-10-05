package SBAdaos;

import SBAmodels.Money;
import SBAutils.CusArrLis;
import java.sql.*;

public class MoneyDAO implements CRUDs{
    private final Connection conn; //Connection that is required in constructor
    private CusArrLis<Money> lisRet = new CusArrLis<>(); //Array list for storage and returning
    private Money mRet; //Money Object to return a singular object

    //Getter for Money Object
    public Money getmRet() {
        return mRet;
    }

    //Getter for Array List of money Objects
    public CusArrLis<Money> getLisRet(){
        return lisRet;
    }

    //Constructor for DAO, requires connection
    public MoneyDAO(Connection conn) {
        this.conn = conn;
    }

    //Create new row in table, starting at $0.00
    @Override
    public void input(String mid) throws SQLException {
        int nmid = Integer.parseInt(mid);
        String sql = "INSERT INTO money (money_id, balance) VALUES (?, 0.00)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, nmid);
        ps.executeUpdate();
    }

    //Assign Stored Money Object as a specific account to use
    @Override
    public void get(String mid) throws SQLException {
        lisRet.clear();
        String sql = "SELECT balance FROM money WHERE money_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(mid));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            mRet = new Money(Integer.parseInt(mid), rs.getFloat("balance"));
        } else {
            throw new SQLException();
        }
    }

    //Get all of the bank accounts a given user has access to
    public void getAccs(String user) throws SQLException {
        lisRet.clear();
        String sql = "SELECT * FROM money m JOIN junc j ON m.money_id = j.money_id " +
                "JOIN user_info ui ON ui.account_id = j.account_id WHERE username = ?";
        PreparedStatement prep = conn.prepareStatement(sql);
        prep.setString(1, user);

        ResultSet rs = prep.executeQuery();

        if (rs.next()) {
            do {
                lisRet.add(new Money(rs.getInt("money_id"),
                        rs.getFloat("balance")));
            } while (rs.next());
        } else {
            System.out.println(user + " Currently Has no Accounts");
        }
    }

    //Delete row based on money_id
    @Override
    public void remove(String mid) throws SQLException {
        int naid = Integer.parseInt(mid);
        String sql = "DELETE FROM money WHERE money_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, naid);
        ps.executeUpdate();
    }

    //Update balance in an account, allows for update of money_id, but UI prevents that
    @Override
    public void update(String mid, String field, String change) throws SQLException {
        double bal = Double.parseDouble(change);
        int nmid = Integer.parseInt(mid);
        String sql = "UPDATE money SET " + field + " = ? WHERE money_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, bal);
        ps.setInt(2, nmid);
        ps.executeUpdate();
    }

    //Fill lisRet with all current accounts
    @Override
    public void getAll() throws SQLException {
        lisRet.clear();
        Statement s = conn.createStatement();
        String sql = "SELECT * FROM money";
        ResultSet rs = s.executeQuery(sql);

        while (rs.next()) {
            int money_id = rs.getInt("money_id");
            float balance = rs.getFloat("balance");
            lisRet.add(new Money(money_id, balance));
        }
    }

    //A method that creates a new row. Necessary for deleting
    public void fullIn(int mid, double balance) throws SQLException {
        String sql = "INSERT INTO money (money_id, balance) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mid);
        ps.setDouble(2, balance);
        ps.executeUpdate();
    }
}

