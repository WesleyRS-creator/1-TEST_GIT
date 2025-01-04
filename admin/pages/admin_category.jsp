<%@ page import="java.util.List" %>
<%@ page import="config.PGdbc" %>
<%@ page import="main.Categorie" %>
<%@ page import="models.DAOCategorie" %>
<%@ page import="java.sql.Connection" %>

<%
    // Vérification de la session
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("../../login.jsp");
        return;
    }

    // Initialisation de PGDBC pour gérer la connexion
    PGdbc pgdbc = new PGdbc();
    List<Categorie> categories = null;

    try {
        pgdbc.openDefaultConnection(); // Ouvre une connexion par défaut
        Connection connection = pgdbc.getConnection(); // Récupère la connexion
        DAOCategorie daoCategorie = new DAOCategorie(connection);

        // Récupération de toutes les catégories
        categories = daoCategorie.getAll();

    } catch (Exception e) {
        e.printStackTrace();
        out.println("Erreur lors de la récupération des catégories.");
    } finally {
        pgdbc.closeConnection(); // Assurez-vous de fermer la connexion
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Catégories</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <!-- Navigation Partagée -->
    <nav>
        <a href="../admin_home.jsp">Dashboard</a>
        <a href="admin_brand.jsp">Marques</a>
        <a href="treatment_logout.jsp">Déconnexion</a>
    </nav>

    <header>
        <h1>Section Admin - Catégories</h1>
    </header>

    <main>
        <!-- Lien pour ajouter une nouvelle catégorie -->
        <section>
            <a href="admin_category_insertion.jsp">Nouvelle Catégorie</a>
        </section>

        <!-- Liste des catégories -->
        <section>
            <h2>Liste des Catégories</h2>
            <% if (categories != null && !categories.isEmpty()) { %>
                <div class="categories">
                    <% for (Categorie categorie : categories) { %>
                        <div class="category-card">
                            <h3><%= categorie.getNom() %></h3>
                            <p>ID: <%= categorie.getId() %></p>
                            <a href="admin_category_details.jsp?idCategory=<%= categorie.getId() %>">Voir Plus</a>
                            <a href="admin_category_edit.jsp?idCategory=<%= categorie.getId() %>">Modifier</a>
                            <a href="admin_category_delete.jsp?idCategory=<%= categorie.getId() %>">Supprimer</a>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <p>Aucune catégorie trouvée.</p>
            <% } %>
        </section>
    </main>

    <footer>
        <p>© 2025 Gestion des Catégories</p>
    </footer>
</body>
</html>
