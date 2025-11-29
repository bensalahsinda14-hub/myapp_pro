package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@DisplayName("Tests pour AppGUI - Cabinet Martin & Associés")
public class AppTest {

    private AppGUI appGUI;

    @BeforeEach
    public void setUp() {
        // Initialize GUI on EDT (Event Dispatch Thread)
        try {
            SwingUtilities.invokeAndWait(() -> {
                appGUI = new AppGUI();
            });
        } catch (Exception e) {
            fail("Failed to initialize AppGUI: " + e.getMessage());
        }
    }

    // ==================== Test Client Class ====================
    
    @Test
    @DisplayName("Test création d'un client")
    public void testClientCreation() {
        AppGUI.Client client = new AppGUI.Client(
            "Jean Dupont", 
            "Marie Martin", 
            "jean@email.com", 
            "12345678", 
            "Droit des Affaires", 
            "Description test"
        );
        
        assertNotNull(client);
        assertEquals("Jean Dupont", client.plaig);
        assertEquals("Marie Martin", client.defend);
        assertEquals("jean@email.com", client.email);
        assertEquals("12345678", client.cin);
        assertEquals("Droit des Affaires", client.service);
        assertEquals("Description test", client.description);
    }

    @Test
    @DisplayName("Test toString du client")
    public void testClientToString() {
        AppGUI.Client client = new AppGUI.Client(
            "Ahmed", 
            "Mohamed", 
            "test@test.com", 
            "11223344", 
            "Droit du Travail", 
            "Test"
        );
        
        String expected = "Ahmed vs Mohamed | Droit du Travail";
        assertEquals(expected, client.toString());
    }

    // ==================== Test GUI Components ====================

    @Test
    @DisplayName("Test initialisation du frame")
    public void testFrameInitialization() throws Exception {
        JFrame frame = getPrivateField(appGUI, "frame");
        
        assertNotNull(frame);
        assertEquals("⚖️ CABINET MARTIN & ASSOCIÉS - Gestion", frame.getTitle());
        assertEquals(960, frame.getWidth());
        assertEquals(780, frame.getHeight());
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
    }

    @Test
    @DisplayName("Test initialisation des champs de texte")
    public void testTextFieldsInitialization() throws Exception {
        JTextField tfPlaignant = getPrivateField(appGUI, "tfPlaignant");
        JTextField tfDefendeur = getPrivateField(appGUI, "tfDefendeur");
        JTextField tfEmail = getPrivateField(appGUI, "tfEmail");
        JTextField tfCIN = getPrivateField(appGUI, "tfCIN");
        
        assertNotNull(tfPlaignant);
        assertNotNull(tfDefendeur);
        assertNotNull(tfEmail);
        assertNotNull(tfCIN);
    }

    @Test
    @DisplayName("Test initialisation du ComboBox services")
    public void testComboBoxInitialization() throws Exception {
        JComboBox<String> cbService = getPrivateField(appGUI, "cbService");
        
        assertNotNull(cbService);
        assertEquals(6, cbService.getItemCount());
        assertEquals("Droit des Affaires", cbService.getItemAt(0));
        assertEquals("Contentieux", cbService.getItemAt(5));
    }

    @Test
    @DisplayName("Test initialisation de la liste")
    public void testListInitialization() throws Exception {
        DefaultListModel<String> listModel = getPrivateField(appGUI, "listModel");
        JList<String> jlist = getPrivateField(appGUI, "jlist");
        
        assertNotNull(listModel);
        assertNotNull(jlist);
        assertEquals(0, listModel.getSize());
    }

    // ==================== Test Actions ====================

    @Test
    @DisplayName("Test ajout d'un client valide")
    public void testHandleAjouterValid() throws Exception {
        // Set form values
        setTextFieldValue("tfPlaignant", "Test Plaignant");
        setTextFieldValue("tfDefendeur", "Test Defendeur");
        setTextFieldValue("tfEmail", "test@example.com");
        setTextFieldValue("tfCIN", "12345678");
        setTextAreaValue("taDescription", "Test description");
        
        // Get storage before
        List<AppGUI.Client> storage = getPrivateField(appGUI, "storage");
        int sizeBefore = storage.size();
        
        // Trigger handleAjouter
        invokePrivateMethod(appGUI, "handleAjouter");
        
        // Verify client was added
        assertEquals(sizeBefore + 1, storage.size());
        
        AppGUI.Client addedClient = storage.get(storage.size() - 1);
        assertEquals("Test Plaignant", addedClient.plaig);
        assertEquals("Test Defendeur", addedClient.defend);
        assertEquals("test@example.com", addedClient.email);
        assertEquals("12345678", addedClient.cin);
    }

