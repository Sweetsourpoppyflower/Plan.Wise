import java.sql.SQLException;
public class Main {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        TaakManager TaakManager = new TaakManager();
        LoginSysteem.checkLogin();
        TaakManager.menu();

    }
}