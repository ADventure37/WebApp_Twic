package beans;

public class Ville {
    private String codeCommune;
    private String nom;
    private String codePostal;
    private String ligne;
    private double latitude;
    private double longitude;

    public Ville(){
        this.setCodeCommune(null);
        this.setLigne(null);
        this.setNom(null);
        this.setCodePostal(null);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getLigne() {
        return ligne;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
