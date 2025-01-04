<%@ page import="models.DAOUser, models.DAOProduit, main.Produit, main.User, java.sql.Connection, config.PGdbc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
    // Vérification de la session
    Integer userId = (Integer) session.getAttribute("user_id");
    System.out.println(userId);
    // if (userId == null) {
    //     response.sendRedirect("Index.jsp");
    //     return;
    // }

    // Récupération des données via les DAO
   
    User currentUser = null;
    PGdbc pgdbc = new PGdbc();

    int productId = Integer.parseInt(request.getParameter("productId"));
    
    Produit produit = null;
    try {
        pgdbc.openDefaultConnection(); // Ouvre une connexion par défaut
        Connection connection = pgdbc.getConnection(); // Récupère la connexion
        DAOUser daoUser = new DAOUser(connection);
        currentUser = daoUser.getById(userId);

        DAOProduit daoProduit = new DAOProduit(connection);
        produit = daoProduit.getById(productId);

    } catch (Exception e) {
        e.printStackTrace();
        out.println("Erreur lors de la récupération des mouvements.");
    } finally {
        pgdbc.closeConnection(); // Assurez-vous de fermer la connexion
    }

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails du Produit</title>
    <link rel="stylesheet" href="styles.css"> <!-- Lien CSS -->
</head>
<body>
    <!-- Layout partagé -->
    <header>
        <h1>Bienvenue, <%= currentUser.getNom() %></h1>
        <nav>
            <a href="treatment_logout.jsp">Déconnexion</a>
        </nav>
    </header>

    <main>
        <!-- Section Admin User -->
        <section>
            <h2>Admin Panel - Détails Produit</h2>
            <a href="admin_product_create.jsp" class="btn">Nouveau Produit</a>

            <!-- Card Produit -->
            <article class="product-card">
                <h3><%= produit.getNom() %></h3>
                <span>ID : <%= produit.getId() %></span>
                <span>Marque : <%= produit.getBrand().getNom() %></span>
                <span>Catégorie : <%= produit.getCategorie().getNom() %></span>
                <span>Note : <%= produit.getNote() %></span>
                <% int stock = produit.getStock(); %>
                <span>Stock : 
                    <%= stock > 0 ? (stock == 1 ? "en rupture de stock" : stock + " unités disponibles") : "pas de stock" %>
                </span>
                <span>Prix Unitaire : <%= produit.getPrixUnitaire() %>€</span>
                <p>Description : <%= produit.getDescription() %></p>
                <div class="product-links">
                    <a href="admin_product_update.jsp?productId=<%= produit.getId() %>" class="btn">Modifier</a>
                    <a href="admin_product_restock.jsp?productId=<%= produit.getId() %>" class="btn">Réapprovisionner</a>
                    <a href="admin_product.jsp" class="btn">Retour</a>
                </div>
            </article>
        </section>
    </main>

    <footer>
        <p>© 2025 Mon Application</p>
    </footer>
</body>
</html>
