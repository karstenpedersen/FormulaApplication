
package Swing.Components;

import Swing.Frame;
import Swing.Panel;
import Main.Settings;
import Swing.VisualTheme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * Hoved panelet
 * @author Karsten F. Pedersen, Proc-20A
 */
public final class PanelHeader extends Panel implements ActionListener {
    
    // Variabler
    private JLabel titleLabel;
    private JComboBox themeComboBox;
    
    // Konstruktoer
    /**
     * Panel konstruktoer
     * @param frame Saetter hvilken frame den er paa
     * @param visualTheme Saetter farvetemaet
     */
    public PanelHeader(Frame frame, VisualTheme visualTheme) {
        this.frame = frame;
        this.visualTheme = visualTheme;
        setup();
        applyVisualTheme();
    }

    @Override
    public void setup() {
        // Saetter panelet
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(8, 16, 8, 16));
        this.setPreferredSize(new Dimension(WIDTH, HEAD_HEIGHT));
        
        // Saetter programmets titel
        titleLabel = new JLabel();
        titleLabel.setText(Settings.PROJECT_NAME);
        this.add(titleLabel, BorderLayout.LINE_START); // Soerger for at det er til venstre i hovedpanelet
        
        // Saetter programmets farvetema JComboBox themeComboBox
        themeComboBox = new JComboBox(Settings.VISUAL_THEMES);
        themeComboBox.setSelectedIndex(0);
        themeComboBox.addActionListener(this);
        this.add(themeComboBox, BorderLayout.LINE_END); // Soerger for at det er til hoejre i hovedpanelet
    }

    @Override
    public void applyVisualTheme() {
        // Baggrundsfarve
        this.setBackground(visualTheme.SURFACE_HEAD_COLOR);
        
        // Forgrundsfarve
        titleLabel.setForeground(visualTheme.ON_SURFACE_HEAD_COLOR);
        
        // Skrifttyper
        titleLabel.setFont(visualTheme.TITLE_FONT);
        themeComboBox.setFont(visualTheme.NORMAL_FONT);
    }
    
    @Override
    public void reset() {
        // Ikke anvendt af denne klasse
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == themeComboBox) {
            
            if (frame.getPanelIndex() == 0) {
                //PanelMain panel = (PanelMain) frame.getPanel();
                //panel.
            }
                
            // Aendre farvetema
            frame.setVisualTheme(themeComboBox.getSelectedIndex());
        }
    }
    
}