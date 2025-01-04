<%@ page import="config.PGdbc, models.DAOUser, main.User, java.sql.Connection, java.sql.SQLException" %>
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
        String motDePasse = request.getParameter("password");

        // Vérifier que les paramètres ne sont pas vides
        if (nom != null && motDePasse != null) {
            DAOUser daoUser = new DAOUser(connection); // Passer la connexion à DAOUser

            // Vérifier si l'utilisateur existe déjà
            if (daoUser.getByName(nom) == null) {
                // Créer un nouvel utilisateur
                User newUser = new User(nom, motDePasse, false);


                // Insérer l'utilisateur dans la base de données
                System.out.println("création faite");
                int userId = daoUser.create(newUser);

                if (userId > 0) {
                    // Sauvegarder l'ID de l'utilisateur dans la session
                    session.setAttribute("user_id", userId);

                    // Redirection vers la page d'accueil du client
                    response.sendRedirect("../client/client_home.jsp");
                } else {
                    out.println("Une erreur est survenue lors de l'inscription. Veuillez réessayer.");
                }
            } else {
                // L'utilisateur existe déjà
                out.println("Cet utilisateur existe déjà. Veuillez choisir un autre nom.");
            }
        } else {
            out.println("Le nom et le mot de passe sont requis.");
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
