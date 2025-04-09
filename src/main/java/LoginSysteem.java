import java.sql.*;
import java.util.Scanner;

public class LoginSysteem {

    public static void checkLogin() throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        String gebruikersnaam;
        String wachtwoord;

        boolean loggedIn = false;

        try (Connection dbc = DatabaseConnector.connect()) {
            String query = "SELECT * FROM gebruiker WHERE gebruikersnaam = ? AND wachtwoord = ?";
            PreparedStatement stmt = dbc.prepareStatement(query);

            while (!loggedIn) {
                System.out.println("Voer je gebruikersnaam in: ");
                gebruikersnaam = sc.nextLine();
                System.out.println("Voer je wachtwoord in: ");
                wachtwoord = sc.nextLine();

                stmt.setString(1, gebruikersnaam);
                stmt.setString(2, wachtwoord);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Inlog gelukt!");
                    loggedIn = true;
                } else {
                    System.out.println("Wachtwoord of gebruikersnaam klopt niet!");
                }

                rs.close();
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Databasefout bij het inloggen:");
            e.printStackTrace();
        }
    }
}