package eccezioni;

/**
 * Eccezione generica per errori relativi alla gestione degli utenti.
 * Può essere utilizzata quando si tenta di aggiungere un utente non valido
 * o con email già registrata.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class GestoreUtenteException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Crea una nuova eccezione per la gestione degli utenti.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public GestoreUtenteException(String message) {
        super(message);
    }
}