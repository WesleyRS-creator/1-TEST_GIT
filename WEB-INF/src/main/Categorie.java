package main;

import main.mother.SimpleItem;

public class Categorie extends SimpleItem {

    public Categorie(int id, String nom){
        super(id, nom);
    }

    public Categorie(String nom){
        super(nom);
    }

    public Categorie(int id){
        super(id);   
    }
}
