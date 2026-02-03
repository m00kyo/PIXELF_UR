import Database.UserDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUser extends JFrame {
    private JTextField txLogin;
    private JPanel NewUser;
    private JPasswordField txPass1;
    private JButton zapiszButton;
    private JPasswordField txPass2;

    public NewUser(){
        super("Tworzenie konta");
        this.setContentPane(this.NewUser);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String haslo1 = new String(txPass1.getPassword());
                String haslo2 = new String(txPass2.getPassword());

                boolean matches = haslo1.equals(haslo2);

                if (matches==false){
                    JOptionPane.showMessageDialog(null, "Hasła nie są takie same!");
                    return;
                }

                zapiszButton.setEnabled(false);

                new SwingWorker<Boolean, Void>(){

                    @Override
                    protected Boolean doInBackground() throws Exception {
                        UserDAO user = new UserDAO();
                        if(!txLogin.getText().isBlank() && !haslo1.isBlank()) {
                            return user.create(txLogin.getText(), haslo1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Uzupełnij wszystkie pola!");
                        }
                        return false;
                    }

                    @Override
                    protected void done() {
                        zapiszButton.setEnabled(true);

                        try {
                            boolean success = get();

                            if (success) {
                                JOptionPane.showMessageDialog(null, "Konto zostało utworzone :)");
                                dispose();
                                new Welcome();
                            } else {
                                JOptionPane.showMessageDialog(null, "Nie udało się utworzyć konta (może login jest zajęty?).");
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();

                            JOptionPane.showMessageDialog(null, "Błąd krytyczny");
                        }
                    }
                }.execute();
            }
        });
    }
}
