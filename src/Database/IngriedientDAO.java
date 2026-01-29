package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IngriedientDAO {

    public boolean create(String name, String quantity) {


        String insertSql = "INSERT INTO ingredients (name, id_user, quantity) VALUES (?, ?, ?)";
        if (name.isBlank() || quantity.isBlank()){
            return false;
        }

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setString(1, name);
            stmt.setInt(2, UserSession.getInstance().getUserID());
            stmt.setString(3,quantity);

            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy dodawaniu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Ingriedient getOne(int id){

        String insertSql = "SELECT * FROM ingredients WHERE id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return new Ingriedient(
                        rs.getInt("id"), rs.getString("name"), rs.getString("quantity")
                );
            }
            return null;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy dodawaniu: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Ingriedient> getAll(){
        String insertSql = "SELECT * FROM ingredients WHERE id_user=?";
        ArrayList <Ingriedient> ingriedients = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setInt(1, UserSession.getInstance().getUserID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                ingriedients.add(new Ingriedient(
                        rs.getInt("id"), rs.getString("name"), rs.getString("quantity")
                ));
            }
            return ingriedients;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy dodawaniu: " + e.getMessage());
            e.printStackTrace();
            return ingriedients;
        }
    }

    public boolean update(int id, String name, String quantity){
        String insertSql = "UPDATE ingredients SET name = ?, quantity = ? WHERE id = ?";
        if (name.isBlank() || quantity.isBlank()){
            return false;
        }

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setString(1, name);
            stmt.setInt(3, id);
            stmt.setString(2,quantity);

            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy dodawaniu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id){
        String insertSql = "DELETE FROM ingredients WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.err.println("Błąd SQL przy dodawaniu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
