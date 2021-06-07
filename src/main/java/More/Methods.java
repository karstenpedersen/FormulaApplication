
package More;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Denne klasse indeholder metoder som anvendes flere steder
 * @author Karsten F. Pedersen, Proc-20A
 */
public class Methods {
    
    /**
     * Denne metoder finder en formel i databasen
     * @param formulaIndex
     * @return formel
     */
    public static Formula setFormula(int formulaIndex) {
        // Faa formularen fra databasen
        JSONArray jsonArray = DatabaseHandler.jsonLoad(DatabaseHandler.formulaPath);
        JSONObject jsonObject = (JSONObject) jsonArray.get(formulaIndex);
        
        // Udpak JSONObjektet i forskellige variabler
        String formulaName = (String) jsonObject.get("name");
        String formulaDesc = (String) jsonObject.get("description");
        String formulaFormula = (String) jsonObject.get("formula");
        
        // Faa soegeord
        JSONArray tagJsonArray = (JSONArray) jsonObject.get("tags");
        String[] formulaTags = new String[tagJsonArray.size()];
        for (int i = 0; i < tagJsonArray.size(); i++) {
            formulaTags[i] = tagJsonArray.get(i).toString();
        }

        // Faa symboler
        JSONArray symbolJsonArray = (JSONArray) jsonObject.get("symbols");
        String[] formulaSymbols = new String[symbolJsonArray.size()];
        for (int i = 0; i < symbolJsonArray.size(); i++) {
            formulaSymbols[i] = symbolJsonArray.get(i).toString();
        }
        
        // Returnere formlen
        return new Formula(formulaIndex, formulaName, formulaDesc, formulaTags, formulaFormula, formulaSymbols);
    }
    
}