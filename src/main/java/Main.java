import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        gebruiker gebruiker = new gebruiker("A", "dss");
        System.out.println(gebruiker.getGebruikersnaam());

        ArrayList<taak> takenLijst = new ArrayList<>();
        focusModus focusModus = new focusModus();


        takenLijst.add(new taak(1, "Boodschappen doen", "Koop melk, eieren en brood", "2025-04-05", "Supermarkt", true));
        takenLijst.add(new taak(2, "Sportschool", "Cardio en krachttraining", "2025-04-06", "Fitnesscentrum", false));
        takenLijst.add(new taak(3, "Project afronden", "Eindrapport schrijven", "2025-04-07", "Thuis", true));
        takenLijst.add(new taak(4, "Doktersafspraak", "Controle bij de huisarts", "2025-04-08", "Ziekenhuis", true));
        takenLijst.add(new taak(5, "Auto wassen", "Binnen en buiten schoonmaken", "2025-04-09", "Garage", false));
        takenLijst.add(new taak(6, "Vrienden ontmoeten", "Gezellig uit eten", "2025-04-10", "Restaurant", false));
        takenLijst.add(new taak(7, "Boek lezen", "Beginnen aan nieuwe thriller", "2025-04-11", "Thuis", false));
        takenLijst.add(new taak(8, "Verjaardagscadeau kopen", "Cadeau voor vriend kiezen", "2025-04-12", "Winkelcentrum", true));
        takenLijst.add(new taak(9, "Tuinieren", "Planten water geven en snoeien", "2025-04-13", "Tuin", false));
        takenLijst.add(new taak(10, "Presentatie voorbereiden", "Slides maken en oefenen", "2025-04-14", "Kantoor", true));

        hoortBij hoortBij = new hoortBij();
        hoortBij.setTaken(takenLijst);
        for (taak t : takenLijst) {
            hoortBij.HoortBij(t.getTaakID(), gebruiker.getGebruikersnaam());
        }
        System.out.println(takenLijst);

        hoortBij.taakVerwijderen(6);
        System.out.println(hoortBij.taken);


        focusModus.setTaken(takenLijst);
        System.out.println(focusModus.getPrioriteitsTaken());
    }
}