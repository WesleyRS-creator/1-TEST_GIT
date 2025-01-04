package models;

import main.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUser extends DAO<User> {

    public DAOUser(Connection connection) {
        super(connection);
    }

    @Override
    public int create(User user) {
        String query = "INSERT INTO MARKET_USER (NOM, MOT_DE_PASSE, ADMIN) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getNom());
            pstmt.setString(2, user.getMotDePasse());
            pstmt.setBoolean(3, user.isAdmin());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        user.setId(id);
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
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM MARKET_USER";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = mapResultSetToEntity(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM MARKET_USER WHERE ID_MARKET_USER = ?";
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


    public User getByName(String name) {
        String query = "SELECT * FROM MARKET_USER WHERE NOM = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
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

    public boolean isInData(String nom, String motDePasse) {
        String query = "SELECT * FROM MARKET_USER WHERE NOM = ? AND MOT_DE_PASSE = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, motDePasse);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getByAuth(String name, String password) throws Exception {
        if (isInData(name, password)) {
            DAOUser daoUser = new DAOUser(connection);
            User u = daoUser.getByName(name);
            return u;
        } else {
            throw new Exception("Utilisateur non répertorié dans la base de donnée");
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE MARKET_USER SET NOM = ?, MOT_DE_PASSE = ?, ADMIN = ? WHERE ID_MARKET_USER = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getNom());
            pstmt.setString(2, user.getMotDePasse());
            pstmt.setBoolean(3, user.isAdmin());
            pstmt.setInt(4, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM MARKET_USER WHERE ID_MARKET_USER = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public User mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new User(rs.getInt("ID_MARKET_USER"), rs.getString("NOM"), rs.getString("MOT_DE_PASSE"), rs.getBoolean("ADMIN"));
    }
}
