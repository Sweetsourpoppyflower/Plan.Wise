import java.util.ArrayList;
import java.util.Iterator;

public class hoortBij {
    private int taakID;
    private String gebruikersnaam;
    public ArrayList<taak> taken;

    public void setTaken(ArrayList<taak> taken) {
        this.taken = taken;
    }

    public void HoortBij(int taakID, String gebruikersnaam) {
        this.taakID = taakID;
        this.gebruikersnaam = gebruikersnaam;
    }

    public taak taakAanmaken(int taakID, String taaknaam, String beschrijving, String datum, String locatie, boolean isPrioriteit) {
        return new taak(taakID, taaknaam, beschrijving, datum, locatie, isPrioriteit);
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
}