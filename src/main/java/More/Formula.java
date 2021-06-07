
package More;

/**
 * Formel objekt anvendes til at holde information om en formel
 * @author Karsten F. Pedersen, Proc-20A
 */
public class Formula {
    
    // Variabler
    private final int id;
    private final String name;
    private final String description;
    private final String[] tags;
    private final String formula;
    private final String[] symbols;

    // Konstruktoer
    /**
     * Formel konstruktoer
     * @param id Formelens ID
     * @param name Formelens navn
     * @param description Formelens beskrivelse
     * @param tags Formelens soegeord
     * @param formula Formelens formel
     * @param symbols Formelens formel symboler
     */
    public Formula(int id, String name, String description, String[] tags, String formula, String[] symbols) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.formula = formula;
        this.symbols = symbols;
    }
    
    // Get metoder
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String[] getTags() {
        return tags;
    }
    
    public String getFormula() {
        return formula;
    }
    
    public String[] getSymbols() {
        return symbols;
    }
    
}