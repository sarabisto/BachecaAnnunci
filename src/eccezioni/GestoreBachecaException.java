package eccezioni;

/**
 * Eccezione principale per errori nella gestione della bacheca.
 * Può essere usata per segnalare problemi generici come ricerca fallita,
 * aggiunta o modifica non valida.
 * 
 * Contiene anche la sottoclasse {@link RimozioneNonAutorizzataException}
 * per gestire i casi in cui un utente tenta di rimuovere un annuncio
 * che non gli appartiene.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class GestoreBachecaException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Costruisce una nuova eccezione generica per la gestione della bacheca.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public GestoreBachecaException(String message) {
        super(message);
    }

    /**
     * Eccezione specifica lanciata quando un utente tenta di rimuovere
     * un annuncio che non gli appartiene.
     */
    public static class RimozioneNonAutorizzataException extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * Costruisce una nuova eccezione di rimozione non autorizzata.
         *
         * @param message messaggio descrittivo dell'errore
         */
        public RimozioneNonAutorizzataException(String message) {
            super(message);
        }
    }
}