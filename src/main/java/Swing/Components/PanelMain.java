
package Swing.Components;

import More.DatabaseHandler;
import Swing.Frame;
import Swing.Panel;
import Swing.VisualTheme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Forsiden af programmet
 * @author Karsten F. Pedersen, Proc-20A
 */
public final class PanelMain extends Panel implements ActionListener {
    
    // Variabler
    private JSONArray formulaJsonArray;
    List<Integer> formulaIndexArray;
    private JPanel mainPanel, searchPanel, dataPanel, dataInnerPanel;
    private JButton searchButton;
    private JTextField searchTextField;
    private JScrollPane dataScrollPane;
    
    // Konstruktoer
    /**
     * Panel konstruktoer
     * @param frame Saetter hvilken frame den er paa
     * @param visualTheme Saetter farvetemaet
     */
    public PanelMain(Frame frame, VisualTheme visualTheme) {
        this.frame = frame;
        this.visualTheme = visualTheme;
        this.formulaIndexArray = new ArrayList<>(); // Anvendes til at holde styr paa formlernes index
        
        // Hent databasen
        formulaJsonArray = DatabaseHandler.jsonLoad(DatabaseHandler.formulaPath);
        for (int i = 0; i < formulaJsonArray.size(); i++) {
            formulaIndexArray.add(i);
        }
        
        // Opseat siden
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
        mainPanel.setPreferredSize(new Dimension(SIDE_WIDTH, BODY_HEIGHT));
        this.add(mainPanel, BorderLayout.CENTER);
        
        // Saetter dataPanelet
        dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        mainPanel.add(dataPanel, BorderLayout.CENTER);
        // Saetter dataInnerPanel (formlerne vises i den)
        dataInnerPanel = new JPanel();
        dataInnerPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        dataPanel.add(dataInnerPanel, BorderLayout.CENTER);
        
        // Saetter searchPanel hvor soegefeltet skal vaere
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(new EmptyBorder(12, 12, 8, 8));
        mainPanel.add(searchPanel, BorderLayout.SOUTH);
        // Saetter searchButton
        searchButton = new JButton();
        searchButton.setText("Søg");
        searchButton.addActionListener(this);
        searchPanel.add(searchButton, BorderLayout.LINE_START); // Soerger for det er til venstre af searchPanel
        // Saetter searchTextField som er inputfeltet
        searchTextField = new JTextField();
        searchTextField.setBorder(null);
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        
        // Indlaeser formlerne
        fillContent(formulaJsonArray);
    }
    
    @Override
    public void applyVisualTheme() {
        // Baggrundsfarver
        mainPanel.setBackground(visualTheme.BACKGROUND_COLOR);
        dataPanel.setBackground(visualTheme.BACKGROUND_COLOR);
        dataInnerPanel.setBackground(visualTheme.BACKGROUND_COLOR);
        searchPanel.setBackground(visualTheme.SURFACE_HEAD_COLOR);

        // Forgrundsfarver
        searchTextField.setForeground(visualTheme.ON_DEFAULT_COLOR);
        
        // Skrifttyper
        searchButton.setFont(visualTheme.NORMAL_FONT);
        searchTextField.setFont(visualTheme.NORMAL_FONT);
        
        removeContent();
        fillContent(formulaJsonArray);
    }
    
