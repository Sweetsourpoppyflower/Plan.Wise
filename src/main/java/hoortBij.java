import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.time.LocalDate;

public class hoortBij {
    public ArrayList<taak> taken;

    public void taakAanmaken(int taakID, String gebruikersnaam, String taaknaam, String beschrijving, String datum, String locatie, boolean isPrioriteit) {
        try (Connection dbc = DatabaseConnector.connect()) {
            String sqlTID = "SELECT MAX(taak_id) FROM taken";
            int nieuwTID = 1;

            try (PreparedStatement sqlTaakID = dbc.prepareStatement(sqlTID);
                 ResultSet resTaakID = sqlTaakID.executeQuery()) {
                if (resTaakID.next()) {
                    nieuwTID = resTaakID.getInt(1) + 1;
                }
            }

            String sql = "INSERT INTO taken (taak_id, gebruikersnaam, taaknaam, beschrijving, datum, locatie, is_prioriteit) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setInt(1, nieuwTID);
                stmt.setString(2, gebruikersnaam);
                stmt.setString(3, taaknaam);
                stmt.setString(4, beschrijving);
                stmt.setDate(5, Date.valueOf(datum));
                stmt.setString(6, locatie);
                stmt.setBoolean(7, isPrioriteit);

                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void taakVerwijderen(int taakID) {
        try (Connection dbc = DatabaseConnector.connect()) {
            String sql = "DELETE FROM taken WHERE taak_id = ?";
            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setInt(1, taakID);
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void taakAanmakenViaInput() {
        Scanner sc = new Scanner(System.in);
        String gebruikersnaam = "Appie";
        int taakID = 0 ;
        System.out.println("Stap 1: Titel van de taak");
        System.out.println("Wat is de naam of titel van deze taak?");
        String taaknaam = sc.nextLine();

        System.out.println("Stap 2: Beschrijving");
        System.out.println("Beschrijf kort wat je moet doen bij deze taak:");
        String beschrijving = sc.nextLine();

        System.out.println("Stap 3: Datum");
        System.out.println("Op welke datum moet je deze taak uitvoeren? (formaat: YYYY-MM-DD):");
        String datum = sc.nextLine();

        System.out.println("Stap 4: Locatie");
        System.out.println("Waar vindt de taak plaats? (bijv. 'school', 'thuis', 'online'):");
        String locatie = sc.nextLine();

        System.out.println("Stap 5: Prioriteit");
        System.out.println("Is deze taak een prioriteit? Typ 'true' of 'false':");
        boolean isPrioriteit = sc.nextBoolean();

        taakAanmaken(taakID, gebruikersnaam, taaknaam, beschrijving, datum, locatie, isPrioriteit);

        System.out.println("Taak succesvol aangemaak!");
    }


    public void schakelPrioriteit(int taakID) {
        try (Connection dbc = DatabaseConnector.connect()) {
            String sql = "UPDATE taken SET is_prioriteit = NOT is_prioriteit WHERE taak_id = ?";
            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setInt(1, taakID);
                stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void vandaagsTaken() {
        LocalDate today = LocalDate.now();
        try (Connection dbc = DatabaseConnector.connect()) {
            String sql = "SELECT taaknaam, datum, locatie FROM taken WHERE datum = ?";
            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(today));
                ResultSet rs = stmt.executeQuery();

                System.out.println("=============================================================================");
                System.out.println("Vandaag: " + today + " heb je de volgende taken:");

                while (rs.next()) {
                    String taaknaam = rs.getString("taaknaam");
                    String locatie = rs.getString("locatie");
                    System.out.println(taaknaam + " bij " + locatie);
                }

                System.out.println("=============================================================================");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void vanDeWeeksTaken() {
        LocalDate today = LocalDate.now();
        try (Connection dbc = DatabaseConnector.connect()) {
            String sql = "SELECT taaknaam, datum, locatie FROM taken WHERE datum BETWEEN ? AND ?";
            LocalDate endOfWeek = today.plusDays(6);

            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(today));
                stmt.setDate(2, Date.valueOf(endOfWeek));
                ResultSet rs = stmt.executeQuery();

                System.out.println("=============================================================================");
                System.out.println("Deze week heb je de volgende taken:");

                while (rs.next()) {
                    String taaknaam = rs.getString("taaknaam");
                    Date datum = rs.getDate("datum");
                    String locatie = rs.getString("locatie");
                    System.out.println(taaknaam + " op " + datum + " bij " + locatie);
                }

                System.out.println("=============================================================================");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void blank() {
        for (int i = 0; i < 50; ++i) {
            System.out.println("");
        }
    }

    public void alleTaken(){
        try (Connection dbc = DatabaseConnector.connect();) {
            String query = "SELECT * FROM taken";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("taakID --- taakNaam ------ taakBeschrijving");
            while (rs.next()) {
                System.out.println(rs.getInt("taak_id") + " ------- " +
                        rs.getString("taaknaam") + " ------ " +
                        rs.getString("beschrijving"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void toonTaakInfo(int taakID, Connection dbc) throws SQLException {
        String sql = "SELECT taaknaam, beschrijving, datum, locatie, is_prioriteit FROM taken WHERE taak_id = ?";

        try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
            stmt.setInt(1, taakID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n ----------TAAK-INFO:----------");
                System.out.println("Taaknaam: " + rs.getString("taaknaam"));
                System.out.println("Beschrijving: " + rs.getString("beschrijving"));
                System.out.println("Datum: " + rs.getDate("datum"));
                System.out.println("Locatie: " + rs.getString("locatie"));
                System.out.println("Prioriteit: " + (rs.getBoolean("is_prioriteit") ? "Ja" : "Nee"));
                System.out.println("----------------------------------");
            }

            rs.close();
        }
    }



    public void updateTaak() throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        for (taak t : taken) {
            System.out.println(t.getTaakID() + " : " + t.getTaaknaam());
        }

        System.out.println("Voer de taakID in van je gewenste taak: ");
        int taakID = sc.nextInt();
        boolean bezig = false;

        if (taakID != 0) {
            bezig = true;
        }

        while (bezig) {
            Connection dbc = DatabaseConnector.connect();
            System.out.println("Kies wat je aan de taak wilt wijzigen: ");
            System.out.println("1: Titel ");
            System.out.println("2: beschrijving ");
            System.out.println("3: datum ");
            System.out.println("4: locatie ");
            System.out.println("5: prioriteit ");
            System.out.println("6: alle taken");
            int invoer = sc.nextInt();
            sc.nextLine();

            if (invoer == 1) {
                System.out.println("type hier je nieuwe titel: ");
                String titel = sc.nextLine();
                String sql = "UPDATE taken SET taaknaam = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setString(1, titel);
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }

            }

            if (invoer == 2) {
                System.out.println("Type hier je nieuwe beschrijving: ");
                String besch = sc.nextLine();
                String sql = "UPDATE taken SET beschrijving = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setString(1, besch);
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }
            }

            if (invoer == 3) {
                System.out.println("welke dag (01-31): ");
                int dag = sc.nextInt();
                System.out.println("welke maand (1-12): ");
                int maand = sc.nextInt();
                System.out.println("welke jaar: ");
                int jaar = sc.nextInt();
                sc.nextLine(); // buffer cleanen
                String datum = jaar + "-" + maand + "-" + dag;
                String sql = "UPDATE taken SET datum = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setDate(1, Date.valueOf(datum));
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }
            }

            if (invoer == 4) {
                System.out.println("Wat is je nieuwe locatie: ");
                String loc = sc.nextLine();
                String sql = "UPDATE taken SET locatie = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setString(1, loc);
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }
            }

            if (invoer == 5) {
                System.out.println("wil je prioriteit schakelen? (j/n)");
                String antw = sc.nextLine();
                if (antw.equalsIgnoreCase("j")) {
                    String sql = "UPDATE taken SET is_prioriteit = NOT is_prioriteit WHERE taak_id = ?";
                    try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                        stmt.setInt(1, taakID);
                        stmt.executeUpdate();
                    }
                }

            if (invoer == 6) {
                alleTaken();
            }
            }
        }

        }


}