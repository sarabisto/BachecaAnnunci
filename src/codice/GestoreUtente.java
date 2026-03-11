package codice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import eccezioni.GestoreUtenteException;

/**
 * Gestisce gli utenti del sistema.
 * Permette di registrare nuovi utenti, cercarli per email,
 * ottenere il numero totale e la lista di tutti gli utenti.
 * Garantisce che ogni email sia univoca (case-insensitive).
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class GestoreUtente {

    private final Map<String, Utente> utenti;

    /**
     * Costruttore che inizializza la struttura dati per memorizzare gli utenti.
     */
    public GestoreUtente() {
        this.utenti = new HashMap<>();
    }

    /**
     * Aggiunge un nuovo utente al sistema.
     *
     * @param utente utente da aggiungere
     * @throws GestoreUtenteException se l'utente è null o se l'email è già registrata
     */
    public void aggiungiUtente(Utente utente) throws GestoreUtenteException {
        if (utente == null) {
            throw new GestoreUtenteException("Utente non può essere null");
        }
        String emailKey = utente.getEmail().toLowerCase(); // normalizzazione
        if (utenti.containsKey(emailKey)) {
            throw new GestoreUtenteException("Email già registrata: " + emailKey);
        }
        utenti.put(emailKey, utente);
    }

    /**
     * Cerca un utente tramite la sua email (case-insensitive).
     *
     * @param email email dell'utente da cercare
     * @return utente corrispondente oppure null se non trovato
     */
    public Utente cercaUtente(String email) {
        if (email == null) return null;
        return utenti.get(email.toLowerCase());
    }

    /**
     * Restituisce il numero totale di utenti registrati.
     *
     * @return numero di utenti registrati
     */
    public int numeroUtenti() {
        return utenti.size();
    }

    /**
     * Restituisce una lista contenente tutti gli utenti registrati.
     *
     * @return lista di utenti
     */
    public List<Utente> getTuttiUtenti() {
        return new ArrayList<>(utenti.values());
    }
}