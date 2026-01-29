import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Options extends JFrame{
    private JButton myrecButton;
    private JButton myingrButton;
    private JPanel panelWyborow;

    public Options() {
        super("Logowanie");
        this.setContentPane(this.panelWyborow);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        myingrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Ingriedients ingriedients = new Ingriedients();
;            }
        });
        myrecButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Recipes recipes = new Recipes();
            }
        });
    }
}
