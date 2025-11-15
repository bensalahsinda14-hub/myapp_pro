package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AppGUI {

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private java.util.List<String> storage = new ArrayList<>();

    public AppGUI() {

        JFrame frame = new JFrame("MyApp Pro - Interface Graphique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 320);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        JLabel lblName = new JLabel("Nom:");
        JTextField tfName = new JTextField(18);
        JLabel lblEmail = new JLabel("Email:");
        JTextField tfEmail = new JTextField(18);

        c.gridx = 0; c.gridy = 0; form.add(lblName, c);
        c.gridx = 1; c.gridy = 0; form.add(tfName, c);
        c.gridx = 0; c.gridy = 1; form.add(lblEmail, c);
        c.gridx = 1; c.gridy = 1; form.add(tfEmail, c);

        JList<String> jlist = new JList<>(listModel);
        JScrollPane scroll = new JScrollPane(jlist);
        scroll.setPreferredSize(new Dimension(440, 120));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnConnecter = new JButton("Connecter");
        JButton btnEffacer = new JButton("Effacer");
        JButton btnQuitter = new JButton("Quitter");

        buttons.add(btnAjouter);
        buttons.add(btnConnecter);
        buttons.add(btnEffacer);
        buttons.add(btnQuitter);

        frame.add(form, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);

        btnAjouter.addActionListener((ActionEvent e) -> {
            String name = tfName.getText().trim();
            String email = tfEmail.getText().trim();
            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Remplis nom + email avant d'ajouter.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String item = name + " <" + email + ">";
            storage.add(item);
            listModel.addElement(item);
            tfName.setText("");
            tfEmail.setText("");
        });

        btnConnecter.addActionListener((ActionEvent e) -> {
            if (storage.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Aucun utilisateur. Ajoute d'abord.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Connexion simulée réussie.\nNb utilisateurs: " + storage.size(), "Connecté", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnEffacer.addActionListener((ActionEvent e) -> {
            int sel = jlist.getSelectedIndex();
            if (sel >= 0) {
                storage.remove(sel);
                listModel.remove(sel);
            } else {
                JOptionPane.showMessageDialog(frame, "Sélectionne un élément pour effacer.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnQuitter.addActionListener((ActionEvent e) -> {
            frame.dispose();
            System.exit(0);
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppGUI::new);
    }
}
