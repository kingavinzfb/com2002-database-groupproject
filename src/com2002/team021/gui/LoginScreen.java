package com2002.team021.gui;

import com2002.team021.Practitioner;
import com2002.team021.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginScreen.java
 */

public class LoginScreen extends JFrame {

    private JButton secretary, hygienist, dentist, exit;
    private Container contentPane;

    public void loginDialog(String title) {
        if (title.equals("Secretary Log In") ) {
                dispose();
                new ChoiceScreen();
        }
        else {
            if (title.equals("Hygienist Log In")) {
                practitionerLogin("Hygienist");
            }
            else if (title.equals("Dentist Log In")) {
                practitionerLogin("Dentist");
            }
        }
    }

    public void practitionerLogin (String partner){
        try {
            Query query = new Query();
            Practitioner practitioner = query.getPractitioner(partner);
            dispose();
            new ChoiceScreen(practitioner);
        }
        catch (java.sql.SQLException e) {}
    }

    public LoginScreen() {

        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}


        setTitle("Login Screen");
        setSize(900,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setLayout(null);

        //Create buttons
        secretary = new JButton("Secretary");
        hygienist = new JButton("Hygienist");
        dentist = new JButton("Dentist");
        exit = new JButton("Exit");

        //Add Listeners
        secretary.addActionListener(new btnSec_Action());
        hygienist.addActionListener(new btnHyg_Action());
        dentist.addActionListener(new btnDent_Action());
        exit.addActionListener(new btnExit_Action());

        //Add buttons
        contentPane.add(secretary);
        contentPane.add(hygienist);
        contentPane.add(dentist);
        contentPane.add(exit);

        //Set Bounds
        secretary.setBounds(300, 100, 300, 25);
        hygienist.setBounds(300, 175, 300, 25);
        dentist.setBounds(300, 255, 300, 25);
        exit.setBounds(300, 330, 300, 25);

        //Make fram visible
        setResizable(false);
        setVisible(true);
    }

    private class btnSec_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loginDialog("Secretary Log In");
        }
    }

    private class btnHyg_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loginDialog("Hygienist Log In");
        }
    }

    private class btnDent_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loginDialog("Dentist Log In");
        }
    }

    private class btnExit_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
