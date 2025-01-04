<%@ page import="models.DAOCategorie" %>
<%@ page import="config.PGdbc" %>
<%@ page import="java.sql.Connection" %>

<%
    // Vérification de la session
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("../../login.jsp");
        return;
    }

    String idCategory = request.getParameter("idCategory");
    if (idCategory == null || idCategory.isEmpty()) {
        out.println("ID de la catégorie invalide.");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Confirmer la Suppression</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Confirmation de la Suppression</h1>
    </header>

    <main>
        <p>Voulez-vous vraiment supprimer la catégorie avec l'ID <%= idCategory %> ?</p>
        <form action="admin_category_delete_action.jsp" method="post">
            <input type="hidden" name="idCategory" value="<%= idCategory %>">
            <button type="submit">Oui, Supprimer</button>
            <a href="admin_category_list.jsp">Annuler</a>
        </form>
    </main>
</body>
</html>
