<%@ page import="java.util.List" %>
<%@ page import="config.PGdbc" %>
<%@ page import="main.UserHistorique" %>
<%@ page import="models.DAOUserHistorique" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="main.User" %>
<%@ page import="models.DAOUser" %>
<%@ page import="models.DAOProduit" %> <!-- Importer DAOProduit pour récupérer les produits -->
<%@ page import="main.Produit" %> <!-- Importer la classe Produit -->

<%
    // Vérification de la session
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("../../login.jsp"); // Redirige vers la page de connexion si session expirée
        return;
    }

    // Initialisation de PGDBC pour gérer la connexion
    PGdbc pgdbc = new PGdbc();
    User currentUser = null;
    List<UserHistorique> historyList = null;

    try {
        pgdbc.openDefaultConnection(); // Ouvre une connexion par défaut
        Connection connection = pgdbc.getConnection(); // Récupère la connexion

        // Récupération de l'utilisateur courant
        DAOUser daoUser = new DAOUser(connection);
        currentUser = daoUser.getById(userId);

        // Récupération de l'historique des mouvements
        DAOUserHistorique daoHistory = new DAOUserHistorique(connection);
        historyList = daoHistory.getAll(); // Récupère tous les historiques

    } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>Erreur lors de la récupération des données : " + e.getMessage() + "</p>");
    } finally {
        pgdbc.closeConnection(); // Assurez-vous de fermer la connexion
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Tableau de bord administrateur - Historique des mouvements">
    <title>Admin Dashboard - Historique</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <!-- Barre de navigation -->
    <nav>
        <a href="../admin_home.jsp">Dashboard</a>
        <a href="admin_user.jsp">Utilisateurs</a>
        <a href="admin_movement.jsp">Mouvements</a>
        <a href="treatment_logout.jsp">Déconnexion</a>
    </nav>

    <!-- En-tête de la page -->
    <header>
        <h1>Section Admin - Historique</h1>
        <p>Bienvenue, <strong><%= currentUser != null ? currentUser.getNom() : "Administrateur" %></strong>.</p>
    </header>

    <!-- Contenu principal -->
    <main>
        <section>
            <h2>Historique des Mouvements</h2>
            <% if (historyList != null && !historyList.isEmpty()) { %>
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">ID Facture</th>
                            <th scope="col">Utilisateur</th>
                            <th scope="col">Produit</th>
                            <th scope="col">Catégorie</th>
                            <th scope="col">Marque</th>
                            <th scope="col">Rating</th>
                            <th scope="col">Quantité</th>
                            <th scope="col">Prix</th>
                            <th scope="col">Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (UserHistorique history : historyList) { 
                            // Récupérer le produit via son ID
                             
                            Produit produit = null;
                            try {
                                pgdbc.openDefaultConnection();
                                DAOProduit daoProduit = new DAOProduit(pgdbc.getConnection());
                                daoProduit.getById(history.getMouvement().getIdProduit());
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                pgdbc.closeConnection();
                            }
                        %>
                            <tr>
                                <td><%= history.getIdUserHistory() %></td>
                                <td><%= history.getIdFacture() %></td>
                                <td><%= history.getMarketUser() != null ? history.getMarketUser().getNom() : "N/A" %></td>
                                <td><%= produit != null ? produit.getNom() : "N/A" %></td>
                                <td><%= produit != null ? produit.getCategorie().getNom() : "N/A" %></td>
                                <td><%= produit != null ? produit.getBrand().getNom() : "N/A" %></td>
                                <td><%= history.getMouvement() != null ? produit.getNote() : "N/A" %></td>
                                <td><%= history.getMouvement() != null ? produit.getStock() : "N/A" %></td>
                                <td><%= history.getMouvement() != null ? (produit.getStock()*produit.getPrixUnitaire()) : "N/A" %></td>
                                <td><%= history.getMouvement() != null ? history.getMouvement().getDateMouvement() : "N/A" %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <p>Aucun mouvement trouvé.</p>
            <% } %>
        </section>
    </main>

    <!-- Pied de page -->
    <footer>
        <p>© 2025 Gestion des Mouvements</p>
    </footer>
</body>
</html>
