
package Swing;

import Main.Settings;
import Swing.Components.PanelCreate;
import Swing.Components.PanelEdit;
import Swing.Components.PanelFormula;
import Swing.Components.PanelHeader;
import Swing.Components.PanelMain;
import Swing.Components.PanelMenu;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Brugerdefineret JFrame med variabler og funktioner
 * @author Karsten F. Pedersen, Proc-20A
 */
public final class Frame extends JFrame {
    
    // Variabler
    private int visualThemeIndex;
    private int currentPanelIndex;
    private final Panel[] panels;
    private PanelHeader headPanel;
    private PanelMenu menuPanel;
    private JPanel contentPanel, pagePanel;
    private CardLayout pageCardLayout;
    
    // Enum som anvendes til at faa sidernes index
    // Det er i stedet for at skrive 0 til 3 i 'setPanel' metoden
    public enum PANELS {
        Main,    // 0
        Create,  // 1
        Formula, // 2
        Edit     // 3
    }
    
    // Konstruktoere
    /**
     * Opsaetter basale variabler
     */
    public Frame() {
        // Saet farvetema fra 'Settings.java'
        visualThemeIndex = Settings.VISUAL_THEME;
        
        // Opsaet siderne
        currentPanelIndex = 0;
        panels = new Panel[] {
            new PanelMain(this, new VisualTheme(visualThemeIndex)),
            new PanelCreate(this, new VisualTheme(visualThemeIndex)),
            new PanelFormula(this, new VisualTheme(visualThemeIndex)),
            new PanelEdit(this, new VisualTheme(visualThemeIndex)),
        };
        
        // Opsaet elementer i framen
        setup();
    }
    
    // Metoder
    /**
     * Opsaetter siden (layout og elementer)
     * Kaldes i konstruktoeren
     */
    public void setup() {
        // Saet JFramens layout manager
        this.setLayout(new BorderLayout());
        
        // Saet contentPanel (indholds panelet)
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        this.add(contentPanel, BorderLayout.CENTER);
        
        // Saet pagePanel (side panelet)
        pagePanel = new JPanel();
        pageCardLayout = new CardLayout();
        pagePanel.setLayout(pageCardLayout);
        contentPanel.add(pagePanel, BorderLayout.CENTER);
        
        // Saet headPanel (hoved panelet som vises i toppen)
        headPanel = new PanelHeader(this, new VisualTheme(visualThemeIndex));
        this.add(headPanel, BorderLayout.NORTH);
        
        // Saet menuPanel (side panelet)
        menuPanel = new PanelMenu(this, new VisualTheme(visualThemeIndex));
        contentPanel.add(menuPanel, BorderLayout.WEST);
        
        // Put side panelerne ind i pagePanel
        for (int i = 0; i < panels.length; i++) {
            pagePanel.add(panels[i], Integer.toString(i));
        }
        
        // Bestem hvilken side der skal vises foerst
        pageCardLayout.show(pagePanel, Integer.toString(currentPanelIndex));
        
        // Saet JFramens indstillinger
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);             // Soerger for at programmet lukker naar vinduets roede kryds klikkes
        this.setTitle(Settings.PROJECT_NAME);                     // Vindue titel
        this.setSize(1920 / 2, 1080 / 2);                         // Saetter vinduets stoerelse
        this.setPreferredSize(new Dimension(1920 / 2, 1080 / 2)); // Saetter vinduets bedste stoerelse
        this.setMinimumSize(new Dimension(1920 / 2, 1080 / 2));   // Saetter vinduets mindste stoerelse
        this.setLocationRelativeTo(null);                         // Placer vinduet i midten af skaermen
        this.pack();                                              // Pak sidens indhold sammen
        this.setVisible(true);                                    // Goer programmet synligt
    }
    
    // Set metoder
    /**
     * Setter JFramets panel
     * @param newIndex Det nye panels index
     * @return Det nye panel
     */
    public Panel setPanel(int newIndex) {
        // Aendre panelet som vises
        currentPanelIndex = newIndex;
        panels[currentPanelIndex].reset(); // Koere reset metoden i Panel siderne
        pageCardLayout.show(pagePanel, Integer.toString(currentPanelIndex));
        
        // Opdater sidemenu panel
        getMenuPanel().updateContent();
        
        // Returner det nye panel
        // Goer saa man kan kalde metoder i det nye panel fra stedet man kaldte 'setPanel' metoden
        return panels[currentPanelIndex];
    }
    
    /**
     * Denne metode saetter programmets farvetema
     * @param newVisualThemeIndex det nye farvetemas index
     */
    public void setVisualTheme(int newVisualThemeIndex) {
        // Tjek om det nye tema er det samme
        if (newVisualThemeIndex != visualThemeIndex) {
            // Saet tema
            visualThemeIndex = newVisualThemeIndex;
            
            // Saet farvetemaet i headPanel (hoved panel) og menuPanel (side panel)
            headPanel.setVisualTheme(new VisualTheme(visualThemeIndex));
            menuPanel.setVisualTheme(new VisualTheme(visualThemeIndex));
            
            // Saet farvetemaet i hver enkel side i panels arrayet
            for (Panel panel : panels) {
                panel.setVisualTheme(new VisualTheme(visualThemeIndex));
            }
        }
    }
    
    // Get metoder
    public PanelMenu getMenuPanel() {
        return menuPanel;
    }
    
    public int getPanelIndex() {
        return currentPanelIndex;
    }
    
    public Panel getPanel() {
        return panels[currentPanelIndex];
    }
    
    public Panel getPanel(int panelIndex) {
        return panels[panelIndex];
    }
    
}