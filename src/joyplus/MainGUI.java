package joyplus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Joy Plus");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 

        createPanel();
    }
    private void createPanel() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(null);

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(50, 60, 80, 30);
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
           }
        });

        panel.add(quitButton);
    }
}
