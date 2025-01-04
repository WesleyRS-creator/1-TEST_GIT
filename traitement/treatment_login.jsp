<%@ page import="config.PGdbc, models.DAOUser, main.User,java.sql.Connection, java.sql.SQLException" %>
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

            try {
                // Vérifier les informations d'authentification
                User user = daoUser.getByAuth(nom, motDePasse);

                if (user != null) {
                    // Sauvegarder l'ID de l'utilisateur dans la session
                    session.setAttribute("user_id", user.getId());
                    System.out.println(user.getNom());
                    System.out.println(user.getId());
                    System.out.println(user.isAdmin());

                    // Vérifier si l'utilisateur est administrateur
                    if (user.isAdmin()) {
                        // Redirection vers la page d'administration
                        response.sendRedirect("../admin/admin_home.jsp");
                    } else {
                        // Redirection vers la page client
                        response.sendRedirect("../client/client_home.jsp");
                    }
                } else {
                    // L'utilisateur n'existe pas ou le mot de passe est incorrect
                    out.println("Identifiants incorrects. Veuillez réessayer.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("Erreur de connexion. Veuillez réessayer.");
            }
        } else {
            out.println("Nom et mot de passe sont requis.");
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
