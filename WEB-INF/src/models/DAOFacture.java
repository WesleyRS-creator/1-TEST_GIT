package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.*;

public class DAOFacture extends DAO<Facture> {

    public DAOFacture(Connection connection) {
        super(connection);
    }

    @Override
    public List<Facture> getAll() {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM FACTURE";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                factures.add(mapResultSetToEntity(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factures;
    }

    @Override
    public Facture getById(int id) {
        Facture facture = null;
        String query = "SELECT * FROM FACTURE WHERE ID_FACTURE = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                facture = mapResultSetToEntity(rs);
            }

            closeResources(ps, rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facture;
    }

    @Override
    public int create(Facture entity) {
        int generatedId = -1;
        String query = "INSERT INTO FACTURE DEFAULT VALUES RETURNING ID_FACTURE";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                generatedId = rs.getInt(1);
                entity.setId(generatedId);
            }

            closeResources(ps, rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    @Override
    public void update(Facture entity) {
        throw new UnsupportedOperationException("La mise à jour n'est pas implémentée pour la table FACTURE.");
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM FACTURE WHERE ID_FACTURE = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Facture mapResultSetToEntity(ResultSet rs) throws SQLException {
        Facture facture = new Facture();
        facture.setId(rs.getInt("ID_FACTURE"));
        return facture;
    }


    public Facture getLastFacture() {
        Facture facture = null;
        String query = "SELECT * FROM FACTURE ORDER BY ID_FACTURE DESC LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                facture = mapResultSetToEntity(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facture;
    }
}
