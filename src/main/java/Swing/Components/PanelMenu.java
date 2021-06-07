
package Swing.Components;

import Swing.Frame;
import Swing.Panel;
import Main.Settings;
import Swing.VisualTheme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Side panel med navigations menu
 * @author Karsten F. Pedersen, Proc-20A
 */
public final class PanelMenu extends Panel implements ActionListener {
    
    // Variabler
    private JPanel topPanel, centerPanel, bottomPanel;
    private JLabel versionLabel;
    private JButton topButton;
    
    // Konstruktoer
    /**
     * Panel konstruktoer
     * @param frame Saetter hvilken frame den er paa
     * @param visualTheme Saetter farvetemaet
     */
    public PanelMenu(Frame frame, VisualTheme visualTheme) {
        this.frame = frame;
        this.visualTheme = visualTheme;
        setup();
        applyVisualTheme();
        
        // Opdatere panelet
        updateContent();
    }

    @Override
    public void setup() {
        // Saetter panelet
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(16, 16, 16, 16));
        this.setPreferredSize(new Dimension(SIDE_WIDTH, BODY_HEIGHT));
        
        // Saetter topPanel
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(SIDE_WIDTH, 150));
        this.add(topPanel, BorderLayout.NORTH);
        // Saetter topButton som skal bruges til at gaa mellem siderne
        topButton = new JButton();
        topButton.setText("");
        topButton.addActionListener(this);
        topButton.setPreferredSize(new Dimension(200, 75));
        topPanel.add(topButton, BorderLayout.NORTH);
        // Saetter centerPanel
        centerPanel = new JPanel();
        this.add(centerPanel, BorderLayout.CENTER);
        // Saetter bottomPanel
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(SIDE_WIDTH, 50));
        this.add(bottomPanel, BorderLayout.SOUTH);
        // Saetter label som skal vise programmets version
        versionLabel = new JLabel();
        versionLabel.setText("Version " + Settings.VERSION);
        bottomPanel.add(versionLabel, BorderLayout.SOUTH);
    }

    @Override
    public void applyVisualTheme() {
        // Baggrundsfarver
        this.setBackground(visualTheme.SURFACE_COLOR);
        topPanel.setBackground(visualTheme.SURFACE_COLOR);
        centerPanel.setBackground(visualTheme.SURFACE_COLOR);
        bottomPanel.setBackground(visualTheme.SURFACE_COLOR);
        
        // Forgrundsfarver
        versionLabel.setForeground(visualTheme.ON_SURFACE_VARIANT_COLOR);
        
        // Skrifttyper
        topButton.setFont(visualTheme.HEADING2_FONT);
        versionLabel.setFont(visualTheme.NORMAL_ITALIC_FONT);
    }
    
    @Override
    public void reset() {
        // Opdatere panelet
        updateContent();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == topButton) {
            switch (frame.getPanelIndex()) {
                case 0 -> { // 0 = Main
                    // Gaa til opretningssiden
                    frame.setPanel(Frame.PANELS.Create.ordinal());
                    
                    // Opdater panelet
                    updateContent();
                }
                case 3 -> { // 3 = Edit
                    // Saeet panel til formulasiden og faa det tilbage i panel variablet
                    PanelFormula panel = (PanelFormula) frame.setPanel(Frame.PANELS.Formula.ordinal());
                    PanelEdit lastPanel = (PanelEdit) frame.getPanel(Frame.PANELS.Main.ordinal());
                    
                    // Set formularen som skal vises paa siden
                    panel.setFormulaIndex(lastPanel.getFormulaIndex());
                    
                    // Opdater panelet
                    updateContent();
                }
                default -> {
                    // Saet panelet til forsiden
                    frame.setPanel(Frame.PANELS.Main.ordinal());
                    
                    // Opdater panelet
                    updateContent();
                }
            }
        }
    }
    
    // Metoder
    /**
     * Opdatere panelets indhold
     * Bruges til at aendre teksten i "topButton"
     */
    public void updateContent() {
        // Bruger switch til at goere forskellige ting paa baggrund af hvilken side man er paa
        switch (frame.getPanelIndex()) {
            case 0 -> {
                // Saet teksten paa forsiden
                topButton.setText("Opret formel");
            }
            default -> {
                // Saet teksten paa alle andre sider
                topButton.setText("Tilbage");
            }
        }
    }

    // Get metoder
    public JPanel getTopPanel() {
        return topPanel;
    }
    
    public JPanel getCenterPanel() {
        return centerPanel;
    }
    
    public JPanel getBottomPanel() {
        return bottomPanel;
    }
    
}