package models;

import main.Produit;

import main.Brand;
import main.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOProduit extends DAO<Produit> {

    // Constructeur
    public DAOProduit(Connection connection) {
        super(connection);
    }

    @Override
    public int create(Produit produit) {
        String query = "INSERT INTO PRODUIT (NOM_PRODUIT, ID_BRAND, ID_CATEGORIE, DESCRIPTION_PRODUIT, PRIX_UNITAIRE, NOTE, image_source, soft_delete) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, produit.getNom());
            pstmt.setInt(2, produit.getBrand().getId());
            pstmt.setInt(3, produit.getCategorie().getId());
            pstmt.setString(4, produit.getDescription());
            pstmt.setInt(5, produit.getPrixUnitaire());
            pstmt.setDouble(6, produit.getNote());
            pstmt.setString(7, produit.getImageSource());
            pstmt.setBoolean(8, produit.isSoftDelete());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        produit.setId(id);
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
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM PRODUIT";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Produit produit = mapResultSetToEntity(rs);
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }

    @Override
    public Produit getById(int id) {
        String query = "SELECT * FROM PRODUIT WHERE ID_PRODUIT = ?";
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
    public void update(Produit produit) {
        String query = "UPDATE PRODUIT SET NOM_PRODUIT = ?, ID_BRAND = ?, ID_CATEGORIE = ?, DESCRIPTION_PRODUIT = ?, PRIX_UNITAIRE = ?, NOTE = ?, image_source = ?, soft_delete = ? " +
                "WHERE ID_PRODUIT = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, produit.getNom());
            pstmt.setInt(2, produit.getBrand().getId());
            pstmt.setInt(3, produit.getCategorie().getId());
            pstmt.setString(4, produit.getDescription());
            pstmt.setInt(5, produit.getPrixUnitaire());
            pstmt.setDouble(6, produit.getNote());
            pstmt.setString(7, produit.getImageSource());
            pstmt.setBoolean(8, produit.isSoftDelete());
            pstmt.setInt(9, produit.getId());
            pstmt.executeUpdate();
            System.out.println("MAJ du produit effectué");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM PRODUIT WHERE ID_PRODUIT = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean setSoftDelete(int idProduit, boolean softDeleteValue) {
        String query = "UPDATE PRODUIT SET soft_delete = ? WHERE ID_PRODUIT = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, softDeleteValue);
            ps.setInt(2, idProduit);  
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  
    }

    @Override
    public Produit mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_PRODUIT");
        String nom = rs.getString("NOM_PRODUIT");
        int brandId = rs.getInt("ID_BRAND");
        int categorieId = rs.getInt("ID_CATEGORIE");
        String description = rs.getString("DESCRIPTION_PRODUIT");
        int prixUnitaire = rs.getInt("PRIX_UNITAIRE");
        double note = rs.getDouble("NOTE");
        String imageSource = rs.getString("image_source");
        boolean softDelete = rs.getBoolean("soft_delete");

        DAOCategorie daoCategorie = new DAOCategorie(connection);
        DAOBrand daoBrand = new DAOBrand(connection);

        Brand brand = daoBrand.getById(brandId);
        Categorie categorie = daoCategorie.getById(categorieId);

        DAOMouvement daoMouvement = new DAOMouvement(connection);
        int stock = daoMouvement.calculerResteStock(id);

        return new Produit(id, nom, categorie, brand, prixUnitaire, description, note, imageSource, softDelete, stock);
    }
}
