import SBAdaos.MoneyDAO;
import SBAmodels.Money;
import SBAutils.*;
import UI.MainMenu;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class SBAExe {
    public static void main(String[] args) throws SQLException, IOException {
        MainMenu run = new MainMenu();
        run.initiate();
    }
}
