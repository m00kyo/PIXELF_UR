import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame{
    private JPanel LoginPanel;
    private JButton wybierzUzytkownikaButton;
    private JButton dodajUzytkownikaButton;
    private JLabel logoLabel;

    public Welcome(){
        setContentPane(LoginPanel);
        setTitle("PIXELF");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        this.setLocationRelativeTo(null);
        setVisible(true);
        wybierzUzytkownikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm();
            }
        });
        dodajUzytkownikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                NewUser newUser = new NewUser();
            }
        });
    }
}
