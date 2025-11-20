package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AppGUI {

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private List<String> storage = new ArrayList<>();

    public AppGUI() {
        JFrame frame = new JFrame("MyApp Pro - Interface Graphique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Formulaire utilisateur
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        JLabel lblName = new JLabel("Nom:");
        JTextField tfName = new JTextField(18);

        JLabel lblEmail = new JLabel("Email:");
        JTextField tfEmail = new JTextField(18);

        c.gridx = 0;
        c.gridy = 0;
        form.add(lblName, c);
        c.gridx = 1;
        form.add(tfName, c);

        c.gridx = 0;
        c.gridy = 1;
        form.add(lblEmail, c);
        c.gridx = 1;
        form.add(tfEmail, c);

        // Liste des utilisateurs
        JList<String> jlist = new JList<>(listModel);
        JScrollPane scroll = new JScrollPane(jlist);
        scroll.setPreferredSize(new Dimension(460, 150));

        // Boutons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

        // Action Ajouter
        btnAjouter.addActionListener((ActionEvent e) -> {
            String name = tfName.getText().trim();
            String email = tfEmail.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Remplis nom et email !");
                return;
            }

            // Validation nom (lettres seulement)
            if (!name.matches("[a-zA-ZÀ-ÿ\\s'-]+")) {
                JOptionPane.showMessageDialog(frame, "Nom invalide !");
                return;
            }

            // Validation email
            String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            if (!email.matches(emailRegex)) {
                JOptionPane.showMessageDialog(frame, "Email invalide !");
                return;
            }

            String item = name + " <" + email + ">";
            storage.add(item);
            listModel.addElement(item);

            tfName.setText("");
            tfEmail.setText("");
        });

        // Action Connecter
        btnConnecter.addActionListener((ActionEvent e) -> {
            if (storage.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Aucun utilisateur disponible !");
                return;
            }

            String[] pages = {"index.html", "about.html", "shop.html", "service.html"};
            String choix = (String) JOptionPane.showInputDialog(
                    frame,
                    "Choisis une page du site à ouvrir:",
                    "Ouvrir site",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    pages,
                    pages[0]
            );

            if (choix != null) {
                try {
                    // Remplace le chemin ci-dessous par le chemin réel vers ton dossier
                    String path = "file:///home/devops/myapp_pro/mon_site_pro/" + choix;
                    Desktop.getDesktop().browse(new URI(path));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Impossible d'ouvrir la page !");
                }
            }
        });

        // Action Effacer
        btnEffacer.addActionListener((ActionEvent e) -> {
            int sel = jlist.getSelectedIndex();
            if (sel >= 0) {
                String code = JOptionPane.showInputDialog(frame, "Entre le code de suppression (1234) :");
                if (code != null && code.matches("\\d{4}")) {
                    if (code.equals("1234")) {
                        storage.remove(sel);
                        listModel.remove(sel);
                        JOptionPane.showMessageDialog(frame, "Utilisateur supprimé !");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Code incorrect !");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Code invalide !");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Sélectionne un utilisateur !");
            }
        });

        // Action Quitter
        btnQuitter.addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Voulez‑vous vraiment quitter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppGUI::new);
    }
}

