<%@ page import="java.util.List" %>
<%@ page import="config.PGdbc" %>
<%@ page import="main.Produit" %>
<%@ page import="models.DAOProduit" %>
<%@ page import="java.sql.Connection" %>
<%@ page session="true" %>

<%
    // Vérification de la session
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("../../login.jsp");
        return;
    }

    // Initialisation de PGDBC pour gérer la connexion
    PGdbc pgdbc = new PGdbc();
    List<Produit> produits = null;

    try {
        pgdbc.openDefaultConnection(); // Ouvre une connexion par défaut
        Connection connection = pgdbc.getConnection(); // Récupère la connexion
        DAOProduit daoProduit = new DAOProduit(connection);

        // Récupération de tous les produits
        produits = daoProduit.getAll();

    } catch (Exception e) {
        e.printStackTrace();
        out.println("Erreur lors de la récupération des produits.");
    } finally {
        pgdbc.closeConnection(); // Assurez-vous de fermer la connexion
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <!-- Navigation Partagée -->
    <nav>
        <a href="../admin_home.jsp">Dashboard</a>
        <a href="treatment_logout.jsp">Déconnexion</a>
    </nav>

    <header>
        <h1>Section Admin - Produits</h1>
    </header>

    <main>
        <!-- Lien pour ajouter un nouveau produit -->
        <section>
            <a href="admin_product_insertion.jsp">Nouveau Produit</a>
        </section>

        <!-- Liste des produits -->
        <section>
            <h2>Liste des Produits</h2>
            <% if (produits != null && !produits.isEmpty()) { %>
                <div class="products">
                    <% for (Produit produit : produits) { %>
                        <div class="product-card">
                            <h3><%= produit.getNom() %></h3>
                            <p>ID: <%= produit.getId() %></p>
                            <p>Marque: <%= produit.getBrand().getNom() %></p>
                            <p>Catégorie: <%= produit.getCategorie().getNom() %></p>
                            <p>Note: <%= produit.getNote() %></p>
                            <p>Stock: <%= produit.getStock() %></p>
                            <p>Prix Unitaire: <%= produit.getPrixUnitaire() %> €</p>
                            <p>Description: <%= produit.getDescription() %></p>
                            <a href="admin_product_details.jsp?productId=<%= produit.getId() %>">Voir Plus</a>

                            <!-- Formulaire de réapprovisionnement -->
                            <form action="admin_product_restock.jsp" method="POST" class="restock-form">
                                <input type="hidden" name="productId" value="<%= produit.getId() %>">
                                <label for="restock_<%= produit.getId() %>">Ajouter au Stock :</label>
                                <input type="number" id="restock_<%= produit.getId() %>" name="restockAmount" min="1" required>
                                <button type="submit">Valider</button>
                            </form>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <p>Aucun produit trouvé.</p>
            <% } %>
        </section>
    </main>

    <footer>
        <p>© 2025 Gestion des Produits</p>
    </footer>
</body>
</html>
