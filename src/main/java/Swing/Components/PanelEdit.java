package Swing.Components;

import More.Formula;
import More.DatabaseHandler;
import More.Methods;
import Swing.Frame;
import Swing.Panel;
import Swing.VisualTheme;
import More.StringHandler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Formel redigerings side
 * @author Karsten F. Pedersen, Proc-20A
 */
public final class PanelEdit extends Panel implements ActionListener {

    // Variabler
    private int formulaIndex;
    private Formula formula;
    private JPanel mainPanel, centerPanel, bottomPanel;
    private JButton saveButton;
    private JTextField nameTextField, tagTextField, formulaTextField, symbolTextField;
    private JTextArea descTextArea;
    private JLabel titleLabel, nameLabel, descLabel, tagLabel, formulaLabel, symbolLabel;

    // Konstruktoer
    /**
     * Panel konstruktoer
     * @param frame Saetter hvilken frame den er paa
     * @param visualTheme Saetter farvetemaet
     */
    public PanelEdit(Frame frame, VisualTheme visualTheme) {
        this.frame = frame;
        this.visualTheme = visualTheme;
        setup();
        applyVisualTheme();
    }

    @Override
    public void setup() {
        // Saetter panelet
        this.setSize(WIDTH, BODY_HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, BODY_HEIGHT));
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Saetter mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        this.add(mainPanel, BorderLayout.CENTER);
        
