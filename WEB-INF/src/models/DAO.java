package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {
    public Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public DAO(Connection connection){
        setConnection(connection);
    }

    // Méthode pour obtenir tous les objets
    public abstract List<T> getAll();

    // Méthode pour obtenir un objet par son ID
    public abstract T getById(int id);

    // Méthode pour insérer un objet
    public abstract int create(T entity);

    // Méthode pour mettre à jour un objet
    public abstract void update(T entity);

    // Méthode pour supprimer un objet
    public abstract void delete(int id);

    public abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    // Méthode utilitaire pour fermer les ressources
    public static void closeResources(PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception ou journaliser l'erreur
        }
    }
}
