
package More;

/**
 * Statiske metoder til at haandtere tekststrenge
 * @author Karsten F. Pedersen, Proc-20A
 */
public class StringHandler {
    
    // Metoder
    /**
     * Fjerner en bestemt karakter
     * @param string Strengen som karakteren skal fjernes fra
     * @param c Karakteren som skal fjernes
     * @param removeAll 
     * @return string
     */
    public static String removeCharacter(String string, char c, boolean removeAll) {
        
        // Tjekker om tekststrengens laengde er over nul
        if (string.length() > 0) {
            // Konvertere karakter til streng
            String stringCharacter = Character.toString(c);

            if (removeAll) {
                // Fjerner alle karaktere som matcher 'stringCharacter'
                string = string.trim().replaceAll(stringCharacter + "+", "");
            } else {
                // Fjerner alle karaktere som staar ved siden af sig selv og matcher 'stringCharacter'
                string = string.trim().replaceAll(stringCharacter + "+", stringCharacter);
                // Soerger for at der ikke er 'stringCharacter' foran eller bagved tekststrengen
                string = removeFirstAndLast(string, c);
            }
        }
        
        // Returnere behandlet tekststreng
        return string;
    }
    
    /**
     * Fjerner foerste og sidste karakter
     * @param string Stengen som skal behandles
     * @param c Karakteren som skal fjernes
     * @return string
     */
    public static String removeFirstAndLast(String string, char c) {
        if (string.charAt(0) == c && string.charAt(string.length() - 1) == c) {
            // Fjerner foerste og sidste karakter
            string = string.substring(1, string.length() - 1);
        } else if (string.charAt(0) == c) {
            // Fjerner foerste karakter
            string = string.substring(1, string.length());
        } else if (string.charAt(string.length() - 1) == c) {
            // Fjerner sidste karakter
            string = string.substring(0, string.length() - 1);
        }
        
        // Returnere behandlet streng
        return string;
    }
    
    /**
     * Splitter en streng ved ','
     * @param string Strengen som skal behandles
     * @return string
     */
    public static String[] splitText(String string) {  
        // Bruger try-catch til at haandtere fejl fra metoden 'removeCharacter'
        // Den vil smide fejl hvis inputtet er tomt
        try {
            string = removeCharacter(string, ' ', true);
            string = removeCharacter(string, ',', false);
            
            // Returner tekststrengarray hvor 'string' er splittet
            return string.split("[,]");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("[ERROR] [error] " + e.getMessage());
        }
        
        // Returnere tomt tekststrengarray hvis der opstod en fejl
        return new String[]{};
    }

}