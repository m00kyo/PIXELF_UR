import Database.User;
import Database.UserDAO;
import Database.UserSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel loginPanel;
    private JButton powrótButton;

    public LoginForm() {
        super("Logowanie");
        this.setContentPane(this.loginPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        loginButton.addActionListener(e -> {
            String login = textField1.getText();
            String password = new String(passwordField1.getPassword());

            new SwingWorker<User, Void>() {
                @Override
                protected User doInBackground() throws Exception {
                    UserDAO dao = new UserDAO();
                    return dao.authenticate(login, password);
                }

                @Override
                protected void done() {
                    try {
                        User loggedUser = get();
                        if (loggedUser != null) {
                            JOptionPane.showMessageDialog(null, "Zalogowano");
                            UserSession.getInstance().login(loggedUser);
                            dispose();
                            Options options = new Options();
                        } else {
                            JOptionPane.showMessageDialog(null, "Nie zalogowano");
                        }
                    } catch (Exception ex) {
                        ex.fillInStackTrace();
                        ex.printStackTrace();
                    }
                }
            }.execute();
        });
        powrótButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Welcome welcome = new Welcome();

            }
        });
    }
}
