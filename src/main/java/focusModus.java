import java.sql.*;
import java.util.ArrayList;
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

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                                           🔥  FOCUS MODUS – PRIORITEITSTAKEN  🔥                                                ");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

        System.out.printf("%-6s | %-25s | %-40s | %-12s%n", "🆔 ID", "📌 Taaknaam", "📝 Beschrijving", "📅 Datum");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");

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

        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n");
        System.out.println("                       📜  Kies een van de volgende opties:  📜");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("  [1] 🏠 Hoofd Menu");
        System.out.println("  [2] ➕ Taak toevoegen");
        System.out.println("  [3] ❌ Exit");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.print("👉 Selecteer een optie >>> ");
        int invoer = sc.nextInt();

        hoortBij hb = new hoortBij();

        if (invoer == 1) {
            hb.menu();
        }
        if (invoer == 2) {
            hb.taakAanmakenViaInput();
        }
        if (invoer == 3) {
            System.exit(0);
        }
    }




}
