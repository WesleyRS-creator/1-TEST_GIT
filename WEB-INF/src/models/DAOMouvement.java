package models;

import main.Mouvement;
import main.TypeMouvement;

import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;

public class DAOMouvement extends DAO<Mouvement> {

    private final DAOTypeMouvement daoTypeMouvement; // DAO pour gérer les types de mouvement

    public DAOMouvement(Connection connection) {
        super(connection);
        this.daoTypeMouvement = new DAOTypeMouvement(connection);  // Initialisation du DAOTypeMouvement
    }

    @Override
    public List<Mouvement> getAll() {
        List<Mouvement> mouvements = new ArrayList<>();
        String query = "SELECT * FROM MOUVEMENT";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                mouvements.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all movements: " + e.getMessage());
        }
        return mouvements;
    }

    @Override
    public Mouvement getById(int id) {
        String query = "SELECT * FROM MOUVEMENT WHERE ID_MOUVEMENT = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving movement by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int create(Mouvement mouvement) {
        String query = "INSERT INTO MOUVEMENT (ID_PRODUIT, QUANTITY, TYPE_MOUVEMENT, DATE_MOUVEMENT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, mouvement.getIdProduit());
            pstmt.setInt(2, mouvement.getQuantity());
            pstmt.setInt(3, mouvement.getTypeMouvement().getId());  // Utiliser l'ID de TypeMouvement
            pstmt.setTimestamp(4, Timestamp.valueOf(mouvement.getDateMouvement()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        mouvement.setIdMouvement(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting movement: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Mouvement mouvement) {
        String query = "UPDATE MOUVEMENT SET ID_PRODUIT = ?, QUANTITY = ?, ID_TYPE_MOUVEMENT = ?, DATE_MOUVEMENT = ? WHERE ID_MOUVEMENT = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, mouvement.getIdProduit());
            stmt.setInt(2, mouvement.getQuantity());
            stmt.setInt(3, mouvement.getTypeMouvement().getId());  // Utiliser l'ID de TypeMouvement
            stmt.setTimestamp(4, Timestamp.valueOf(mouvement.getDateMouvement()));
            stmt.setInt(5, mouvement.getIdMouvement());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating movement: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM MOUVEMENT WHERE ID_MOUVEMENT = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting movement: " + e.getMessage());
        }
    }

    @Override
    public Mouvement mapResultSetToEntity(ResultSet rs) throws SQLException {
        int idMouvement = rs.getInt("ID_MOUVEMENT");
        int idProduit = rs.getInt("ID_PRODUIT");
        int quantity = rs.getInt("QUANTITY");
        TypeMouvement typeMouvement = daoTypeMouvement.getById(rs.getInt("ID_TYPE_MOUVEMENT"));
        LocalDateTime dateMouvement = rs.getTimestamp("DATE_MOUVEMENT").toLocalDateTime();

        return new Mouvement(idMouvement, idProduit, quantity, typeMouvement, dateMouvement);
    }

    // Méthodes supplémentaires pour calculer le stock
    public int calculerNombreProduitEntres(int idProduit) {
        return calculerStock(idProduit, 1);
    }

    public int calculerNombreProduitSortis(int idProduit) {
        return calculerStock(idProduit, 2);
    }

    private int calculerStock(int idProduit, int typeMouvement) {
        int total = 0;
        String query = "SELECT SUM(QUANTITY) FROM MOUVEMENT WHERE ID_PRODUIT = ? AND ID_TYPE_MOUVEMENT = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idProduit);
            ps.setInt(2, typeMouvement);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error calculating stock: " + e.getMessage());
        }
        return total;
    }

    public int calculerResteStock(int idProduit) {
        int totalEntres = calculerNombreProduitEntres(idProduit);
        int totalSortis = calculerNombreProduitSortis(idProduit);
        return totalEntres - totalSortis;
    }
}
