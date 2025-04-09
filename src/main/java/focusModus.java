import java.util.ArrayList;

public class focusModus {
    public ArrayList<taak> taken;
    public boolean ingeschakeld;


    public boolean isIngeschakeld() {
        return ingeschakeld;
    }

    public void setIngeschakeld(boolean ingeschakeld) {
        this.ingeschakeld = ingeschakeld;
    }

    public ArrayList<String> getPrioriteitsTaken() {
        ArrayList<String> prioriteitTaken = new ArrayList<>();

        for (taak t : taken) {
            if (t.getPrioriteit()) {
                prioriteitTaken.add(t.getTaaknaam());
            }
        }

        return prioriteitTaken;
    }


}
