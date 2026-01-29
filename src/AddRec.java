import Database.Recipe;
import Database.RecipeDAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class AddRec extends JFrame {
    private JPanel addingRecipe;
    private JTextField textName;
    private JTextArea textDesc;
    private JButton addRecipe;

    public AddRec(Recipe recipeToUpdate) {
        super("Dodaj Przepis");
        this.setContentPane(this.addingRecipe);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 300, height = 300;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if(recipeToUpdate != null) {
            textName.setText(recipeToUpdate.getName());
            textDesc.setText(recipeToUpdate.getDescription());
        }

        addRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textName.getText().isEmpty() || textDesc.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Wypełnij wszystkie pola!");
                    return;
                }

                new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        RecipeDAO recipeDAO = new RecipeDAO();
                        if (recipeToUpdate == null) {
                            return recipeDAO.create(textName.getText(), textDesc.getText());
                        } else {
                            return recipeDAO.update(recipeToUpdate.getID(), textName.getText(), textDesc.getText());
                        }
                    }

                    @Override
                    protected void done() {
                        super.done();
                        try {
                            boolean addResult = get();
                            if (addResult) {
                                dispose();
                                new Recipes();
                            } else {
                                JOptionPane.showMessageDialog(null, "Błąd podczas dodawania do bazy danych.");
                            }
                        } catch (InterruptedException | ExecutionException ex) {
                            JOptionPane.showMessageDialog(null, "Wystąpił błąd krytyczny: " + ex.getMessage());
                        }
                    }
                }.execute();
            }
        });
    }
}