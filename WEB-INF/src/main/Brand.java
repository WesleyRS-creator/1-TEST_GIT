package main;

import main.mother.SimpleItem;

public class Brand extends SimpleItem {

    public Brand(int id, String nom){
        super(id, nom);
    }

    public Brand(String nom){
        super(nom);
    }

    public Brand(int id){
        super(id);   
    }
}
