
package More;

import Main.Settings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Denne klasse indeholder metoder til at gemme og hente data
 * @author Karsten F. Pedersen, Proc-20A
 */
public class DatabaseHandler {
    
    public static String formulaPath = Settings.FOLDER_PATH + "/" + Settings.FOLDER_NAME + "/" + Settings.FILE_NAME;
    
    /**
     * Gemmer json teksten i en fil
     * @param jsonObject Dataen som skal gemmes
     * @param path Stedet hvor filen skal gemmes
     */
    public static void jsonSave(JSONObject jsonObject, String path) {
        // Tjek om folderen eksistere
        File theDir = new File(Settings.FOLDER_PATH + "/" + Settings.FOLDER_NAME);
        if (!theDir.exists()){
            // Opret folderen
            theDir.mkdirs();
        }
        
        // Bruger try-catch til at haandtere fejl fra PrintWriter
        try (PrintWriter printWriter = new PrintWriter(path)) {
            // Put teksten ind i filen
            printWriter.println(jsonObject);
        } catch (Exception e) {
            // Der opstod en fejl, som blev fanget med catch i e
            System.out.println(e.getMessage());
        }
    }

    /**
     * Faa den gemte json tekstreng som en JSONArray
     * @param path Hvor filen ligger
     * @return JSONArray
     */
    public static JSONArray jsonLoad(String path) {
        // Opret en JSONParser til at laese json strengen
        JSONParser parser = new JSONParser();
        
        // Bruger try-catch til at haandtere fejl fra JSONParser
        try {
            // Find JSONArray fra databasen
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path)); 
            JSONArray jsonArray = (JSONArray) jsonObject.get("formulas");
            
            // Returner det fundet JSONArray
            return jsonArray;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // Returner tomt JSONArray hvis der ikke blev fundet et under try
        return new JSONArray();
    }
    
    /**
     * Bruges til at lave formula instance om til JSONObject
     * @param formula Formular objektet som skal laves om
     * @return JSONObject
     */
    public static JSONObject formulaToJson(Formula formula) {
        // Opret JSONObject
        JSONObject jsonObject = new JSONObject();
        
        // Put navn, beskrivelse og formularen ind i JSONObjektet
        jsonObject.put("name", formula.getName());
        jsonObject.put("description", formula.getDescription());
        jsonObject.put("formula", formula.getFormula());
        
        // Put soegeordene ind i JSONObjektet
        JSONArray tagArray = new JSONArray();
        tagArray.addAll(Arrays.asList(formula.getTags()));
        jsonObject.put("tags", tagArray);
        
        // Put symbolerne ind i JSONObjektet
        JSONArray symbolArray = new JSONArray();
        symbolArray.addAll(Arrays.asList(formula.getSymbols()));
        jsonObject.put("symbols", symbolArray);
        
        return jsonObject;
    }
    
}