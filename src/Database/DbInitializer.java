package Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitializer {

    public static void initialize() {
        String sqlUsers = """
                CREATE TABLE IF NOT EXISTS Users (
                    id SERIAL PRIMARY KEY,
                    login VARCHAR(50) NOT NULL UNIQUE,
                    password VARCHAR(100) NOT NULL
                );
                """;

        String sqlIngredients = """
                CREATE TABLE IF NOT EXISTS ingredients (
                    id SERIAL PRIMARY KEY,
                    id_user INTEGER NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    quantity VARCHAR(255),
                    CONSTRAINT fk_ingredients_user FOREIGN KEY (id_user) 
                        REFERENCES users(id) ON DELETE CASCADE
                );
                """;

        String sqlRecipes = """
                CREATE TABLE IF NOT EXISTS recipes (
                    id SERIAL PRIMARY KEY,
                    id_user INTEGER NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    description VARCHAR(255),
                    CONSTRAINT fk_recipes_user FOREIGN KEY (id_user) 
                        REFERENCES users(id) ON DELETE CASCADE
                );
                """;


        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement()) {


            stmt.execute(sqlUsers);
            stmt.execute(sqlIngredients);
            stmt.execute(sqlRecipes);


            System.out.println("Baza danych zainicjalizowana pomyślnie.");

        } catch (SQLException e) {
            System.err.println("Błąd podczas inicjalizacji bazy: " + e.getMessage());
            e.printStackTrace();
        }
    }

}