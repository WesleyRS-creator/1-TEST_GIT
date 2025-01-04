<%@ page import="config.PGdbc, models.DAOCategorie, main.Categorie, java.sql.Connection, java.sql.SQLException" %>
<%@ page session="true" %>
<%
    // Créer une instance de PGdbc pour gérer la connexion
    PGdbc pgdbc = new PGdbc();
    Connection connection = null;

    try {
        // Ouverture de la connexion à la base de données
        pgdbc.openDefaultConnection();
        connection = pgdbc.getConnection();
        
        // Récupérer les données du formulaire
        String nom = request.getParameter("nom");
        // String motDePasse = request.getParameter("password");

        // Vérifier que les paramètres ne sont pas vides
        if (nom != null) {
            DAOCategorie daoCategorie = new DAOCategorie(connection); // Passer la connexion à DAOCategorie

            // Vérifier si la categorie existe déjà
            if (daoCategorie.getByName(nom) == null) {
                // Créer un nouvel categorie
                Categorie newCategorie = new Categorie(nom);


                // Insérer la nouvelle categorie dans la base de données
                System.out.println("création de catégorie faite");
                int categorieId = daoCategorie.create(newCategorie);
                

                if (categorieId > 0) {

                    // Redirection vers la page d'accueil du client
                    response.sendRedirect("../admin_category.jsp");
                } else {
                    out.println("Une erreur est survenue lors de la creation. Veuillez réessayer.");
                }
            } else {
                // La categorie existe déjà
                out.println("Cette categorie existe déjà. Veuillez choisir un autre nom.");
            }
        } else {
            out.println("Le nom est requis.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        out.println("Erreur de connexion à la base de données.");
    } finally {
        // Fermer la connexion à la base de données à la fin du traitement
        if (connection != null) {
            pgdbc.closeConnection();
        }
    }
%>
