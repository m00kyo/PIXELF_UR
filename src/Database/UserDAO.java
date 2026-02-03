package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User authenticate(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);
            System.out.println(stmt.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Znaleziono użytkownika.");
                return new User(
                        rs.getInt("id"),
                        rs.getString("login")
                );
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public boolean create(String login, String password) {
        String checkSql = "SELECT * FROM users WHERE login = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkSql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                System.out.println("Taki użytkownik już istnieje!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String insertSql = "INSERT INTO users (login, password) VALUES (?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy dodawaniu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}