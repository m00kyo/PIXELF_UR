import Database.Ingriedient;
import Database.IngriedientDAO;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Ingriedients extends JFrame {
    private JLabel Składniki;
    private JPanel ingrPanel;
    private JButton backButton;
    private JButton buttonAdd;
    private JTable ingrTable;


    private ArrayList<Ingriedient> ingriedients = new ArrayList<>();
    private boolean isRestoringValue = false;


    public Ingriedients() {
        super("Składniki");
        this.setContentPane(this.ingrPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Options options = new Options();
            }
        });

        String[] columnNames = {"Nazwa", "Ilość"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
        ingrTable.setModel(tableModel);

        new SwingWorker<ArrayList<Ingriedient>, Void>() {
            @Override
            protected ArrayList<Ingriedient> doInBackground() throws Exception {
                IngriedientDAO ingriedientDAO = new IngriedientDAO();
                return ingriedientDAO.getAll();

            }

            @Override
            protected void done() {
                super.done();
                try {
                    ingriedients = get();

                    tableModel.setRowCount(0);

                    for (Ingriedient i:ingriedients){
                        Object[] row = {i.getName(), i.getQuantity()};
                        tableModel.addRow(row);
                    }


                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nie udało się wczytać danych");
                }
            }
        }.execute();

        tableModel.addTableModelListener(e ->{
            if(isRestoringValue) {
                return;
            }

            if (e.getType() == TableModelEvent.UPDATE){
                int row = e.getFirstRow();

                String newName = tableModel.getValueAt(row, 0).toString();
                String newQuantity = tableModel.getValueAt(row, 1).toString();

                Ingriedient oldIngredientData = new Ingriedient(ingriedients.get(row));

                Ingriedient i = ingriedients.get(row);
                i.setName(newName);
                i.setQuantity(newQuantity);

                new SwingWorker<Boolean, Void>() {

                    @Override
                    protected Boolean doInBackground() throws Exception {
                        IngriedientDAO ingriedientDAO = new IngriedientDAO();
                        return ingriedientDAO.update(
                                ingriedients.get(row).getID(),
                                newName,
                                newQuantity
                        );
                    }

                    @Override
                    protected void done() {
                        super.done();
                        try {
                            boolean updateResult = get();
                            if (!updateResult){
                                throw new Exception();
                            }
                        } catch (Exception ex) {
                            isRestoringValue = true;
                            JOptionPane.showMessageDialog(null, "Uzupełnij poprawnie pola.");
                            tableModel.setValueAt(oldIngredientData.getName(), row, 0);
                            tableModel.setValueAt(oldIngredientData.getQuantity(), row, 1);
                            ingriedients.set(row, new Ingriedient(oldIngredientData));
                        } finally {
                            isRestoringValue = false;
                        }
                    }
                }.execute();
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Usuń składnik");
        deleteItem.addActionListener(e -> {
            int row = ingrTable.getSelectedRow();
            if (row != -1) {

                new  SwingWorker<Boolean, Void>(){


                    @Override
                    protected Boolean doInBackground() throws Exception {
                        IngriedientDAO ingriedientDAO = new IngriedientDAO();
                        return ingriedientDAO.delete(ingriedients.get(row).getID());
                    }

                    @Override
                    protected void done() {
                        super.done();
                        try {
                            boolean deleteResult = get();
                            if (deleteResult){
                                tableModel.removeRow(row);
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Nie udało się usunąć wiersza.");
                        }

                    }
                }.execute();
            }
        });
        popupMenu.add(deleteItem);
        ingrTable.setComponentPopupMenu(popupMenu);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddIngr addIngr = new AddIngr();
            }
        });
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}