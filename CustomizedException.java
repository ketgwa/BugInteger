package eserciziobuginteger;

/**
 * @version 0.1g
 */

public class CustomizedException extends Exception {
    private final String descrizione;
    
    public CustomizedException(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Exception {" + descrizione + "}";
    }
}
