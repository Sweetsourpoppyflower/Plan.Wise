import java.util.ArrayList;
import java.util.Scanner;
public class Main {


    public static void main(String[] args) {
        ArrayList<gebruiker> gebruikersLijst = new ArrayList<>();
        gebruiker gebruiker = new gebruiker("abdul2604a@gmail.com", "Appie", "geheim");
        gebruikersLijst.add(gebruiker);
        System.out.println(gebruiker.getGebruikersnaam());

        ArrayList<taak> Taken = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        //INLOG//
        System.out.println("voer je gebruikersnaam in: ");
        String gbnaamInvoer = sc.nextLine();
        System.out.println("voer je wachtwoord in: ");
        String gbwwInvoer = sc.nextLine();

        while (!(gbnaamInvoer.equals(gebruiker.getGebruikersnaam()) && gbwwInvoer.equals(gebruiker.getWachtwoord()))) {
            System.out.println("ww of gebruikersnaam klopt niet!");
            System.out.println("voer je gebruikersnaam in: ");
            gbnaamInvoer = sc.nextLine();
            System.out.println("voer je wachtwoord in: ");
            gbwwInvoer = sc.nextLine();
        }

        if (gbnaamInvoer.equals(gebruiker.getGebruikersnaam()) && gbwwInvoer.equals(gebruiker.getWachtwoord())) {
            System.out.println("Inlog gelukt!");
        }




        Taken.add(new taak(1, "Appie", "Boodschappen doen", "Koop melk, eieren en brood", "2025-04-05", "Supermarkt", true));
        Taken.add(new taak(2, "Jamal", "Sportschool", "Cardio en krachttraining", "2025-04-06", "Fitnesscentrum", false));
        Taken.add(new taak(3, "Appie", "Project afronden", "Eindrapport schrijven", "2025-04-07", "Thuis", true));
        Taken.add(new taak(4, "Sophie", "Doktersafspraak", "Controle bij de huisarts", "2025-04-08", "Ziekenhuis", true));
        Taken.add(new taak(5, "Appie", "Auto wassen", "Binnen en buiten schoonmaken", "2025-04-09", "Garage", false));
        Taken.add(new taak(6, "Sophie", "Vrienden ontmoeten", "Gezellig uit eten", "2025-04-10", "Restaurant", false));
        Taken.add(new taak(7, "Appie", "Boek lezen", "Beginnen aan nieuwe thriller", "2025-04-11", "Thuis", false));
        Taken.add(new taak(8, "Jamal", "Verjaardagscadeau kopen", "Cadeau voor vriend kiezen", "2025-04-12", "Winkelcentrum", true));
        Taken.add(new taak(9, "Sophie", "Tuinieren", "Planten water geven en snoeien", "2025-04-13", "Tuin", false));
        Taken.add(new taak(10, "Appie", "Presentatie voorbereiden", "Slides maken en oefenen", "2025-04-14", "Kantoor", true));

        hoortBij hoortBij = new hoortBij();
        focusModus focusModus = new focusModus();

        focusModus.setTaken(Taken);

        for (taak t : Taken) {
            System.out.println("Taaknaam: " + t.getTaaknaam() +  "is voor: " + t.getGebruikersnaam());
        }




        System.out.println(focusModus.getPrioriteitsTaken());
    }
}