    @Test
    @DisplayName("Test ajout avec champs vides")
    public void testHandleAjouterEmptyFields() throws Exception {
        // Leave fields empty
        setTextFieldValue("tfPlaignant", "");
        setTextFieldValue("tfDefendeur", "");
        setTextFieldValue("tfEmail", "");
        setTextFieldValue("tfCIN", "");
        
        List<AppGUI.Client> storage = getPrivateField(appGUI, "storage");
        int sizeBefore = storage.size();
        
        // Trigger handleAjouter
        invokePrivateMethod(appGUI, "handleAjouter");
        
        // Verify no client was added
        assertEquals(sizeBefore, storage.size());
    }

    @Test
    @DisplayName("Test ajout avec CIN invalide")
    public void testHandleAjouterInvalidCIN() throws Exception {
        setTextFieldValue("tfPlaignant", "Test");
        setTextFieldValue("tfDefendeur", "Test");
        setTextFieldValue("tfEmail", "test@test.com");
        setTextFieldValue("tfCIN", "123"); // Invalid: not 8 digits
        
        List<AppGUI.Client> storage = getPrivateField(appGUI, "storage");
        int sizeBefore = storage.size();
        
        invokePrivateMethod(appGUI, "handleAjouter");
        
        assertEquals(sizeBefore, storage.size());
    }

    @Test
    @DisplayName("Test ajout avec email invalide")
    public void testHandleAjouterInvalidEmail() throws Exception {
        setTextFieldValue("tfPlaignant", "Test");
        setTextFieldValue("tfDefendeur", "Test");
        setTextFieldValue("tfEmail", "invalid-email"); // Invalid format
        setTextFieldValue("tfCIN", "12345678");
        
        List<AppGUI.Client> storage = getPrivateField(appGUI, "storage");
        int sizeBefore = storage.size();
        
        invokePrivateMethod(appGUI, "handleAjouter");
        
        assertEquals(sizeBefore, storage.size());
    }

    @Test
    @DisplayName("Test styled button creation")
    public void testStyledButton() throws Exception {
        Method method = AppGUI.class.getDeclaredMethod("styledButton", String.class, Color.class);
        method.setAccessible(true);
        
        JButton button = (JButton) method.invoke(appGUI, "Test Button", Color.RED);
        
        assertNotNull(button);
        assertEquals("Test Button", button.getText());
        assertEquals(Color.RED, button.getBackground());
        assertEquals(Color.WHITE, button.getForeground());
        assertEquals(160, button.getPreferredSize().width);
        assertEquals(44, button.getPreferredSize().height);
    }

    // ==================== Test CardRenderer ====================

    @Test
    @DisplayName("Test CardRenderer initialization")
    public void testCardRendererCreation() {
        AppGUI.CardRenderer renderer = new AppGUI.CardRenderer();
        assertNotNull(renderer);
    }

    @Test
    @DisplayName("Test CardRenderer component rendering")
    public void testCardRendererGetListCellRendererComponent() {
        AppGUI.CardRenderer renderer = new AppGUI.CardRenderer();
        JList<String> list = new JList<>();
        
        Component comp = renderer.getListCellRendererComponent(
            list, 
            "Test Value", 
            0, 
            false, 
            false
        );
        
        assertNotNull(comp);
        assertTrue(comp instanceof JPanel);
    }

    @Test
    @DisplayName("Test CardRenderer selected state")
    public void testCardRendererSelected() {
        AppGUI.CardRenderer renderer = new AppGUI.CardRenderer();
        JList<String> list = new JList<>();
        
        Component comp = renderer.getListCellRendererComponent(
            list, 
            "Test Value", 
            0, 
            true, // selected
            false
        );
        
        assertEquals(new Color(204, 228, 255), comp.getBackground());
    }

    @Test
    @DisplayName("Test CardRenderer non-selected state")
    public void testCardRendererNotSelected() {
        AppGUI.CardRenderer renderer = new AppGUI.CardRenderer();
        JList<String> list = new JList<>();
        
        Component comp = renderer.getListCellRendererComponent(
            list, 
            "Test Value", 
            0, 
            false, // not selected
            false
        );
        
        assertEquals(new Color(245, 247, 250), comp.getBackground());
    }

    // ==================== Helper Methods ====================

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(obj);
    }

    private void setTextFieldValue(String fieldName, String value) throws Exception {
        JTextField field = getPrivateField(appGUI, fieldName);
        field.setText(value);
    }

    private void setTextAreaValue(String fieldName, String value) throws Exception {
        JTextArea field = getPrivateField(appGUI, fieldName);
        field.setText(value);
    }

    private void invokePrivateMethod(Object obj, String methodName) throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(obj);
    }
}
