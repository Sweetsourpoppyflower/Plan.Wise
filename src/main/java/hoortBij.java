import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.time.LocalDate;

public class hoortBij {
    public ArrayList<taak> taken;

    public void setTaken(ArrayList<taak> taken) {
        this.taken = taken;
    }


    public taak taakAanmaken(int taakID, String gebruikersnaam, String taaknaam, String beschrijving, String datum, String locatie, boolean isPrioriteit) {
        return new taak(taakID, gebruikersnaam ,taaknaam, beschrijving, datum, locatie, isPrioriteit);
    }

    public void taakVerwijderen(int taakID) {
        Iterator<taak> iterator = taken.iterator();
        while (iterator.hasNext()) {
            taak t = iterator.next();
            if (t.getTaakID() == taakID) {
                iterator.remove();
            }
        }
    }

    public void schakelPrioriteit(int taakID) {
        for (taak t : taken) {
            if (t.getTaakID() == taakID) {
                t.setPrioriteit(!(t.getPrioriteit()));
            }
        }
    }

    public void vandaagsTaken() {
        LocalDate today = LocalDate.now();
        System.out.println("=============================================================================");
        System.out.println("Vandaag: " + today + " heb je de volgende taken:");
        for (taak t : taken) {
            LocalDate datum = LocalDate.parse(t.getDatum());
            if (datum.isEqual(today)) {
                System.out.println(t.getTaaknaam() + " bij " + t.getLocatie());
            }
        }
        System.out.println("=============================================================================");
        System.out.println("\n\n");
    }


    public void vanDeWeeksTaken(){
        LocalDate today = LocalDate.now();
        System.out.println("=============================================================================");
        System.out.println("Deze week heb je de volgende taken:");
        for (taak t : taken) {
            LocalDate datum = LocalDate.parse(t.getDatum());
            if (datum.compareTo(today) >= 0 && datum.compareTo(today) <= 7) {
                System.out.println(t.getTaaknaam() + " op " + datum + " bij " + t.getLocatie());
            }
        }
        System.out.println("=============================================================================");
    }

    public void blank(){
        for (int i = 0; i < 50; ++i)
            {System.out.println("");}
    }

    public void updateTaak() {
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
            blank();
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
                for (taak tk : taken) {
                    if (tk.getTaakID() == taakID) {
                        System.out.println("type hier je nieuwe titel: ");
                        String titel = sc.nextLine();
                        tk.setTaaknaam(titel);
                        System.out.println("je nieuwe titel : " + tk.getTaaknaam());
                    }
                }
            }

            if (invoer == 2) {
                for (taak tk : taken) {
                    if (tk.getTaakID() == taakID) {
                        System.out.println("Type hier je nieuwe beschrijving: ");
                        String besch = sc.nextLine();
                        tk.setBeschrijving(besch);
                    }
                }
            }

            if (invoer == 3) {
                for (taak tk : taken) {
                    if (tk.getTaakID() == taakID) {
                        System.out.println("welke dag ( 01-30 / 31 ): ");
                        int dag = sc.nextInt();
                        System.out.println("welke maand ( 1-12 ): ");
                        int maand = sc.nextInt();
                        System.out.println("welke jaar: ");
                        int jaar = sc.nextInt();

                        tk.setDatum(jaar + "-" + maand + "-" + dag);
                    }
                }
            }

            if (invoer == 4) {
                for (taak tk : taken) {
                    if (tk.getTaakID() == taakID) {
                        System.out.println("Wat is je nieuwe locatie: ");
                        String loc = sc.nextLine();
                        tk.setLocatie(loc);
                    }
                }
            }

            if (invoer == 5) {
                for (taak tk : taken) {
                    if (tk.getTaakID() == taakID) {
                        System.out.println("Je prioriteit is nu op: " + tk.getPrioriteit());
                        System.out.println("wil je het schakelen? (antwoord met j of n)");
                        String antw = sc.nextLine();
                        if (antw.equalsIgnoreCase("ja")) {
                            schakelPrioriteit(taakID);
                        }
                    }
                }
            }

            if (invoer == 6) {
                System.out.println("TaakUpdate afgesloten...");
                bezig = false;
            }

            if (invoer < 1 || invoer > 6) {
                System.out.println("Voer een geldige optienummer in!!");
            }


        }
    }


}