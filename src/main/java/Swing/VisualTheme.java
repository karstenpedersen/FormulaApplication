
package Swing;

import Main.Settings;
import java.awt.Color;
import java.awt.Font;

/**
 * Indeholder farvetema variabler
 * @author Karsten F. Pedersen, Proc-20A
 */
public final class VisualTheme {
    
    // Tema farver
    public Color PRIMARY_COLOR;
    public Color SECONDARY_COLOR;
    public Color BACKGROUND_COLOR;
    public Color SURFACE_COLOR;
    public Color SURFACE_HEAD_COLOR;
    public Color ERROR_COLOR;
    public Color ON_PRIMARY_COLOR;
    public Color ON_SECONDARY_COLOR;
    public Color ON_BACKGROUND_COLOR;
    public Color ON_SURFACE_COLOR;
    public Color ON_SURFACE_VARIANT_COLOR;
    public Color ON_SURFACE_HEAD_COLOR;
    public Color ON_ERROR_COLOR;
    public Color ON_DEFAULT_COLOR;
    
    // Tema skrifttyper
    public Font TITLE_FONT;
    public Font HEADING1_FONT;
    public Font HEADING2_FONT;
    public Font HEADING3_FONT;
    public Font NORMAL_FONT;
    public Font NORMAL_ITALIC_FONT;
    public Font SMALL_FONT;
    
    // Konstruktoer
    /**
     * Farve tema objekts basic konstruktoer
     */
    public VisualTheme() {
        setVisualTheme(Settings.VISUAL_THEME);
    }
    
    /**
     * Farve tema objekt konstruktoer
     * @param themeIndex Farvetemaets index
     */
    public VisualTheme(int themeIndex) {
        setVisualTheme(themeIndex);
    }
    
    // Metoder
    /**
     * Saetter objektets farvetema
     * @param themeIndex Farvetemaets index
     */
    public void setVisualTheme(int themeIndex) {
        // Bruger switch til at skifte mellem farvetemaer
        switch (themeIndex) {
            case 1 -> { // Moerkt tema
                PRIMARY_COLOR = new Color(98, 0, 238);
                SECONDARY_COLOR = new Color(3, 218, 198);
                BACKGROUND_COLOR = new Color(18, 18, 18);
                SURFACE_COLOR = new Color(75, 75, 75);
                SURFACE_HEAD_COLOR = new Color(36, 36, 36);
                ERROR_COLOR = new Color(176 , 0, 32);

                ON_PRIMARY_COLOR = new Color(18, 18, 18);
                ON_SECONDARY_COLOR = new Color(18, 18, 18);
                ON_BACKGROUND_COLOR = new Color(255, 255, 255);
                ON_SURFACE_COLOR = new Color(255, 255, 255);
                ON_SURFACE_VARIANT_COLOR = new Color(85, 85, 85);
                ON_SURFACE_HEAD_COLOR = new Color(255, 255, 255);
                ON_ERROR_COLOR = new Color(255, 255, 255);
                ON_DEFAULT_COLOR = new Color(0, 0, 0);
            } 
            default -> { // Lyst tema
                PRIMARY_COLOR = new Color(98, 0, 238);
                SECONDARY_COLOR = new Color(3, 218, 198);
                BACKGROUND_COLOR = new Color(223, 230, 233);
                SURFACE_COLOR = new Color(178, 190, 195);
                SURFACE_HEAD_COLOR = new Color(99, 110, 114);
                ERROR_COLOR = new Color(176, 0, 32);

                ON_PRIMARY_COLOR = new Color(255, 255, 255);
                ON_SECONDARY_COLOR = new Color(0, 0, 0);
                ON_BACKGROUND_COLOR = new Color(0, 0, 0);
                ON_SURFACE_COLOR = new Color(0, 0, 0);
                ON_SURFACE_VARIANT_COLOR = new Color(0, 0, 0);
                ON_SURFACE_HEAD_COLOR = new Color(0, 0, 0);
                ON_ERROR_COLOR = new Color(255, 255, 255);
                ON_DEFAULT_COLOR = new Color(0, 0, 0);
            }
        }
        
        // Saetter skrifttyper
        String font = Font.SANS_SERIF;
        TITLE_FONT = new Font(font, Font.BOLD, 24);
        HEADING1_FONT = new Font(font, Font.BOLD, 24);
        HEADING2_FONT = new Font(font, Font.BOLD, 16);
        HEADING3_FONT = new Font(font, Font.BOLD, 12);
        NORMAL_FONT = new Font(font, Font.PLAIN, 12);
        NORMAL_ITALIC_FONT = new Font(font, Font.ITALIC, 12);
        SMALL_FONT = new Font(font, Font.PLAIN, 8);
    }
    
}