        // Saetter titlen
        titleLabel = new JLabel();
        titleLabel.setText("Rediger formel");
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Saetter centerPanel
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1, 0, 16));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Saetter navn titlen
        nameLabel = new JLabel();
        nameLabel.setText("Navn");
        centerPanel.add(nameLabel);
        // Saetter navn inputfeltet
        nameTextField = new JTextField(20);
        centerPanel.add(nameTextField);

        // Saetter beskrivelse titlen
        descLabel = new JLabel();
        descLabel.setText("Beskrivelse");
        centerPanel.add(descLabel);
        // Saetter beskrivelse inputfeltet
        descTextArea = new JTextArea(5, 1);
        descTextArea.setLineWrap(true);
        centerPanel.add(descTextArea);

        // Saetter soegeord titlen
        tagLabel = new JLabel();
        tagLabel.setText("Søge ord");
        centerPanel.add(tagLabel);
        // Saetter soegeord inputfeltet
        tagTextField = new JTextField(20);
        centerPanel.add(tagTextField);

        // Saetter formel titlen
        formulaLabel = new JLabel();
        formulaLabel.setText("Formel");
        centerPanel.add(formulaLabel);
        // Saetter formel inputfeltet
        formulaTextField = new JTextField(20);
        centerPanel.add(formulaTextField);

        // Saetter symbol titlen
        symbolLabel = new JLabel();
        symbolLabel.setText("Symboler");
        centerPanel.add(symbolLabel);
        // Saetter symbol inputfeltet
        symbolTextField = new JTextField(20);
        centerPanel.add(symbolTextField);

        // Saetter bottomPanel
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Saetter saveButton i bottomPanel
        saveButton = new JButton();
        saveButton.setText("Gem formel");
        saveButton.addActionListener(this);
        bottomPanel.add(saveButton);
    }

    @Override
    public void applyVisualTheme() {
        // Baggrundsfarver
        this.setBackground(visualTheme.BACKGROUND_COLOR);
        mainPanel.setBackground(visualTheme.SURFACE_COLOR);
        centerPanel.setBackground(visualTheme.SURFACE_COLOR);
        bottomPanel.setBackground(visualTheme.SURFACE_COLOR);
        
        // Forgrundsfarver
        titleLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        titleLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        nameLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        descLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        tagLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        formulaLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        symbolLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        
        // Skrifttyper
        titleLabel.setFont(visualTheme.HEADING1_FONT);
        nameLabel.setFont(visualTheme.HEADING3_FONT);
        descLabel.setFont(visualTheme.HEADING3_FONT);
        tagLabel.setFont(visualTheme.HEADING3_FONT);
        formulaLabel.setFont(visualTheme.HEADING3_FONT);
        symbolLabel.setFont(visualTheme.HEADING3_FONT);
        nameTextField.setFont(visualTheme.NORMAL_FONT);
        descTextArea.setFont(visualTheme.NORMAL_FONT);
        tagTextField.setFont(visualTheme.NORMAL_FONT);
        formulaTextField.setFont(visualTheme.NORMAL_FONT);
        symbolTextField.setFont(visualTheme.NORMAL_FONT);
        saveButton.setFont(visualTheme.HEADING2_FONT);
    }

    @Override
    public void reset() {
        // Set teksten i inputfelterne til ingenting
        nameTextField.setText("");
        descTextArea.setText("");
        tagTextField.setText("");
        formulaTextField.setText("");
        symbolTextField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) { // Gem formel
            // Faa brugerens input i tekstfelterne
            String inputName = nameTextField.getText();
            String inputDesc = descTextArea.getText();
            String inputTags = tagTextField.getText();
            String inputFormula = formulaTextField.getText();
            String inputSymbols = symbolTextField.getText();
            
            // Konverter brugerens input vha. StringHandler klassen
            String formulaName = inputName;
            String formulaDesc = inputDesc;
            String[] formulaTags = StringHandler.splitText(inputTags);
            String formulaFormula = StringHandler.removeCharacter(inputFormula, ' ', true);
            String[] formulaSymbols = StringHandler.splitText(inputSymbols);
            
            if (!"".equals(formulaName) && !"".equals(formulaFormula)) {
                // Faa gemte formler
                JSONArray jsonArray = DatabaseHandler.jsonLoad(DatabaseHandler.formulaPath);

                // Opret formula objekt
                JSONObject jsonObject = DatabaseHandler.formulaToJson(new Formula(jsonArray.size(), formulaName, formulaDesc, formulaTags, formulaFormula, formulaSymbols));

                // Gem jsonObject i json array paa formlens plads
                jsonArray.set(formulaIndex, jsonObject);
                
                // Put jsonArray i et andet jsonObject
                JSONObject jsonContainer = new JSONObject();
                jsonContainer.put("formulas", jsonArray);

                // Gem dette jsonObject
                DatabaseHandler.jsonSave(jsonContainer, DatabaseHandler.formulaPath);

                // Saeet panel til formel side og faa det tilbage i panel variablet
                PanelFormula panel = (PanelFormula) frame.setPanel(Frame.PANELS.Formula.ordinal());

                // Set formularen som skal vises paa siden
                panel.setFormulaIndex(formulaIndex);
                
                //frame.setPanel(Frame.PANELS.Main.ordinal());
            } else {
                // Forkert input eller mangler input - Smid fejl til brugeren
                JOptionPane.showMessageDialog(null, "Forkert/Mangler input!", "Fejl", JOptionPane.OK_OPTION);
            }
        }
    }
    
    // Set metoder
    public void setFormulaIndex(int formulaIndex) {
        // Saet den viste formula
        this.formulaIndex = formulaIndex;
        
        // Faa formularens info
        this.formula = Methods.setFormula(this.formulaIndex);
        
        // Put formularens info ind i tekstfelterne
        nameTextField.setText(formula.getName());
        descTextArea.setText(formula.getDescription());
        formulaTextField.setText(formula.getFormula());
        
        // Lav soegeordene om til en tekststreng og put det ind i tekstfeltet
        String tagString = "";
        for (int i = 0; i < formula.getTags().length; i++) {
            tagString += formula.getTags()[i];
            if (i < formula.getTags().length - 1) {
                tagString += ", ";
            }
        }
        tagTextField.setText(tagString);
        
        
        // Lav symbolerne om til en tekststreng og put det ind i tekstfeltet
        String symbolString = "";
        for (int i = 0; i < formula.getSymbols().length; i++) {
            symbolString += formula.getSymbols()[i];
            if (i < formula.getSymbols().length - 1) {
                symbolString += ", ";
            }
        }
        symbolTextField.setText(symbolString);
    }
    
    // Get metoder
    public int getFormulaIndex() {
        return formulaIndex;
    }

}
