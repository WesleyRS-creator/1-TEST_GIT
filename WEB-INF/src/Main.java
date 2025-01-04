import java.util.List;

import config.*;

import main.*;
import models.DAOBrand;
import models.DAOCategorie;
import models.DAOMouvement;
import models.DAOProduit;
import models.DAOUser;

public class Main {

    public static void main(String[] args) {
        PGdbc pGjdbc = new PGdbc();
        User u = new User("Bob", "bobPassword", true);
        try {
            pGjdbc.openDefaultConnection();
            // DAOCategorie daoCategorie = new DAOCategorie(pGjdbc.getConnection());
            // DAOUser daoUser = new DAOUser(pGjdbc.getConnection());

            // DAOBrand daoBrand = new DAOBrand(pGjdbc.getConnection());
            //daoBrand.create(new Brand("American"));


            DAOProduit daoProduit = new DAOProduit(pGjdbc.getConnection());
            Produit p = new Produit("T72", 1, 1, 1600, "a old tank", 2, "none", false);
            daoProduit.create(p);
            System.out.println(p.toString());

            // DAOMouvement daoMouvement = new DAOMouvement(pGjdbc.getConnection());
            // Mouvement mouvement = new Mouvement(1, 25, 0);
            // daoMouvement.create(mouvement);
            // List<Produit> lp = daoProduit.getAll();
            // for (Produit produit : lp) {
            //     System.out.println(produit.toString());
            // }



            // daoUser.create(u);

            // List<Categorie> cList = daoCategorie.getAll();
            // for (Categorie categorie : cList) {
            //     System.out.println(categorie.getNom());
            // }
            // daoCategorie.create(c);
            //System.out.println("Echo test");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pGjdbc.closeConnection();
        }
    }
}