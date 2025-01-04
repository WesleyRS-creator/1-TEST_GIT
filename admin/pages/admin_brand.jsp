<%@ page import="java.util.List" %>
<%@ page import="config.PGdbc" %>
<%@ page import="main.Brand" %>
<%@ page import="models.DAOBrand" %>
<%@ page import="java.sql.Connection" %>

<%
    // Vérification de la session
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("../../login.jsp");
        return;
    }

    PGdbc pgdbc = new PGdbc();
    List<Brand> brands = null;

    try {
        pgdbc.openDefaultConnection(); // Ouvre une connexion par défaut
        Connection connection = pgdbc.getConnection(); // Récupère la connexion
        DAOBrand daoBrand = new DAOBrand(connection);

        // Récupération de toutes les marques
        brands = daoBrand.getAll();

    } catch (Exception e) {
        e.printStackTrace();
        out.println("Erreur lors de la récupération des marques.");
    } finally {
        pgdbc.closeConnection(); // Assurez-vous de fermer la connexion
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Marques</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <!-- Navigation Partagée -->
    <nav>
        <a href="../admin_home.jsp">Dashboard</a>
        <a href="treatment_logout.jsp">Déconnexion</a>
    </nav>

    <header>
        <h1>Section Admin - Marques</h1>
    </header>

    <main>
        <!-- Lien pour ajouter une nouvelle marque -->
        <section>
            <a href="admin_brand_insertion.jsp">Nouvelle Marque</a>
        </section>

        <!-- Liste des marques -->
        <section>
            <h2>Liste des Marques</h2>
            <% if (brands != null && !brands.isEmpty()) { %>
                <div class="brands">
                    <% for (Brand brand : brands) { %>
                        <div class="brand-card">
                            <h3><%= brand.getNom() %></h3>
                            <p>ID: <%= brand.getId() %></p>
                            <a href="admin_brand_details.jsp?idBrand=<%= brand.getId() %>">Voir Plus</a>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <p>Aucune marque trouvée.</p>
            <% } %>
        </section>
    </main>

    <footer>
        <p>© 2025 Gestion des Marques</p>
    </footer>
</body>
</html>
