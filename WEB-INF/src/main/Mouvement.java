package main;

import java.time.LocalDateTime;

public class Mouvement {
    private int idMouvement;
    private int idProduit;
    private int quantity;
    private TypeMouvement typeMouvement;
    private LocalDateTime dateMouvement;

    public Mouvement() {}

    public Mouvement(int idMouvement, int idProduit, int quantity, TypeMouvement typeMouvement, LocalDateTime dateMouvement) {
        setIdMouvement(idMouvement);
        setIdProduit(idProduit);
        setQuantity(quantity);
        setTypeMouvement(typeMouvement);
        setDateMouvement(dateMouvement);
    }

    public Mouvement(int idProduit, int quantity, TypeMouvement typeMouvement) {
        setIdMouvement(quantity);
        setQuantity(quantity);
        setTypeMouvement(typeMouvement);
        setDateMouvement(LocalDateTime.now());
    }

    public int getIdMouvement() {
        return idMouvement;
    }

    public void setIdMouvement(int idMouvement) {
        this.idMouvement = idMouvement;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    @Override
    public String toString() {
        return "Mouvement {" +
                "\n  idMouvement: " + idMouvement +
                "\n  idProduit: " + idProduit +
                "\n  quantity: " + quantity +
                "\n  typeMouvement: " + typeMouvement +
                "\n  dateMouvement: " + dateMouvement +
                "\n}";
    }
}
