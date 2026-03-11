package eccezioni;

/**
 * Eccezione che rappresenta errori nella creazione o gestione di un annuncio.
 * Viene lanciata quando i parametri forniti non rispettano i vincoli richiesti,
 * come nome nullo, prezzo non valido o autore mancante.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class AnnuncioException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Crea una nuova eccezione con il messaggio specificato.
     *
     * @param message descrizione dell'errore
     */
    public AnnuncioException(String message) {
        super(message);
    }

    /**
     * Crea una nuova eccezione con messaggio e causa.
     *
     * @param message descrizione dell'errore
     * @param cause eccezione che ha originato questo errore
     */
    public AnnuncioException(String message, Throwable cause) {
        super(message, cause);
    }
}
