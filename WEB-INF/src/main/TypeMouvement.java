package main;

import main.mother.SimpleItem;

public class TypeMouvement extends SimpleItem {
    
    public String getDescriptionType_Mouvement() {
        return super.getNom();
    }

    public void setDescriptionType_Mouvement(String description){
        super.setNom(description);
    }

    public TypeMouvement(int id, String description) {
        super(id, description);
    }
}
