import Database.Recipe;
import Database.RecipeDAO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Recipes extends JFrame {
    private JPanel recipesPanel;
    private JLabel Tytuł;
    private JButton backButton;
    private JButton buttonAdd;
    private JTable recipesTable;

    private ArrayList<Recipe> recipesList = new ArrayList<>();

    public Recipes() {
        super("Moje Przepisy");
        this.setContentPane(this.recipesPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        backButton.addActionListener(e -> {
            dispose();
            new Options();
        });

        String[] columnNames = {"Nazwa", "Opis"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        recipesTable.setModel(tableModel);

        loadData(tableModel);


        recipesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int row = recipesTable.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        new AddRec(recipesList.get(row));
                        dispose();
                    }
                }
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Usuń przepis");
        deleteItem.addActionListener(e -> {
            int row = recipesTable.getSelectedRow();
            if (row != -1) {
                int idToDelete = recipesList.get(row).getID();

                new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        return new RecipeDAO().delete(idToDelete);
                    }

                    @Override
                    protected void done() {
                        try {
                            if (get()) {
                                tableModel.removeRow(row);
                                recipesList.remove(row);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Nie udało się pomyślnie usunąć wiersza");
                        }
                    }
                }.execute();
            }
        });
        popupMenu.add(deleteItem);
        recipesTable.setComponentPopupMenu(popupMenu);

        buttonAdd.addActionListener(e -> {
            new AddRec(null);
            dispose();
        });
    }

    private void loadData(DefaultTableModel tableModel) {
        new SwingWorker<ArrayList<Recipe>, Void>() {
            @Override
            protected ArrayList<Recipe> doInBackground() throws Exception {
                return new RecipeDAO().getAll();
            }

            @Override
            protected void done() {
                try {
                    recipesList = get();
                    tableModel.setRowCount(0);
                    for (Recipe r : recipesList) {
                        tableModel.addRow(new Object[]{r.getName(), r.getDescription()});
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nie udało się poparwnie wczytać danych");
                }
            }
        }.execute();
    }
}