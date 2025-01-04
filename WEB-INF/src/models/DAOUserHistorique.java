package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.*;

public class DAOUserHistorique extends DAO<UserHistorique> {
    private DAOUser daoUser;
    private DAOMouvement daoMouvement;

    public DAOUserHistorique(Connection connection) {
        super(connection);
        this.daoUser = new DAOUser(connection); // Initialisation du DAOUser
        this.daoMouvement = new DAOMouvement(connection); // Initialisation du DAOMouvement
    }

    @Override
    public List<UserHistorique> getAll() {
        List<UserHistorique> historiques = new ArrayList<>();
        String query = "SELECT * FROM USER_HISTORY";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UserHistorique historique = mapResultSetToEntity(rs);
                historiques.add(historique);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiques;
    }

    @Override
    public UserHistorique getById(int id) {
        String query = "SELECT * FROM USER_HISTORY WHERE ID_USER_HISTORY = ?";
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
    public int create(UserHistorique entity) {
        String query = "INSERT INTO USER_HISTORY (ID_FACTURE, ID_MARKET_USER, ID_MOUVEMENT) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, entity.getIdFacture());
            pstmt.setInt(2, entity.getMarketUser().getId());  // Assurez-vous de passer l'ID de l'utilisateur
            pstmt.setInt(3, entity.getMouvement().getIdMouvement()); // Id du mouvement

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        entity.setIdUserHistory(id);
                        return id; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void update(UserHistorique entity) {
        String query = "UPDATE USER_HISTORY SET ID_FACTURE = ?, ID_MARKET_USER = ?, ID_MOUVEMENT = ? WHERE ID_USER_HISTORY = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, entity.getIdFacture());
            pstmt.setInt(2, entity.getMarketUser().getId());  // Mise à jour de l'ID utilisateur
            pstmt.setInt(3, entity.getMouvement().getIdMouvement()); // Mise à jour du mouvement
            pstmt.setInt(4, entity.getIdUserHistory());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM USER_HISTORY WHERE ID_USER_HISTORY = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserHistorique mapResultSetToEntity(ResultSet rs) throws SQLException {
        int idUserHistory = rs.getInt("ID_USER_HISTORY");
        int idFacture = rs.getInt("ID_FACTURE");
        int idMarketUser = rs.getInt("ID_MARKET_USER");
        int idMouvement = rs.getInt("ID_MOUVEMENT");

        User marketUser = daoUser.getById(idMarketUser);

        Mouvement mouvement = daoMouvement.getById(idMouvement);

        UserHistorique historique = new UserHistorique(idUserHistory, idFacture, idMarketUser, idMouvement);
        historique.setMarketUser(marketUser);
        historique.setMouvement(mouvement);

        return historique;
    }
}
