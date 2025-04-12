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


            System.out.println(" ____    _                  __        __  _               ");
            System.out.println("|  _ \\  | |   __ _   _ __   \\ \\      / / (_)  ___    ___ ");
            System.out.println("| |_) | | |  / _` | | '_ \\   \\ \\ /\\ / /  | | / __|  / _ \\");
            System.out.println("|  __/  | | | (_| | | | | |   \\ V  V /   | | \\__ \\ |  __/");
            System.out.println("|_|     |_|  \\__,_| |_| |_|    \\_/\\_/    |_| |___/  \\___|");
            System.out.println();
            System.out.println("Welkom Terug, Login:");
            while (!loggedIn) {
                System.out.print("👤 Gebruikersnaam: ");
                gebruikersnaam = sc.nextLine();

                System.out.print("🔑 Wachtwoord: ");
                wachtwoord = sc.nextLine();

                stmt.setString(1, gebruikersnaam);
                stmt.setString(2, wachtwoord);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("\n✅ Inloggen gelukt! Welkom terug, " + gebruikersnaam + "!");
                    loggedIn = true;
                } else {
                    System.out.println("❌ Oeps! Verkeerde gebruikersnaam of wachtwoord.");
                    System.out.println("🔁 Probeer het opnieuw.\n");
                }

                rs.close();
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("🚨 Databasefout bij het inloggen:");
            e.printStackTrace();
        }
    }
}
