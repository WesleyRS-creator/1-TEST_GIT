<%@ page import="config.PGdbc, models.DAOUser, main.User,java.sql.Connection, java.sql.SQLException" %>
<%@ page session="true" %>

<%
    // Vérification si l'utilisateur est connecté
    Integer userId = (Integer) session.getAttribute("user_id");

    if (userId == null) {
        // Si l'utilisateur n'est pas connecté, redirection vers la page de connexion
        response.sendRedirect("login.jsp");
        return;
    }

    // Création de l'objet PGdbc pour gérer la connexion à la base de données
    PGdbc pgdbc = new PGdbc();
    Connection connection = null;
    User user = null;

    try {
        // Ouverture de la connexion à la base de données
        pgdbc.openDefaultConnection();
        connection = pgdbc.getConnection();

        // Création de l'objet DAOUser pour récupérer les informations de l'utilisateur par son ID
        DAOUser daoUser = new DAOUser(connection);
        user = daoUser.getById(userId);

        if (user == null || !user.isAdmin()) {
            // Si l'utilisateur n'est pas administrateur, redirection vers une page d'erreur ou autre page
            response.sendRedirect("error.jsp");
            return;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        out.println("Erreur de connexion à la base de données.");
    } finally {
        if (connection != null) {
            pgdbc.closeConnection();
        }
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page d'Administration</title>
    <!-- Ajouter des liens vers le CSS, par exemple Bootstrap -->
</head>
<body>
    <!-- Barre latérale pour la navigation -->
    <aside>
        <h2>Bienvenue, <%= user.getNom() %></h2>
        <nav>
            <ul>
                <li><a href="#">Accueil</a></li>
                <li><a href="pages/admin_product.jsp">Produits</a></li>
                <li><a href="pages/admin_brand.jsp">Marques</a></li>
                <li><a href="pages/admin_category.jsp">Catégories</a></li>
                <li><a href="pages/admin_movement.jsp">Mouvements</a></li>
                <li><a href="pages/admin_history.jsp">Historique</a></li>
                <li><a href="treatment_logout.jsp">Déconnexion</a></li>
            </ul>
        </nav>
    </aside>

    <!-- Contenu principal -->
    <main>
        <h1>Admin Dashboard</h1>

        <div class="section" id="produits">
            <h2>Nos Produits</h2>
            <a href="pages/admin_product.jsp">Voir la liste</a>
        </div>

        <div class="section" id="marques">
            <h2>Nos Marques</h2>
            <a href="pages/admin_brand.jsp">Voir la liste</a>
        </div>

        <div class="section" id="categories">
            <h2>Les catégories</h2>
            <a href="pages/admin_category.jsp">Voir la liste</a>
        </div>

        <div class="section" id="mouvements">
            <h2>Mouvements</h2>
            <a href="pages/admin_movement.jsp">Voir la liste</a>
        </div>

        <div class="section" id="historique">
            <h2>Historique</h2>
            <a href="pages/admin_history.jsp">Voir la liste</a>
        </div>
    </main>
</body>
</html>
