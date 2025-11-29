package com.mycompany.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AppGUI {

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private List<Client> storage = new ArrayList<>();
    private static final String DEFAULT_FILE_URL = "file:///home/devops/myapp_pro/mon-cabinet/index.html";
    private static final String MSG_ERROR = "Erreur";
    private static final String FONT_NAME = "Segoe UI";
    private static final String FONT_EMOJI = "Segoe UI Emoji";

    static class Client {
        String plaig, defend, email, cin, service, description;
        Client(String plaig, String defend, String email, String cin, String service, String description) {
            this.plaig = plaig; this.defend = defend; this.email = email; this.cin = cin;
            this.service = service; this.description = description;
        }
        @Override public String toString() { return plaig + " vs " + defend + " | " + service; }
    }

    private JButton btnAjouter, btnOuvrir, btnVoir, btnSupprimer, btnQuitter;

    public AppGUI() {
        JFrame frame = new JFrame("⚖️ CABINET MARTIN & ASSOCIÉS - Gestion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 780);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(12,12));

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 18));
        header.setBackground(new Color(10,36,99));
        JLabel icon = new JLabel("⚖️"); icon.setForeground(Color.WHITE); icon.setFont(new Font(FONT_EMOJI, Font.PLAIN, 36));
        JLabel title = new JLabel("CABINET MARTIN & ASSOCIÉS"); title.setForeground(Color.WHITE); title.setFont(new Font(FONT_NAME, Font.BOLD, 32));
        header.add(icon); header.add(title);
        frame.add(header, BorderLayout.NORTH);

        // Center
        JPanel center = new JPanel(new BorderLayout(10,10));
        center.setBorder(new EmptyBorder(12,20,12,20));
        center.setBackground(new Color(245,247,250));

        // Form fields
        JTextField tfPlaignant = new JTextField(18);
        JTextField tfDefendeur = new JTextField(18);
        JTextField tfEmail = new JTextField(18);
        JTextField tfCIN = new JTextField(10);
        JComboBox<String> cbService = new JComboBox<>(new String[]{"Droit des Affaires", "Droit du Travail", "Droit Immobilier", "Droit de la Famille", "Conseil Fiscal", "Contentieux"});
        JTextArea taDescription = new JTextArea(4,48);
        taDescription.setLineWrap(true); taDescription.setWrapStyleWord(true);

        JPanel formPanel = new JPanel(new GridLayout(5,2,12,12));
        formPanel.add(new JLabel("Plaignant:")); formPanel.add(tfPlaignant);
        formPanel.add(new JLabel("Défendeur:")); formPanel.add(tfDefendeur);
        formPanel.add(new JLabel("Email:")); formPanel.add(tfEmail);
        formPanel.add(new JLabel("CIN:")); formPanel.add(tfCIN);
        formPanel.add(cbService); formPanel.add(new JScrollPane(taDescription));
        center.add(formPanel, BorderLayout.NORTH);

        // Liste
        JList<String> jlist = new JList<>(listModel);
        jlist.setCellRenderer(new CardRenderer());
        center.add(new JScrollPane(jlist), BorderLayout.CENTER);

        frame.add(center, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER,20,12));
        btnAjouter = styledButton("➕ Ajouter", new Color(16,185,129));
        btnOuvrir = styledButton("🌐 Ouvrir Site", new Color(59,130,246));
        btnVoir = styledButton("👁️ Voir Détails", new Color(139,92,246));
        btnSupprimer = styledButton("🗑️ Supprimer", new Color(239,68,68));
        btnQuitter = styledButton("❌ Quitter", new Color(75,85,99));
        buttons.add(btnAjouter); buttons.add(btnOuvrir); buttons.add(btnVoir); buttons.add(btnSupprimer); buttons.add(btnQuitter);
        frame.add(buttons, BorderLayout.PAGE_END);

        initActions(tfPlaignant, tfDefendeur, tfEmail, tfCIN, cbService, taDescription, jlist, frame);
        frame.setVisible(true);
    }

    private void initActions(JTextField tfPlaignant, JTextField tfDefendeur, JTextField tfEmail, JTextField tfCIN,
                             JComboBox<String> cbService, JTextArea taDescription, JList<String> jlist, JFrame frame) {

        btnAjouter.addActionListener(e -> {
            Client c = new Client(tfPlaignant.getText(), tfDefendeur.getText(), tfEmail.getText(), tfCIN.getText(),
                    (String)cbService.getSelectedItem(), taDescription.getText());
            storage.add(c); listModel.addElement(c.toString());
            tfPlaignant.setText(""); tfDefendeur.setText(""); tfEmail.setText(""); tfCIN.setText(""); taDescription.setText("");
        });

        btnVoir.addActionListener(e -> {
            int sel = jlist.getSelectedIndex();
            if(sel>=0) {
                Client c = storage.get(sel);
                JOptionPane.showMessageDialog(frame, c.toString(), "Détails", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnOuvrir.addActionListener(e -> {
            try { Desktop.getDesktop().browse(new URI(DEFAULT_FILE_URL)); } catch(Exception ex) { JOptionPane.showMessageDialog(frame, "Impossible d'ouvrir", MSG_ERROR, JOptionPane.ERROR_MESSAGE); }
        });

        btnSupprimer.addActionListener(e -> {
            int sel = jlist.getSelectedIndex();
            if(sel>=0) { storage.remove(sel); listModel.remove(sel); }
        });

        btnQuitter.addActionListener(e -> System.exit(0));
    }

    private JButton styledButton(String text, Color bg) {
        JButton b = new JButton(text); b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        b.setPreferredSize(new Dimension(160,44));
        return b;
    }

    private static class CardRenderer extends JPanel implements ListCellRenderer<String> {
        private JLabel label;
        CardRenderer() { setLayout(new BorderLayout()); setOpaque(true); label=new JLabel(); add(label, BorderLayout.CENTER); }
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            label.setText(value);
            setBackground(isSelected?new Color(204,228,255):new Color(245,247,250));
            return this;
        }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(AppGUI::new); }

}
