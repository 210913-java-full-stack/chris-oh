import SBAdaos.MoneyDAO;
import SBAmodels.Money;
import SBAutils.*;
import UI.MainMenu;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class SBAExe {
    public static void main(String[] args) throws SQLException, IOException {
//        Connection conn = null;
//        try {
//            conn = ConnMan.getConn();
//            System.out.println("Connection Established Successfully");
//        } catch (SQLException e){
//            System.out.println("Unable to connect to Database. Try again later.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //List<Geff> empList = new ArrayList<>();
//        CusArrLis<Geff> test = new CusArrLis<>();
//
//        }
//        for (int i = 0; i < test.size(); i++){
//            System.out.println(test.get(i));
//        }
//        Geff x = new Geff(3, "eg");
//        test.add(x, 1);
//        test.add(x, 3);
//        test.remove(1);
//
//
//        for (int i = 0; i < test.size(); i++){
//            System.out.println(test.get(i));
       // }


        MainMenu run = new MainMenu();
        run.initiate();
//        Connection conn = ConnMan.getConn();
//        MoneyDAO md = new MoneyDAO(conn);
//        Validator v = new Validator();
//        if (v.accountExist("test", conn)) {
//            System.out.println("true");
//        }
//        md.get("use");
////        CusArrLis<Money> test = new CusArrLis<>();
////        test = md.lReturner();
////        for (int i = 0; i < test.size(); i++) {
//            System.out.println(test.get(i).getBalance());
//        }







    }
}
