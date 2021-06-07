
package Swing.Components;

import More.Formula;
import More.DatabaseHandler;
import More.Methods;
import Swing.Frame;
import Swing.Panel;
import Swing.VisualTheme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Side hvor man ser formler
 * @author Karsten F. Pedersen, Proc-20A
 */
public final class PanelFormula extends Panel implements ActionListener {
    
    // Variabler
    private int formulaIndex;
    private Formula formula;
    private JPanel mainPanel, mainInnerPanel, infoPanel, infoHeadPanel, infoHeadRightPanel;
    private JButton editButton, deleteButton;
    private JLabel nameLabel, tagLabel, formulaLabel, symbolLabel;
    private JTextArea descTextArea;
    private JScrollPane mainScrollPane;
    
    // Konstruktoer
    /**
     * Panel konstruktoer
     * @param frame Saetter hvilken frame den er paa
     * @param visualTheme Saetter farvetemaet
     */
    public PanelFormula(Frame frame, VisualTheme visualTheme) {
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
        
        // Saetter mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        
        // Saetter mainInnerPanel
        mainInnerPanel = new JPanel();
        mainInnerPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        mainInnerPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(mainInnerPanel, BorderLayout.CENTER);
        
        // Saetter infoPanel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1));
        infoPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        mainInnerPanel.add(infoPanel);
        
        // Saetter infoHeadPanel
        infoHeadPanel = new JPanel();
        infoHeadPanel.setLayout(new BorderLayout());
        infoPanel.add(infoHeadPanel);
        // Saetter navne titlen i infoHeadPanel
        nameLabel = new JLabel("Name");
        infoHeadPanel.add(nameLabel, BorderLayout.CENTER);
        // Saetter infoHeadRightPanel
        infoHeadRightPanel = new JPanel();
        infoHeadRightPanel.setLayout(new BorderLayout());
        infoHeadPanel.add(infoHeadRightPanel, BorderLayout.LINE_END); // Soeger for at panelet puttes til hoejre i infoHeadPanel
        // Saetter editButton i infoHeadRightPanel
        editButton = new JButton();
        editButton.setText("Rediger");
        editButton.addActionListener(this);
        infoHeadRightPanel.add(editButton, BorderLayout.CENTER);
        // Saetter deleteButton i infoHeadRightPanel
        deleteButton = new JButton();
        deleteButton.setText("Slet");
        deleteButton.addActionListener(this);
        infoHeadRightPanel.add(deleteButton, BorderLayout.LINE_END);
        
        // Saetter beskrivelsen ind i infoPanel
        descTextArea = new JTextArea("Description");
        descTextArea.setEditable(false);
        descTextArea.setLineWrap(true);
        infoPanel.add(descTextArea);
        // Saetter soegeordene ind i infoPanel
        tagLabel = new JLabel("Tags");
        infoPanel.add(tagLabel);
        // Saetter formlen ind i infoPanel
        formulaLabel = new JLabel("Formula");
        infoPanel.add(formulaLabel);
        // Saetter symbolerne ind i infoPanel
        symbolLabel = new JLabel("Symbols");
        infoPanel.add(symbolLabel);
    }
    
    @Override
    public void applyVisualTheme() {
        // Baggrundsfarver
        this.setBackground(visualTheme.BACKGROUND_COLOR);
        mainPanel.setBackground(visualTheme.BACKGROUND_COLOR);
        mainInnerPanel.setBackground(visualTheme.BACKGROUND_COLOR);
        infoPanel.setBackground(visualTheme.SURFACE_COLOR);
        infoHeadPanel.setBackground(visualTheme.SURFACE_COLOR);
        descTextArea.setBackground(visualTheme.SURFACE_COLOR);
        
        // Forgrundsfarver
        nameLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        descTextArea.setForeground(visualTheme.ON_SURFACE_COLOR);
        tagLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        formulaLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        symbolLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
        
        // Skrifttyper
        nameLabel.setFont(visualTheme.HEADING1_FONT);
        descTextArea.setFont(visualTheme.NORMAL_FONT);
        tagLabel.setFont(visualTheme.NORMAL_FONT);
        formulaLabel.setFont(visualTheme.NORMAL_FONT);
        symbolLabel.setFont(visualTheme.NORMAL_FONT);
    }
    
    @Override
    public void reset() {
        // Set teksten i inputfelterne til ""
        nameLabel.setText("");
        descTextArea.setText("");
        tagLabel.setText("");
        formulaLabel.setText("");
        symbolLabel.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            // Saeet og faa panelet
            PanelEdit panel = (PanelEdit) frame.setPanel(Frame.PANELS.Edit.ordinal());
            
            // Set formularen som skal anvendes på naeste side
            panel.setFormulaIndex(formulaIndex);
        } else if (e.getSource() == deleteButton) {
            // Faa database formel array
            JSONArray jsonArray = DatabaseHandler.jsonLoad(DatabaseHandler.formulaPath);
            
            // Fjern formularen fra JSONArray
            jsonArray.remove(formulaIndex);
            
            // Put jsonArray i et andet jsonObject
            JSONObject jsonContainer = new JSONObject();
            jsonContainer.put("formulas", jsonArray);

            // Gem dette jsonObject
            DatabaseHandler.jsonSave(jsonContainer, DatabaseHandler.formulaPath);
            
            // Gaa tilbage til forsiden
            frame.setPanel(Frame.PANELS.Main.ordinal());
        }
    }
    
    // Set metoder
    public void setFormulaIndex(int formulaIndex) {
        // Saet den viste formula
        this.formulaIndex = formulaIndex;
        
        // Faa formularens info
        this.formula = Methods.setFormula(formulaIndex);
        
        // Indsaet formularens info paa siden
        nameLabel.setText(formula.getName());
        descTextArea.setText(formula.getDescription());
        tagLabel.setText(Arrays.toString(formula.getTags()));
        formulaLabel.setText(formula.getFormula());
        symbolLabel.setText(Arrays.toString(formula.getSymbols()));
    }
    
    // Get metoder
    public int getFormulaIndex() {
        return formulaIndex;
    }
    
}