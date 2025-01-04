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

    PGdbc pgdbc = new PGdbc();

    try {
        pgdbc.openDefaultConnection();
        Connection connection = pgdbc.getConnection();
        DAOCategorie daoCategorie = new DAOCategorie(connection);

        // Suppression de la catégorie
        daoCategorie.delete(Integer.parseInt(idCategory));

        response.sendRedirect("..\admin_category.jsp?message=deleted");
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Erreur : " + e.getMessage());
    } finally {
        pgdbc.closeConnection();
    }
%>
