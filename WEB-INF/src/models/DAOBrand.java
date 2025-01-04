package models;

import main.Brand;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOBrand extends DAO<Brand> {

    // Constructeur
    public DAOBrand(Connection connection) {
        super(connection);
    }

    @Override
    public int create(Brand brand) {
        String query = "INSERT INTO BRAND (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, brand.getNom());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        brand.setId(id);
                        return id; 
                    } else {
                        throw new SQLException("L'ID généré n'a pas pu être récupéré.");
                    }
                }
            } else {
                throw new SQLException("Aucune ligne insérée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Brand> getAll() {
        List<Brand> brands = new ArrayList<>();
        String query = "SELECT * FROM BRAND";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                // Utiliser la méthode mapResultSetToEntity pour créer l'entité
                Brand brand = mapResultSetToEntity(rs);
                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brands;
    }

    @Override
    public Brand getById(int id) {
        String query = "SELECT * FROM BRAND WHERE ID_BRAND = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Utiliser mapResultSetToEntity pour récupérer l'entité
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Brand brand) {
        String query = "UPDATE BRAND SET name = ? WHERE ID_BRAND = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, brand.getNom());
            pstmt.setInt(2, brand.getId());  // Utiliser getId_categorie pour obtenir l'ID
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM BRAND WHERE ID_BRAND = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Brand mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Brand(rs.getInt("ID_BRAND"), rs.getString("name"));
    }
}
