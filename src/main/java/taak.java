public class taak {
    private int taakID;
    private String taaknaam;
    private String beschrijving;
    private String datum;
    private String locatie;
    private boolean isPrioriteit;
    private String gebruikersnaam;
    public String toString(){
        return taaknaam;
    }

    public taak(int taakID, String gebruikersnaam, String taaknaam, String beschrijving, String datum, String locatie, boolean isPrioriteit){
        this.taakID = taakID;
        this.gebruikersnaam = gebruikersnaam;
        this.taaknaam = taaknaam;
        this.beschrijving = beschrijving;
        this.datum = datum;
        this.locatie = locatie;
        this.isPrioriteit = isPrioriteit;
    }
    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public int getTaakID() {
        return taakID;
    }

    public void setTaakID(int taakID) {
        this.taakID = taakID;
    }

    public String getTaaknaam() {
        return taaknaam;
    }

    public void setTaaknaam(String taaknaam) {
        this.taaknaam = taaknaam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }


    public boolean getPrioriteit() {
        return isPrioriteit;
    }

    public void setPrioriteit(boolean prioriteit) {
        isPrioriteit = prioriteit;
    }

}
