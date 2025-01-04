package main;

import main.mother.SimpleItem;

public class Produit extends SimpleItem {
    private Categorie categorie;
    private Brand brand;
    private int prixUnitaire;
    private String description;
    private boolean softDelete;
    private double note;
    private String imageSource;
    private int stock;

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public void setPrixUnitaire(int prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setSoftDelete(boolean softDelete) {
        this.softDelete = softDelete;
    }

    public Brand getBrand() {
        return brand;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }

    public String getImageSource() {
        return imageSource;
    }

    public double getNote() {
        return note;
    }

    public int getPrixUnitaire() {
        return prixUnitaire;
    }

    public boolean isSoftDelete() {
        return softDelete;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return this.stock;
    }

    public Produit(int id, String nom, Categorie c, Brand b, int prixUnitaire, String description, double note, String imageSource, boolean softDelete, int stock) {
        super(id, nom);
        setBrand(b);
        setCategorie(c);
        setDescription(description);
        setImageSource(imageSource);
        setPrixUnitaire(prixUnitaire);
        setNote(note);
        setSoftDelete(softDelete);
        setStock(stock);
    }


    public Produit(String nom, int idCategorie, int idBrand, int prixUnitaire, String description, double note, String imageSource, boolean softDelete) {
        super(nom);
        setCategorie(new Categorie(idBrand));
        setBrand(new Brand(idBrand));
        setDescription(description);
        setImageSource(imageSource);
        setPrixUnitaire(prixUnitaire);
        setNote(note);
        setSoftDelete(softDelete);
    }

    public Produit(int id ,String nom, int idCategorie, int idBrand, int prixUnitaire, String description, double note, String imageSource, boolean softDelete) {
        super(id, nom);
        setCategorie(new Categorie(idBrand));
        setBrand(new Brand(idBrand));
        setDescription(description);
        setImageSource(imageSource);
        setPrixUnitaire(prixUnitaire);
        setNote(note);
        setSoftDelete(softDelete);
    }

    
    
    @Override
    public String toString() {
        return "Produit {" +
                "\n  id: " + getId() + 
                "\n  nom: '" + getNom() + '\'' +
                "\n  categorie: " + (categorie != null ? categorie.toString() : "null") +
                "\n  brand: " + (brand != null ? brand.toString() : "null") +
                "\n  prixUnitaire: " + prixUnitaire +
                "\n  description: '" + description + '\'' +
                "\n  softDelete: " + softDelete +
                "\n  note: " + note +
                "\n  imageSource: '" + imageSource + '\'' +
                "\n}";
    }

}