    @Override
    public void reset() {
        // Reset soege inputfeltet
        searchTextField.setText("");
        
        // Fjern viste formulare hvis der er nogen
        removeContent();
        
        // Faa formler fra databasen
        formulaJsonArray = DatabaseHandler.jsonLoad(DatabaseHandler.formulaPath);
        formulaIndexArray.clear();
        for (int i = 0; i < formulaJsonArray.size(); i++) {
            formulaIndexArray.add(i);
        }
        
        // Set formlerne ind paa siden
        fillContent(formulaJsonArray);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // Fjern viste formulare hvis der er nogen
            removeContent();
            
            // Sorter i formlerne
            sortContent();
            
            // Set formlerne ind paa siden
            fillContent(formulaJsonArray);
        }
    }
    
    // Metoder
    public void sortContent() {
        // Faa brugerens soegeord fra inputfeltet
        String searchInput = searchTextField.getText().toLowerCase();

        // Sorter
        if (!"".equals(searchInput)) {
            // Faa alle formler fra databasen
            JSONArray jsonArray = DatabaseHandler.jsonLoad(DatabaseHandler.formulaPath);

            // Ryd de viste formler
            formulaJsonArray.clear();
            formulaIndexArray.clear();
            
            for (int i = 0; i < jsonArray.size(); i++) {
                // Faa formlens JSONObject
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                
                // Faa formlens navn
                String formulaName = (String) jsonObject.get("name");
                formulaName = formulaName.toLowerCase();
                
                // Faa formlens soegeord
                JSONArray jsonTagArray = (JSONArray) jsonObject.get("tags");
                String[] formulaTags = new String[jsonTagArray.size()];
                for (int j = 0; j < formulaTags.length; j++) {
                    formulaTags[j] = (String) jsonTagArray.get(j);
                    formulaTags[j] = formulaTags[j].toLowerCase();
                }
                
                if (formulaName.contains(searchInput)) { // Tjek om soegeordet er i navnet
                    formulaJsonArray.add(jsonObject);
                    formulaIndexArray.add(i);
                } else { // Tjek om soegeordet er i soegeordene hos formlen
                    for (String tag : formulaTags) {
                        if (tag.contains(searchInput)) {
                            formulaJsonArray.add(jsonObject);
                            formulaIndexArray.add(i);
                            break;
                        }
                    }
                }
            }
        } else {
            // Vis alle formlerne
            formulaJsonArray = DatabaseHandler.jsonLoad(DatabaseHandler.formulaPath);
            
            for (int i = 0; i < formulaJsonArray.size(); i++) {
                formulaIndexArray.add(i, i);
            }
        }
    }
    
    /**
     * Indsaetter formlerne pa siden
     * @param jsonArray De formler som skal vises
     */
    private void fillContent(JSONArray jsonArray) {
        
        // Seat dataInnerPanel's layout manager
        dataInnerPanel.setLayout(new GridLayout(jsonArray.size() + 20, 1, 0, 8));
        
        // Bruger for-loop til at koere over hver formel
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            
            // Faa formlens navn og dens formel
            String formulaName = (String) jsonObject.get("name");
            String formulaFormula = (String) jsonObject.get("formula");
            
            // Laver panel som skal saettes ind på siden med info om formlen
            JPanel elementPanel = new JPanel();
            elementPanel.setLayout(new BorderLayout());
            elementPanel.setBackground(visualTheme.SURFACE_COLOR);
            elementPanel.setBorder(new EmptyBorder(8, 16, 8, 16));
            elementPanel.setSize(new Dimension(dataInnerPanel.getWidth(), 32));
            // Opretter elementInfoPanel
            JPanel elementInfoPanel = new JPanel();
            elementInfoPanel.setLayout(new GridLayout(1, 2));
            elementInfoPanel.setBackground(visualTheme.SURFACE_COLOR);
            elementPanel.add(elementInfoPanel, BorderLayout.CENTER);
            // Opretter og putter titlen ind i elementInfoPanel
            JLabel titleLabel = new JLabel();
            titleLabel.setText(formulaName);
            titleLabel.setFont(visualTheme.HEADING3_FONT);
            titleLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
            elementInfoPanel.add(titleLabel);
            // Opretter og putter formlen ind i elementInfoPanel
            JLabel formulaLabel = new JLabel();
            formulaLabel.setText(formulaFormula);
            formulaLabel.setFont(visualTheme.NORMAL_FONT);
            formulaLabel.setForeground(visualTheme.ON_SURFACE_COLOR);
            elementInfoPanel.add(formulaLabel);
            // Opretter 'ButtonFormula.java' og putter den ind i elementPanel
            ButtonFormula formulaButton = new ButtonFormula(frame, formulaIndexArray.get(i), "Se mere");
            elementPanel.add(formulaButton, BorderLayout.LINE_END); // Soerger for den er til hoejre i elementPanel
            
            // Put elementPanel ind i dataInnerPanel hvor formlerne vises
            dataInnerPanel.add(elementPanel);
        }
        
        // Saetter dataScrollPane som bruges til at koere over formlerne
        dataScrollPane = new JScrollPane(dataInnerPanel);
        dataScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dataScrollPane.setBorder(null);
        dataPanel.add(dataScrollPane);
    }
    
    private void removeContent() {
        // Fjerner alle formlerne
        dataInnerPanel.removeAll();
        dataInnerPanel.revalidate();
        dataInnerPanel.repaint();
        
        // Fjerner scrollpane
        dataPanel.remove(dataScrollPane);
        dataPanel.revalidate();
        dataPanel.repaint();
    }

}