package SBAdaos;

import SBAmodels.Money;
import SBAutils.CusArrLis;

import java.sql.*;

public class MoneyDAO implements CRUDs{
    private final Connection conn;
    private CusArrLis<Money> lisRet = new CusArrLis<>();

    public CusArrLis<Money> lReturner(){
        return lisRet;
    }
    public MoneyDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void input(String pk) throws SQLException {

    }

    @Override
    public void get(String user) throws SQLException {
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

    @Override
    public void remove(String aid) {

    }

    @Override
    public void update(String pk, String field, String change) throws SQLException {

    }

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
}

