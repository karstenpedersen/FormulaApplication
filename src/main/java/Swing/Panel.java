
package Swing;

import javax.swing.JPanel;

/**
 * Brugerdefineret JPanel med variabler og metoder
 * @author Karsten F. Pedersen, Proc-20A
 */
public abstract class Panel extends JPanel {
    
    // Variabler
    public Frame frame;
    public VisualTheme visualTheme;
    
    final public int PAGE_WIDTH_MIN = 480;
    final public int PAGE_HEIGHT_MIN = 270;
    final public int PAGE_WIDTH = 1920;
    final public int PAGE_HEIGHT = 1080;
    final public int HEAD_HEIGHT = 50;
    final public int BODY_HEIGHT = HEIGHT - HEAD_HEIGHT;
    final public int SIDE_WIDTH = 200;
    
    // Metoder
    /**
     * Saetter panelets farvetema
     * @param visualTheme 
     */
    public void setVisualTheme(VisualTheme visualTheme) {
        this.visualTheme = visualTheme;
        applyVisualTheme();
    }
    
    // Abstrakte metoder
    // Goer saa hver underklasse skal have disse metoder
    public abstract void setup();
    public abstract void reset();
    public abstract void applyVisualTheme();

}
