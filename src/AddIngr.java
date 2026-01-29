import Database.IngriedientDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class AddIngr extends JFrame {
    private JTextField textName;
    private JPanel addingIngr;
    private JTextField textQuan;
    private JButton addIngriedient;

    public AddIngr() {
        super("Składniki");
        this.setContentPane(this.addingIngr);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 300, height = 300;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        addIngriedient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Boolean, Void>(){
                    @Override
                    protected void done() {
                        super.done();
                        try {
                            boolean addResult = get();
                            if (addResult){
                                dispose();
                                Ingriedients ingriedients = new Ingriedients();
                            } else {
                                throw new Exception();
                            }
                        }  catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,"Uzupełnij poprawnie pola");
                        }
                    }

                    @Override
                    protected Boolean doInBackground() throws Exception {
                        IngriedientDAO ingriedientDAO = new IngriedientDAO();
                        return ingriedientDAO.create(textName.getText(), textQuan.getText());
                    }
                }.execute();
            }
        });
    }
}
