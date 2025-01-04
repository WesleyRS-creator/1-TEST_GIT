package main;

public class UserHistorique {
    private int idUserHistory;
    private int idFacture;
    private User marketUser;
    private Mouvement mouvement;

    public UserHistorique() {}

    public UserHistorique(int idUserHistory, int idFacture, int idMarketUser, int idMouvement) {
        setIdUserHistory(idUserHistory);
        setIdFacture(idFacture);
        setMarketUser(new User());
    }

    public UserHistorique(int idFacture, int idMarketUser, int idMouvement) {
        setIdFacture(idFacture);

    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public void setIdUserHistory(int idUserHistory) {
        this.idUserHistory = idUserHistory;
    }

    public void setMarketUser(User marketUser) {
        this.marketUser = marketUser;
    }

    public void setMouvement(Mouvement mouvement) {
        this.mouvement = mouvement;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public int getIdUserHistory() {
        return idUserHistory;
    }

    public User getMarketUser() {
        return marketUser;
    }

    public Mouvement getMouvement() {
        return mouvement;
    }

}
