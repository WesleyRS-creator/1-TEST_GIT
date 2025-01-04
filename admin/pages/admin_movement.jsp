<%@ page import="java.util.List" %>
<%@ page import="config.PGdbc" %>
<%@ page import="main.Mouvement" %>
<%@ page import="models.DAOMouvement" %>
<%@ page import="main.TypeMouvement" %>
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
    List<Mouvement> mouvements = null;

    try {
        pgdbc.openDefaultConnection(); // Ouvre une connexion par défaut
        Connection connection = pgdbc.getConnection(); // Récupère la connexion
        DAOMouvement daoMouvement = new DAOMouvement(connection);

        // Récupération de tous les mouvements
        mouvements = daoMouvement.getAll();

    } catch (Exception e) {
        e.printStackTrace();
        out.println("Erreur lors de la récupération des mouvements.");
    } finally {
        pgdbc.closeConnection(); // Assurez-vous de fermer la connexion
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Mouvements</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <!-- Navigation Partagée -->
    <nav>
        <a href="../admin_home.jsp">Dashboard</a>
        <a href="admin_product.jsp">Produits</a>
        <a href="admin_movement.jsp">Mouvements</a>
        <a href="treatment_logout.jsp">Déconnexion</a>
    </nav>

    <header>
        <h1>Section Admin - Mouvements</h1>
    </header>

    <main>
        <!-- Lien pour ajouter un nouveau mouvement -->
        <section>
            <a href="admin_movement_insertion.jsp">Nouveau Mouvement</a>
        </section>

        <!-- Liste des mouvements -->
        <section>
            <h2>Liste des Mouvements</h2>
            <% if (mouvements != null && !mouvements.isEmpty()) { %>
                <div class="movements">
                    <% for (Mouvement mouvement : mouvements) { %>
                        <div class="movement-card">
                            <h3>Produit ID: <%= mouvement.getIdProduit() %></h3>
                            <p>Quantité: <%= mouvement.getQuantity() %></p>
                            <p>Type de Mouvement: <%= mouvement.getTypeMouvement().getNom() %></p>
                            <p>Date: <%= mouvement.getDateMouvement() %></p>
                            <a href="admin_movement_details.jsp?idMouvement=<%= mouvement.getIdMouvement() %>">Voir Plus</a>
                            <a href="admin_movement_edit.jsp?idMouvement=<%= mouvement.getIdMouvement() %>">Modifier</a>
                            <a href="admin_movement_delete.jsp?idMouvement=<%= mouvement.getIdMouvement() %>">Supprimer</a>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <p>Aucun mouvement trouvé.</p>
            <% } %>
        </section>
    </main>

    <footer>
        <p>© 2025 Gestion des Mouvements</p>
    </footer>
</body>
</html>
