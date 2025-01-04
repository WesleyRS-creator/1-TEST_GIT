package models;

import main.TypeMouvement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOTypeMouvement extends DAO<TypeMouvement> {

    public DAOTypeMouvement(Connection connection) {
        super(connection);
    }

    @Override
    public List<TypeMouvement> getAll() {
        List<TypeMouvement> types = new ArrayList<>();
        String query = "SELECT * FROM TYPE_MOUVEMENT";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                types.add(mapResultSetToEntity(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return types;
    }

    @Override
    public TypeMouvement getById(int id) {
        TypeMouvement type = null;
        String query = "SELECT * FROM TYPE_MOUVEMENT WHERE ID_TYPE_MOUVEMENT = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                type = mapResultSetToEntity(rs);
            }

            closeResources(ps, rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }

    @Override
    public int create(TypeMouvement entity) {
        int generatedId = -1;
        String query = "INSERT INTO TYPE_MOUVEMENT (DESCRIPTION_TYPE_MOUVEMENT) VALUES (?) RETURNING ID_TYPE_MOUVEMENT";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getDescriptionType_Mouvement());
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
    public void update(TypeMouvement entity) {
        String query = "UPDATE TYPE_MOUVEMENT SET DESCRIPTION_TYPE_MOUVEMENT = ? WHERE ID_TYPE_MOUVEMENT = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getDescriptionType_Mouvement());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM TYPE_MOUVEMENT WHERE ID_TYPE_MOUVEMENT = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TypeMouvement mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_TYPE_MOUVEMENT");
        String description = rs.getString("DESCRIPTION_TYPE_MOUVEMENT");
        return new TypeMouvement(id, description);
    }
}
