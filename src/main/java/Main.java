import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        hoortBij hoortBij = new hoortBij();
        focusModus focusModus = new focusModus();

        LoginSysteem.checkLogin();

        hoortBij.taakVerwijderen(24);
        hoortBij.alleTaken();

    }
}