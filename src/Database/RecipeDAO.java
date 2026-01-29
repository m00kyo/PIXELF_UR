package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RecipeDAO {

    public boolean create(String name, String description) {
        String sql = "INSERT INTO recipes (name, id_user, description) VALUES (?, ?, ?)";
        if (name.isBlank() || description.isBlank()){
            return false;
        }

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, UserSession.getInstance().getUserID());
            stmt.setString(3, description);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy dodawaniu przepisu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Recipe getOne(int id) {
        String sql = "SELECT * FROM recipes WHERE id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Recipe(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
            }
            return null;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy pobieraniu przepisu: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Recipe> getAll() {
        String sql = "SELECT * FROM recipes WHERE id_user=?";
        ArrayList<Recipe> recipes = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, UserSession.getInstance().getUserID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                recipes.add(new Recipe(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
            return recipes;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy pobieraniu wszystkich przepisów: " + e.getMessage());
            e.printStackTrace();
            return recipes;
        }
    }

    public boolean update(int id, String name, String description) {
        String sql = "UPDATE recipes SET name = ?, description = ? WHERE id = ?";
        if (name.isBlank() || description.isBlank()){
            return false;
        }

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, id);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy aktualizacji przepisu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM recipes WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy usuwaniu przepisu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}