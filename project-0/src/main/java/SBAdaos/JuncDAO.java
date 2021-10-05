package SBAdaos;

import SBAmodels.Junc;
import SBAmodels.Money;
import SBAutils.CusArrLis;

import java.sql.*;

public class JuncDAO implements CRUDs{
    private CusArrLis<Junc> lisRet = new CusArrLis<>(); //Create Array List for Returning
    private final Connection conn; //Instantiate Connection

    public JuncDAO(Connection conn) {
        this.conn = conn;
    } //Constructor the receives Connection

    public CusArrLis<Junc> getLisRet() {
        return lisRet;
    } //Getter that returns Array list that is manipulated by methods

    //Link method used to join 2 accounts. Does so by adding new row to junction table
    public void link(int aid, int mid) throws SQLException {
        String sql = "INSERT INTO junc (account_id, money_id) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, aid);
        ps.setInt(2, mid);
        ps.executeUpdate();
    }

    //Inserts a new row into the junction table. Adds the money_id through a random number generator
    @Override
    public void input(String aid) throws SQLException {
        int change = 0; //Logical checkpoint to make sure the same random number is not used
        int naid = Integer.parseInt(aid); //Because of override, need to parse int from input string
        int rn6 = (int) (100000*Math.random()); //Create a random number
        getAll(); //Gets current Junction Table Data and stores in lisRet
        do{
            change = 0;

            //for loop to check if the randomly generated number matches another
            for (int i =0; i < lisRet.size(); i++) {
                if (lisRet.get(i).getMoney_id() == rn6 && rn6 != 0) {
                    change =1;
                    rn6 = (int) (100000*Math.random());
                }
            }
        } while (change == 1);

        //Once the program checks for repeat, inserts into table based on the arg and the randomly generated number
        String sql = "INSERT INTO junc (account_id, money_id) VALUES (?, " + rn6 + ")";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, naid);
        ps.executeUpdate();
    }

    //Get function that gets all of a user's money_id
    @Override
    public void get(String user) throws SQLException {
        lisRet.clear(); //Reset from previous uses
        String sql = "SELECT * FROM junc j "+
                "JOIN user_info ui ON ui.account_id = j.account_id WHERE username = ?";
        PreparedStatement prep = conn.prepareStatement(sql);
        prep.setString(1, user);
        ResultSet rs = prep.executeQuery();

        if (rs.next()) {
            do {
                lisRet.add(new Junc(rs.getInt("account_id"), rs.getInt("junc_id"),
                        rs.getInt("money_id")));
            } while (rs.next());
        } else {
            System.out.println(user + " Currently Has no Accounts");
        }
    }

    //Delete from junction table based on account_id
    @Override
    public void remove(String uid) throws SQLException {
        String sql = "DELETE FROM junc WHERE account_id = ?";
        int nuid = Integer.parseInt(uid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, nuid);
        ps.executeUpdate();
    }

    //Delete from junction table based on junction_id.
    public void remove(int jid) throws SQLException {
        String sql = "DELETE FROM junc WHERE junc_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, jid);
        ps.executeUpdate();
    }

    //Update information in junction table. Never utilized.
    @Override
    public void update(String pk, String field, String change) throws SQLException {

    }

    //Retrieves every row in the table and saves it to lisRet
    @Override
    public void getAll() throws SQLException {
        lisRet.clear(); //Reset lisRet
        Statement s = conn.createStatement();
        String sql = "SELECT * FROM junc";
        ResultSet rs = s.executeQuery(sql);

        //While loop to fill in lisRet
        while (rs.next()) {
            int junc_id = rs.getInt("junc_id");
            int account_id = rs.getInt("account_id");
            int money_id  = rs.getInt("money_id");
            lisRet.add(new Junc(junc_id, account_id,money_id));
        }
    }

    //Getter that chooses rows based on money_id
    public void selectByMid(int mid) throws SQLException {
        lisRet.clear(); //Reset lisRet
        String sql = "SELECT  * FROM junc where money_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lisRet.add(new Junc(rs.getInt("account_id"),
                    rs.getInt("junc_id"), rs.getInt("money_id")));
        }

    }
}
