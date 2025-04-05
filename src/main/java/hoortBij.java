import java.util.ArrayList;
import java.util.Iterator;

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


}