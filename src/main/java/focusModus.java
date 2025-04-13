import java.sql.*;
import java.util.Scanner;

public class focusModus {
    public boolean ingeschakeld;


    public boolean isIngeschakeld() {
        return ingeschakeld;
    }

    public void setIngeschakeld(boolean ingeschakeld) {
        this.ingeschakeld = ingeschakeld;
    }

    public void getPrioriteitsTaken() throws SQLException, ClassNotFoundException {
        hoortBij hoortBij = new hoortBij();
        hoortBij.blank(20);
        Scanner sc = new Scanner(System.in);
        Connection dbc = DatabaseConnector.connect();
        String query = "SELECT * FROM taken WHERE is_prioriteit = true";

        Statement stmt = dbc.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        hoortBij.header("🔥  FOCUS MODUS – PRIORITEITSTAKEN  🔥");
        System.out.printf("%-6s | %-25s | %-40s | %-12s%n", "🆔 ID", "📌 Taaknaam", "📝 Beschrijving", "📅 Datum");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");

        boolean heeftPrioriteiten = false;

        while (rs.next()) {
            System.out.printf(
                    "%-6d | %-25s | %-40s | %-12s%n",
                    rs.getInt("taak_id"),
                    rs.getString("taaknaam"),
                    rs.getString("beschrijving"),
                    rs.getDate("datum").toString()
            );
            heeftPrioriteiten = true;
        }

        if (!heeftPrioriteiten) {
            System.out.println("🙌 Geen prioriteitstaken gevonden.");
        }

        hoortBij.menuHeader("📜  Kies een van de volgende opties:  📜");
        System.out.println("  [1] 🏠 Hoofd Menu");
        System.out.println("  [2] ➕ Taak toevoegen");
        System.out.println("  [3] ❌ Exit");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════");

        int invoer = hoortBij.intMenus(sc, "👉 Selecteer een optie >>> ", 1, 3);

        if (invoer == 1) {
            hoortBij.menu();
        }
        if (invoer == 2) {
            hoortBij.taakAanmakenViaInput();
        }
        if (invoer == 3) {
            System.exit(0);
        }
    }




}
