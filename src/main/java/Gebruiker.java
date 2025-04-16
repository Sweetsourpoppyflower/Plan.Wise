public class Gebruiker implements SysteemEntiteit {
    private String email;
    private String gebruikersnaam;
    private String wachtwoord;

    public Gebruiker(String email, String gebruikersnaam, String wachtwoord) {
        this.email = email;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    @Override
    public String getNaam() {
        return gebruikersnaam;
    }

    @Override
    public String getBeschrijving() {
        return "E-mail:" + email;
    }
}
