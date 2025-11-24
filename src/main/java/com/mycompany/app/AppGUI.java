package com.mycompany.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AppGUI {

private DefaultListModel<String> listModel = new DefaultListModel<>();
private List<Client> storage = new ArrayList<>();
private static final String DEFAULT_FILE_URL = "file:///home/devops/myapp_pro/mon-cabinet/index.html";

static class Client {
    String plaig, defend, email, cin, service, description;

    Client(String plaig, String defend, String email, String cin, String service, String description) {
        this.plaig = plaig; this.defend = defend; this.email = email;
        this.cin = cin; this.service = service; this.description = description;
    }

    @Override
    public String toString() {
        return plaig + " vs " + defend + " | " + service;
    }
}

public AppGUI() {
    JFrame frame = new JFrame("⚖️ CABINET MARTIN & ASSOCIÉS - Gestion");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(960, 780);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout(12, 12));

    // ---------------- Header ----------------
    JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 18));
    header.setBackground(new Color(10, 36, 99));
    header.setPreferredSize(new Dimension(frame.getWidth(), 100));
    JLabel icon = new JLabel("⚖️");
    icon.setForeground(Color.WHITE); icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
    JLabel title = new JLabel("CABINET MARTIN & ASSOCIÉS");
    title.setForeground(Color.WHITE); title.setFont(new Font("Segoe UI", Font.BOLD, 32));
    header.add(icon); header.add(title);
    frame.add(header, BorderLayout.NORTH);

    // ---------------- Center Panel ----------------
    JPanel center = new JPanel(new BorderLayout(10, 10));
    center.setBorder(new EmptyBorder(12, 20, 12, 20));
    center.setBackground(new Color(245, 247, 250)); // background léger

    // ---- Formulaire ----
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(255, 255, 255));
    formPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0, 51, 102), 2, true),
            new EmptyBorder(16, 16, 16, 16)
    ));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 12, 6, 12);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel lblPlaignant = new JLabel("Plaignant:"); JTextField tfPlaignant = new JTextField(18);
    JLabel lblDefendeur = new JLabel("Défendeur:"); JTextField tfDefendeur = new JTextField(18);
    JLabel lblEmail = new JLabel("Email:"); JTextField tfEmail = new JTextField(18);
    JLabel lblCIN = new JLabel("CIN:"); JTextField tfCIN = new JTextField(10);
    JLabel lblService = new JLabel("Service:");
    String[] services = {"Droit des Affaires", "Droit du Travail", "Droit Immobilier", "Droit de la Famille", "Conseil Fiscal", "Contentieux"};
    JComboBox<String> cbService = new JComboBox<>(services);
    JLabel lblDescription = new JLabel("Description:");
    JTextArea taDescription = new JTextArea(4, 48);
    taDescription.setLineWrap(true); taDescription.setWrapStyleWord(true);
    JScrollPane descScroll = new JScrollPane(taDescription,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    gbc.gridx=0; gbc.gridy=0; formPanel.add(lblPlaignant, gbc);
    gbc.gridx=1; gbc.gridy=0; formPanel.add(tfPlaignant, gbc);
    gbc.gridx=2; gbc.gridy=0; formPanel.add(lblDefendeur, gbc);
    gbc.gridx=3; gbc.gridy=0; formPanel.add(tfDefendeur, gbc);

    gbc.gridx=0; gbc.gridy=1; formPanel.add(lblEmail, gbc);
    gbc.gridx=1; gbc.gridy=1; formPanel.add(tfEmail, gbc);
    gbc.gridx=2; gbc.gridy=1; formPanel.add(lblCIN, gbc);
    gbc.gridx=3; gbc.gridy=1; formPanel.add(tfCIN, gbc);

    gbc.gridx=0; gbc.gridy=2; formPanel.add(lblService, gbc);
    gbc.gridx=1; gbc.gridy=2; formPanel.add(cbService, gbc);

    gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=1; formPanel.add(lblDescription, gbc);
    gbc.gridx=1; gbc.gridy=3; gbc.gridwidth=3; formPanel.add(descScroll, gbc);

    center.add(formPanel, BorderLayout.NORTH);

    // ---- Liste des demandes ----
    JPanel listPanel = new JPanel(new BorderLayout());
    listPanel.setBackground(new Color(255, 255, 255));
    listPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0, 51, 102), 2, true),
            new EmptyBorder(16, 16, 16, 16)
    ));

    JList<String> jlist = new JList<>(listModel);
    jlist.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    jlist.setFixedCellHeight(36);
    jlist.setCellRenderer(new CardRenderer());

    JScrollPane listScroll = new JScrollPane(jlist);
    listScroll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    listScroll.setPreferredSize(new Dimension(880, 200));
    listPanel.add(listScroll, BorderLayout.CENTER);
    center.add(listPanel, BorderLayout.CENTER);

    frame.add(center, BorderLayout.CENTER);

    // ---------------- Footer Buttons ----------------
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
    buttons.setBackground(new Color(245, 247, 250));
    buttons.setBorder(new EmptyBorder(10, 0, 20, 0));

    JButton btnAjouter = styledButton("➕ Ajouter", new Color(16, 185, 129));
    JButton btnOuvrir = styledButton("🌐 Ouvrir Site", new Color(59, 130, 246));
    JButton btnVoir = styledButton("👁️ Voir Détails", new Color(139, 92, 246));
    JButton btnSupprimer = styledButton("🗑️ Supprimer", new Color(239, 68, 68));
    JButton btnQuitter = styledButton("❌ Quitter", new Color(75, 85, 99));

    buttons.add(btnAjouter); buttons.add(btnOuvrir); buttons.add(btnVoir);
    buttons.add(btnSupprimer); buttons.add(btnQuitter);
    frame.add(buttons, BorderLayout.PAGE_END);

    // ---------------- Actions ----------------
    btnAjouter.addActionListener((ActionEvent e) -> {
        String plaig = tfPlaignant.getText().trim();
        String defend = tfDefendeur.getText().trim();
        String email = tfEmail.getText().trim();
        String cin = tfCIN.getText().trim();
        String service = (String) cbService.getSelectedItem();
        String desc = taDescription.getText().trim();

        if (plaig.isEmpty() || defend.isEmpty() || email.isEmpty() || cin.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Tous les champs obligatoires doivent être remplis.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!cin.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(frame, "Le CIN doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(frame, "Email invalide.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Client client = new Client(plaig, defend, email, cin, service, desc);
        storage.add(client); listModel.addElement(client.toString());

        tfPlaignant.setText(""); tfDefendeur.setText(""); tfEmail.setText(""); tfCIN.setText(""); taDescription.setText("");
        JOptionPane.showMessageDialog(frame, "✅ Demande ajoutée avec succès !");
    });

    btnVoir.addActionListener((ActionEvent e) -> {
        int sel = jlist.getSelectedIndex();
        if (sel >= 0) {
            Client c = storage.get(sel);
            String msg = "Plaignant : " + c.plaig + "\nDéfendeur : " + c.defend +
                    "\nEmail : " + c.email + "\nCIN : " + c.cin +
                    "\nService : " + c.service + "\n\nDescription :\n" + c.description;
            JTextArea ta = new JTextArea(msg); ta.setEditable(false); ta.setBackground(null); ta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            JScrollPane sp = new JScrollPane(ta); sp.setPreferredSize(new Dimension(540, 260));
            JOptionPane.showMessageDialog(frame, sp, "Détails de la demande", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Sélectionne une demande dans la liste.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    });

    btnOuvrir.addActionListener((ActionEvent e) -> {
        try { Desktop.getDesktop().browse(new URI(DEFAULT_FILE_URL)); }
        catch (Exception ex) { JOptionPane.showMessageDialog(frame, "Impossible d'ouvrir le fichier.\nChemin : " + DEFAULT_FILE_URL, "Erreur", JOptionPane.ERROR_MESSAGE); }
    });

    btnSupprimer.addActionListener((ActionEvent e) -> {
        int sel = jlist.getSelectedIndex();
        if (sel >= 0) {
            String code = JOptionPane.showInputDialog(frame, "Entrez le code admin pour supprimer :");
            if (code != null && code.equals("1234")) { storage.remove(sel); listModel.remove(sel); JOptionPane.showMessageDialog(frame, "✅ Demande supprimée."); }
            else { JOptionPane.showMessageDialog(frame, "Code incorrect.", "Erreur", JOptionPane.WARNING_MESSAGE); }
        } else { JOptionPane.showMessageDialog(frame, "Sélectionne une demande à supprimer.", "Info", JOptionPane.INFORMATION_MESSAGE); }
    });

    btnQuitter.addActionListener((ActionEvent e) -> {
        int conf = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) System.exit(0);
    });

    frame.setVisible(true);
}

private JButton styledButton(String text, Color bg) {
    JButton b = new JButton(text); b.setBackground(bg); b.setForeground(Color.WHITE);
    b.setFocusPainted(false); b.setFont(new Font("Segoe UI", Font.BOLD, 14));
    b.setPreferredSize(new Dimension(160, 44));
    return b;
}

private static class CardRenderer extends JPanel implements ListCellRenderer<String> {
    private JLabel label;
    CardRenderer() { setLayout(new BorderLayout()); setOpaque(true); label = new JLabel(); label.setBorder(new EmptyBorder(6, 12, 6, 12)); label.setFont(new Font("Segoe UI", Font.PLAIN, 14)); add(label, BorderLayout.CENTER); }
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        label.setText(value);
        if (isSelected) { setBackground(new Color(204, 228, 255)); label.setForeground(new Color(10, 36, 99)); setBorder(new LineBorder(new Color(10, 36, 99), 2, true)); }
        else { setBackground(new Color(245, 247, 250)); label.setForeground(new Color(30, 30, 30)); setBorder(new LineBorder(new Color(230, 230, 230), 1, true)); }
        return this;
    }
}

public static void main(String[] args) { SwingUtilities.invokeLater(AppGUI::new); }

}

