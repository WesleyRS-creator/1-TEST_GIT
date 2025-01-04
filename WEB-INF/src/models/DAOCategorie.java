package models;

import main.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCategorie extends DAO<Categorie> {

    public DAOCategorie(Connection connection) {
        super(connection);
    }

    @Override
    public int create(Categorie categorie) {
        String query = "INSERT INTO CATEGORIE (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, categorie.getNom());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        categorie.setId(id);
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
    public List<Categorie> getAll() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM CATEGORIE";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Categorie categorie = mapResultSetToEntity(rs);
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }

    @Override
    public Categorie getById(int id) {
        String query = "SELECT * FROM CATEGORIE WHERE ID_CATEGORIE = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Categorie categorie) {
        String query = "UPDATE CATEGORIE SET name = ? WHERE ID_CATEGORIE = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, categorie.getNom());
            pstmt.setInt(2, categorie.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM CATEGORIE WHERE ID_CATEGORIE = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e)            {
            e.printStackTrace();
        }
    }

    @Override
    public Categorie mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Categorie(rs.getInt("ID_CATEGORIE"), rs.getString("name"));
    }
}
