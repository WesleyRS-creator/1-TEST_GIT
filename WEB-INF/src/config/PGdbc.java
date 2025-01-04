package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PGdbc {
        private Connection connection;

        public void openDefaultConnection() throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/supermarket";
            String user = "supermarket_admin";
            String password = "1234";
            openConnection(url, user, password);
        }

        public void openConnection(String url, String user, String password) throws SQLException {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (Exception e) {
                System.out.println("Driver PostgreSQL introuvable !");
                e.printStackTrace();
            }

            setConnection(DriverManager.getConnection(url, user, password));
            System.out.println("Connexion ouverte avec succès");
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }


        public void closeConnection() {
            if (connection != null) {
                try {
                    getConnection().close();
                    System.out.println("Connexion fermée avec succès.");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("La connexion est déjà fermée.");
            }
        }
}