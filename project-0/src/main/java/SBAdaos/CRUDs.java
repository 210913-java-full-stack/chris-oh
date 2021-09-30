package SBAdaos;


import java.sql.SQLException;

public interface CRUDs {

    void input(String pk) throws SQLException;

    void get(String pk) throws SQLException;

    void remove(String pk) throws SQLException;

    void update(String pk, String field, String change) throws SQLException;

    void getAll() throws SQLException;
}
