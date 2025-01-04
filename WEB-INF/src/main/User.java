package main;

import main.mother.SimpleItem;

public class User extends SimpleItem {
    private String motDePasse;
    private boolean admin;

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public User(){}

    public User(String nom, String motDePasse, boolean admin){
        super(nom);
        setMotDePasse(motDePasse);
        setAdmin(admin);
    }

    public User(int id, String nom, String motDePasse, Boolean adminState){
        super(id, nom);
        setMotDePasse(motDePasse);
        setAdmin(adminState);
    }
}
