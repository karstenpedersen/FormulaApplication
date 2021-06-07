
package Swing.Components;

import Swing.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Brugerdefineret knap til formler
 * @author Karsten F. Pedersen, Proc-20A
 */
public class ButtonFormula extends JButton implements ActionListener {
    
    // Variabler
    private final Frame frame;
    private final int formulaIndex;
    
    // Konstruktoer
    /**
     * JButton lavet til formler
     * @param frame Saetter hvilken frame den er paa
     * @param formulaIndex Formel som den skal lede til
     * @param text Knappens tekst
     */
    public ButtonFormula(Frame frame, int formulaIndex, String text) {
        this.frame = frame;
        this.formulaIndex = formulaIndex;
        this.setText(text);
        
        // Lav en ActionListener til knappen
        this.addActionListener(this);
    }
    
    /**
     * Koeres naar knappen trykkes
     * @param e Information om aktionen
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Saeet panel og faa det tilbage i panel variablet
        PanelFormula panel = (PanelFormula) frame.setPanel(Frame.PANELS.Formula.ordinal());
        
        // Set formularen som skal vises paa siden
        panel.setFormulaIndex(formulaIndex);
    }
    
}