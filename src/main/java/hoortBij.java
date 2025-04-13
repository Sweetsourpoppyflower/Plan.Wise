import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
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


    public void taakVerwijderen(int taakID) throws SQLException, ClassNotFoundException {
        try (Connection dbc = DatabaseConnector.connect()) {
            String sql = "DELETE FROM taken WHERE taak_id = ?";
            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setInt(1, taakID);
                stmt.executeUpdate();
            }
        }

        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        menu();
    }


    public void taakAanmakenViaInput() throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        String gebruikersnaam = "Appie";
        int taakID = 0;

        blank(50);
        header("➕  TAAK TOEVOEGEN  ➕");
        System.out.print("📝 Titel van de taak: ");
        String taaknaam = sc.nextLine();

        System.out.print("📄 Beschrijving: ");
        String beschrijving = sc.nextLine();

        System.out.println("📆 Kies een datum:");
        System.out.println("  [1] Vandaag");
        System.out.println("  [2] Morgen");
        System.out.println("  [3] Volgende week");
        System.out.println("  [4] Handmatig invoeren (YYYY-MM-DD)");
        int keuze = intMenus(sc, "👉 Keuze: ", 1, 4);

        LocalDate datum;
        if (keuze == 1) {
            datum = LocalDate.now();
        } else if (keuze == 2) {
            datum = LocalDate.now().plusDays(1);
        } else if (keuze == 3) {
            datum = LocalDate.now().plusWeeks(1);
        } else {
            System.out.print("✍️ Datum (YYYY-MM-DD): ");
            String input = sc.nextLine();
            try {
                datum = LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("❌ Ongeldige invoer. Terug naar menu.");
                return;
            }
        }


        System.out.print("📍 Locatie: ");
        String locatie = sc.nextLine();

        boolean isPrioriteit = false;
        int priorinvoer = intMenus(sc, "⚡ Is het een prioriteit? (1 = wel / 0 = geen prioriteit): ", 0, 1);
        if (priorinvoer == 1) {isPrioriteit = true;}

        taakAanmaken(taakID, gebruikersnaam, taaknaam, beschrijving, String.valueOf(datum), locatie, isPrioriteit);

        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");

        blank(5);
        menu();
    }


    public void vandaagsTaken() throws SQLException, ClassNotFoundException {
        blank(50);
        Scanner sc = new Scanner(System.in);
        LocalDate today = LocalDate.now();

        try (Connection dbc = DatabaseConnector.connect()) {
            String sql = "SELECT taaknaam, datum, locatie FROM taken WHERE datum = ?";
            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(today));
                ResultSet rs = stmt.executeQuery();

                header("📅  TAKEN VANDAAG  📅");
                boolean heeftTaken = false;

                while (rs.next()) {
                    String taaknaam = rs.getString("taaknaam");
                    String locatie = rs.getString("locatie");
                    System.out.println("📌 " + taaknaam + " bij 📍 " + locatie);
                    heeftTaken = true;
                }

                if (!heeftTaken) {
                    System.out.println("🙌 Geen taken gepland voor vandaag!");
                }
            }
        }

        menuHeader("📜  Kies een van de volgende opties:  📜");
        System.out.println("  [1] 🏠 Hoofd Menu");
        System.out.println("  [2] 📋 Alle Taken");
        int invoer = intMenus(sc, "👉 Keuze: ", 1, 2);

        if (invoer == 1) {
            menu();
        } else if (invoer == 2) {
            alleTaken();
        }


        blank(5);
    }


    public void vanDeWeeksTaken() throws SQLException, ClassNotFoundException {
        blank(50);
        Scanner sc = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.plusDays(6);

        try (Connection dbc = DatabaseConnector.connect()) {
            String sql = "SELECT taaknaam, datum, locatie FROM taken WHERE datum BETWEEN ? AND ?";
            try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(today));
                stmt.setDate(2, Date.valueOf(endOfWeek));
                ResultSet rs = stmt.executeQuery();

                header("📅  TAKEN DEZE WEEK  📅");
                boolean heeftTaken = false;

                while (rs.next()) {
                    String taaknaam = rs.getString("taaknaam");
                    Date datum = rs.getDate("datum");
                    String locatie = rs.getString("locatie");
                    System.out.println("📌 " + taaknaam + " op 📆 " + datum + " bij 📍 " + locatie);
                    heeftTaken = true;
                }

                if (!heeftTaken) {
                    System.out.println("🙌 Geen taken gepland deze week!");
                }

            }
        }

        menuHeader("📜  Kies een van de volgende opties:  📜");
        System.out.println("  [1] 🏠 Hoofd Menu");
        System.out.println("  [2] 📋 Alle Taken");
        int invoer = intMenus(sc, "👉 Keuze: ", 1, 2);

        if (invoer == 1) {
            menu();
        } else if (invoer == 2) {
            alleTaken();
        }

        blank(5);
    }


    public void alleTaken() throws SQLException, ClassNotFoundException {
        blank(50);
        Scanner sc = new Scanner(System.in);
        int hoogsteID = 0;
        try (Connection dbc = DatabaseConnector.connect();) {
            String query = "SELECT * FROM taken";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            header("📋  Alle Taken  📋");
            System.out.printf("%-10s %-20s %-50s%n", "ID", "NAAM", "BESCHRIJVING");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────────────");
            while (rs.next()) {
                System.out.printf(
                        "%-10d %-20s %-50s%n",
                        rs.getInt("taak_id"),
                        rs.getString("taaknaam"),
                        rs.getString("beschrijving")
                );
                hoogsteID = rs.getInt("taak_id");
            }

            rs.close();
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        menuHeader("📜  Kies een van de volgende opties:  📜");
        System.out.println("  [1] 🏠 Hoofd Menu");
        System.out.println("  [2] 🗓️ Dagelijkse taken");
        System.out.println("  [3] 🗓️ Wekelijkse taken");
        System.out.println("  [5] 🔍 TaakID voor meer informatie");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.print("");
        int invoer = alleenInts(sc, "👉 Selecteer een optie >>> ");


        while (invoer > hoogsteID) {
            System.out.println("❌ Ongeldige invoer");
            invoer = alleenInts(sc, "👉 Selecteer een optie >>> ");
        }

        if (invoer == 1) {
            menu();
        }

        if (invoer == 2) {
            vandaagsTaken();
        }

        if (invoer == 3) {
            vanDeWeeksTaken();
        }

        if (invoer > 10) {
            toonTaakInfo(invoer);
        }

        blank(10);
    }


    public void toonTaakInfo(int taakID) throws SQLException, ClassNotFoundException {
        blank(50);
        Scanner sc = new Scanner(System.in);
        Connection dbc = DatabaseConnector.connect();
        String sql = "SELECT taak_id, taaknaam, beschrijving, datum, locatie, is_prioriteit FROM taken WHERE taak_id = ?";

        try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
            stmt.setInt(1, taakID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String stInf = "📋 Taak Informatie: " + rs.getString("taaknaam") + " 📋";
                header(stInf);
                System.out.println("📝 Beschrijving: " + rs.getString("beschrijving"));
                System.out.println("📅 Datum: " + rs.getDate("datum"));
                System.out.println("📍 Locatie: " + rs.getString("locatie"));
                System.out.println("⚡ Prioriteit: " + (rs.getBoolean("is_prioriteit") ? "Ja" : "Nee"));
            }

            menuHeader("📜  Kies een van de volgende opties:  📜");
            System.out.println("  [1] 🏠 Hoofd Menu");
            System.out.println("  [2] ✏️ Taak updaten");
            System.out.println("  [3] 🗑️ Taak verwijderen");
            System.out.println("  [4] 📋 Alle taken\n");

            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");
            int invoer = intMenus(sc, "👉 Selecteer een optie >>> ", 1, 4);

            if (invoer == 1) { menu(); }
            if (invoer == 2) { updateTaak(rs.getInt("taak_id")); }
            if (invoer == 3) { taakVerwijderen(rs.getInt("taak_id")); }
            if (invoer == 4) { alleTaken(); }

            rs.close();
        }
        blank(10);
    }


    public void updateTaak(int taakID) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean bezig = false;

        if (taakID != 0) {
            bezig = true;
        }

        while (bezig) {
            Connection dbc = DatabaseConnector.connect();
            blank(50);

            header("🛠️  UPDATE TAAK MENU  🛠️");
            menuHeader("📜  Kies wat je aan de taak wilt wijzigen:  📜");

            System.out.println("  [1] ✏️  Titel");
            System.out.println("  [2] 📝 Beschrijving");
            System.out.println("  [3] 📅 Datum");
            System.out.println("  [4] 📍 Locatie");
            System.out.println("  [5]  ⚡ Prioriteit");
            System.out.println("  [6] 📋 Alle taken\n");

            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");
            int invoer = intMenus(sc, "👉 Invoer: ", 1, 6);

            if (invoer == 1) {
                System.out.print("✏️  Type hier je nieuwe titel >>> ");
                String titel = sc.nextLine();
                String sql = "UPDATE taken SET taaknaam = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setString(1, titel);
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }
            }

            if (invoer == 2) {
                System.out.print("📝 Type hier je nieuwe beschrijving >>> ");
                String besch = sc.nextLine();
                String sql = "UPDATE taken SET beschrijving = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setString(1, besch);
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }
            }

            if (invoer == 3) {
                System.out.print("📅 Welke dag (01-31): ");
                int dag = sc.nextInt();
                System.out.print("📅 Welke maand (1-12): ");
                int maand = sc.nextInt();
                System.out.print("📅 Welke jaar: ");
                int jaar = sc.nextInt();
                sc.nextLine();

                String datum = jaar + "-" + maand + "-" + dag;
                String sql = "UPDATE taken SET datum = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setDate(1, Date.valueOf(datum));
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }
            }

            if (invoer == 4) {
                System.out.print("📍 Wat is je nieuwe locatie >>> ");
                String loc = sc.nextLine();
                String sql = "UPDATE taken SET locatie = ? WHERE taak_id = ?";
                try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                    stmt.setString(1, loc);
                    stmt.setInt(2, taakID);
                    stmt.executeUpdate();
                }
            }

            if (invoer == 5) {
                System.out.print("⚡ Wil je prioriteit schakelen? (j/n) >>> ");
                String antw = sc.nextLine();
                while (!(antw.contains("j") || antw.contains("n"))){
                    System.out.println("❌ Ongeldige invoer");
                    System.out.print("⚡ Wil je prioriteit schakelen? (j/n) >>> ");
                    antw = sc.nextLine();
                }


                if (antw.equalsIgnoreCase("j")) {
                    String sql = "UPDATE taken SET is_prioriteit = NOT is_prioriteit WHERE taak_id = ?";
                    try (PreparedStatement stmt = dbc.prepareStatement(sql)) {
                        stmt.setInt(1, taakID);
                        stmt.executeUpdate();
                    }
                }
            }

            if (invoer == 6) {
                alleTaken();
            }
        }
    }


    public void menu() throws SQLException, ClassNotFoundException {
        blank(50);
        Scanner sc = new Scanner(System.in);
        focusModus fm = new focusModus();

        header("📋  HOOFD MENU  📋");
        menuHeader("📜  Kies een van de volgende opties:  📜");
        System.out.println("  [1] 📂 Alle taken bekijken");
        System.out.println("  [2] ➕ Taak toevoegen");
        System.out.println("  [3] 🎯 Focus Modus");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");

        int invoer = intMenus(sc, "👉 Keuze: ",1,4);

        if (invoer == 1) { alleTaken(); }
        if (invoer == 2) { taakAanmakenViaInput(); }
        if (invoer == 3) { fm.getPrioriteitsTaken(); }
        if (invoer == 4) { System.exit(0); }

        blank(5);
    }

    public int intMenus(Scanner sc, String prompt, int min, int max) {
        int invoer = -1;
        boolean geldig = false;
        while (!geldig) {
            System.out.print(prompt);
            try {
                invoer = sc.nextInt();
                sc.nextLine();
                if (invoer >= min && invoer <= max) {
                    geldig = true;
                } else {
                    System.out.println("❌ Ongeldige invoer (moet tussen " + min + " en " + max + ")");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Ongeldige invoer (geen getal)");
                sc.nextLine();
            }
        }
        return invoer;
    }

    public int alleenInts(Scanner sc, String prompt) {
        int invoer = -1;
        boolean geldig = false;
        while (!geldig) {
            System.out.print(prompt);
            try {
                invoer = sc.nextInt();
                sc.nextLine();
                geldig = true;

            } catch (InputMismatchException e) {
                System.out.println("❌ Ongeldige invoer (geen getal)");
                sc.nextLine();
            }
        }
        return invoer;
    }

    public void header(String naam){
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════════PlanWise══╗");
        System.out.println("                                " + naam);
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void menuHeader(String naam){
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                       " + naam);
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    public void blank(int aantal) {
        System.out.println("\n".repeat(aantal));
        System.out.println("                ____       ____    _                  __        __  _                    ____");
        System.out.println("                \\ \\ \\     |  _ \\  | |   __ _   _ __   \\ \\      / / (_)  ___    ___      / / /");
        System.out.println("                 \\ \\ \\    | |_) | | |  / _` | | '_ \\   \\ \\ /\\ / /  | | / __|  / _ \\    / / / ");
        System.out.println("                 / / /    |  __/  | | | (_| | | | | |   \\ V  V /   | | \\__ \\ |  __/    \\ \\ \\ ");
        System.out.println("                /_/_/     |_|     |_|  \\__,_| |_| |_|    \\_/\\_/    |_| |___/  \\___|     \\_\\_\\");
    }

}