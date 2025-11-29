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

    // Constantes pour messages, police et code admin
    private static final String MSG_ERROR = "Erreur";
    private static final String MSG_INFO = "Info";
    private static final String FONT_DEFAULT = "Segoe UI";
    private static final String ADMIN_CODE = "1234";

    // Déclaration des boutons sur des lignes séparées
    private JButton btnAjouter;
    private JButton btnOuvrir;
    private JButton btnVoir;
    private JButton btnSupprimer;
    private JButton btnQuitter;

    static class Client {
        String plaig;
        String defend;
        String email;
        String cin;
        String service;
        String description;

        Client(String plaig, String defend, String email, String cin, String service, String description) {
            this.plaig = plaig;
            this.defend = defend;
            this.email = email;
            this.cin = cin;
            this.service = service;
            this.description = description;
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
        JPanel header = createHeader();
        frame.add(header, BorderLayout.NORTH);

        // ---------------- Center Panel ----------------
        JPanel center = createCenterPanel();
        frame.add(center, BorderLayout.CENTER);

        // ---------------- Footer Buttons ----------------
        JPanel buttons = createFooterButtons();
        frame.add(buttons, BorderLayout.PAGE_END);

        // ---------------- Actions ----------------
        initActions(frame, center);

        frame.setVisible(true);
    }

    // ---------------- Création Header ----------------
    private JPanel createHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 18));
        header.setBackground(new Color(10, 36, 99));
        JLabel icon = new JLabel("⚖️");
        icon.setForeground(Color.WHITE);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        JLabel title = new JLabel("CABINET MARTIN & ASSOCIÉS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font(FONT_DEFAULT, Font.BOLD, 32));
        header.add(icon);
        header.add(title);
        return header;
    }

    // ---------------- Création Center Panel ----------------
    private JPanel createCenterPanel() {
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(new EmptyBorder(12, 20, 12, 20));
        center.setBackground(new Color(245, 247, 250));

        // Formulaire
        JPanel formPanel = createFormPanel();
        center.add(formPanel, BorderLayout.NORTH);

        // Liste des demandes
        JPanel listPanel = createListPanel();
        center.add(listPanel, BorderLayout.CENTER);

        return center;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0, 51, 102), 2, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 6, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPlaignant = new JLabel("Plaignant:");
        JTextField tfPlaignant = new JTextField(18);
        JLabel lblDefendeur = new JLabel("Défendeur:");
        JTextField tfDefendeur = new JTextField(18);
        JLabel lblEmail = new JLabel("Email:");
        JTextField tfEmail = new JTextField(18);
        JLabel lblCIN = new JLabel("CIN:");
        JTextField tfCIN = new JTextField(10);
        JLabel lblService = new JLabel("Service:");
        String[] services = {"Droit des Affaires", "Droit du Travail", "Droit Immobilier", "Droit de la Famille", "Conseil Fiscal", "Contentieux"};
        JComboBox<String> cbService = new JComboBox<>(services);
        JLabel lblDescription = new JLabel("Description:");
        JTextArea taDescription = new JTextArea(4, 48);
        taDescription.setLineWrap(true);
        taDescription.setWrapStyleWord(true);
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

        return formPanel;
    }

    private JPanel createListPanel() {
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0, 51, 102), 2, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        JList<String> jlist = new JList<>(listModel);
        jlist.setFont(new Font(FONT_DEFAULT, Font.PLAIN, 14));
        jlist.setFixedCellHeight(36);
        jlist.setCellRenderer(new CardRenderer());

        JScrollPane listScroll = new JScrollPane(jlist);
        listScroll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        listScroll.setPreferredSize(new Dimension(880, 200));
        listPanel.add(listScroll, BorderLayout.CENTER);

        return listPanel;
    }

    private JPanel createFooterButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        buttons.setBackground(new Color(245, 247, 250));
        buttons.setBorder(new EmptyBorder(10, 0, 20, 0));

        btnAjouter = styledButton("➕ Ajouter", new Color(16, 185, 129));
        btnOuvrir = styledButton("🌐 Ouvrir Site", new Color(59, 130, 246));
        btnVoir = styledButton("👁️ Voir Détails", new Color(139, 92, 246));
        btnSupprimer = styledButton("🗑️ Supprimer", new Color(239, 68, 68));
        btnQuitter = styledButton("❌ Quitter", new Color(75, 85, 99));

        buttons.add(btnAjouter);
        buttons.add(btnOuvrir);
        buttons.add(btnVoir);
        buttons.add(btnSupprimer);
        buttons.add(btnQuitter);

        return buttons;
    }

    private void initActions(JFrame frame, JPanel center) {
        JList<String> jlist = (JList<String>) ((JScrollPane) ((JPanel) center.getComponent(1)).getComponent(0)).getViewport().getView();
        JTextField tfPlaignant = (JTextField) ((JPanel) ((JPanel) center.getComponent(0)).getComponent(1)).getComponent(1);
        JTextField tfDefendeur = (JTextField) ((JPanel) ((JPanel) center.getComponent(0)).getComponent(1)).getComponent(3);
        JTextField tfEmail = (JTextField) ((JPanel) ((JPanel) center.getComponent(0)).getComponent(1)).getComponent(5);
        JTextField tfCIN = (JTextField) ((JPanel) ((JPanel) center.getComponent(0)).getComponent(1)).getComponent(7);
        JComboBox<String> cbService = (JComboBox<String>) ((JPanel) ((JPanel) center.getComponent(0)).getComponent(1)).getComponent(9);
        JTextArea taDescription = (JTextArea) ((JScrollPane) ((JPanel) ((JPanel) center.getComponent(0)).getComponent(1)).getComponent(11)).getViewport().getView();

        btnAjouter.addActionListener(e -> handleAjouter(tfPlaignant, tfDefendeur, tfEmail, tfCIN, cbService, taDescription));
        btnVoir.addActionListener(e -> handleVoir(jlist, frame));
        btnOuvrir.addActionListener(e -> handleOuvrir(frame));
        btnSupprimer.addActionListener(e -> handleSupprimer(jlist, frame));
        btnQuitter.addActionListener(e -> handleQuitter(frame));
    }

    // ---------------- Handlers ----------------
    private void handleAjouter(JTextField tfPlaignant, JTextField tfDefendeur, JTextField tfEmail, JTextField tfCIN, JComboBox<String> cbService, JTextArea taDescription) {
        String plaig = tfPlaignant.getText().trim();
        String defend = tfDefendeur.getText().trim();
        String email = tfEmail.getText().trim();
        String cin = tfCIN.getText().trim();
        String service = (String) cbService.getSelectedItem();
        String desc = taDescription.getText().trim();

        if (plaig.isEmpty() || defend.isEmpty() || email.isEmpty() || cin.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tous les champs obligatoires doivent être remplis.", MSG_ERROR, JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!cin.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(null, "Le CIN doit contenir exactement 8 chiffres.", MSG_ERROR, JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(null, "Email invalide.", MSG_ERROR, JOptionPane.WARNING_MESSAGE);
            return;
        }

        Client client = new Client(plaig, defend, email, cin, service, desc);
        storage.add(client);
        listModel.addElement(client.toString());

        tfPlaignant.setText(""); tfDefendeur.setText(""); tfEmail.setText(""); tfCIN.setText(""); taDescription.setText("");
        JOptionPane.showMessageDialog(null, "✅ Demande ajoutée avec succès !");
    }

    private void handleVoir(JList<String> jlist, JFrame frame) {
        int sel = jlist.getSelectedIndex();
        if (sel >= 0) {
            Client c = storage.get(sel);
            String msg = "Plaignant : " + c.plaig + "\nDéfendeur : " + c.defend +
                    "\nEmail : " + c.email + "\nCIN : " + c.cin +
                    "\nService : " + c.service + "\n\nDescription :\n" + c.description;
            JTextArea ta = new JTextArea(msg);
            ta.setEditable(false); ta.setBackground(null); ta.setFont(new Font(FONT_DEFAULT, Font.PLAIN, 13));
            JScrollPane sp = new JScrollPane(ta);
            sp.setPreferredSize(new Dimension(540, 260));
            JOptionPane.showMessageDialog(frame, sp, "Détails de la demande", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Sélectionne une demande dans la liste.", MSG_INFO, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleOuvrir(JFrame frame) {
        try { Desktop.getDesktop().browse(new URI(DEFAULT_FILE_URL)); }
        catch (Exception ex) { JOptionPane.showMessageDialog(frame, "Impossible d'ouvrir le fichier.\nChemin : " + DEFAULT_FILE_URL, MSG_ERROR, JOptionPane.ERROR_MESSAGE); }
    }

    private void handleSupprimer(JList<String> jlist, JFrame frame) {
        int sel = jlist.getSelectedIndex();
        if (sel >= 0) {
            String code = JOptionPane.showInputDialog(frame, "Entrez le code admin pour supprimer :");
            if (ADMIN_CODE.equals(code)) {
                storage.remove(sel);
                listModel.remove(sel);
                JOptionPane.showMessageDialog(frame, "✅ Demande supprimée.");
            } else {
                JOptionPane.showMessageDialog(frame, "Code incorrect.", MSG_ERROR, JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Sélectionne une demande à supprimer.", MSG_INFO, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleQuitter(JFrame frame) {
        int conf = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) System.exit(0);
    }

    // ---------------- Styled Button ----------------
    private JButton styledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font(FONT_DEFAULT, Font.BOLD, 14));
        b.setPreferredSize(new Dimension(160, 44));
        return b;
    }

    // ---------------- Renderer ----------------
    private static class CardRenderer extends JPanel implements ListCellRenderer<String> {
        private JLabel label;
        CardRenderer() { setLayout(new BorderLayout()); setOpaque(true); label = new JLabel(); label.setBorder(new EmptyBorder(6, 12, 6, 12)); label.setFont(new Font(FONT_DEFAULT, Font.PLAIN, 14)); add(label, BorderLayout.CENTER); }
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
