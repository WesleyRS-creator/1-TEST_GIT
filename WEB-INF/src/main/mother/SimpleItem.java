package main.mother;

public class SimpleItem {
    private int id;
    private String nom;

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public SimpleItem(){}

    public SimpleItem(String nom){
        setNom(nom);
    }

    public SimpleItem(int id, String nom){
        setId(id);
        setNom(nom);
    }

    public SimpleItem(int id) {
        setId(id);
    }

    @Override
    public String toString() {
        return "SimpleItem { id: " + id + ", nom: '" + nom + "' }";
    }
}
