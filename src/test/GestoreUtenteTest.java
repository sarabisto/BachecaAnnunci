package test;

import codice.*;
import eccezioni.GestoreUtenteException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Classe di test per {@link GestoreUtente}.
 * Verifica il corretto funzionamento delle operazioni
 * di aggiunta, ricerca e gestione degli utenti registrati.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
class GestoreUtenteTest {

    private GestoreUtente gestore;

    /**
     * Inizializza un nuovo gestore utenti prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        gestore = new GestoreUtente();
    }
    
    /**
     * Verifica che il costruttore inizializzi correttamente la struttura dati,
     * partendo con zero utenti registrati.
     */
    @Test
    void testCostruttoreGestoreUtente() {
        GestoreUtente gu = new GestoreUtente();

        assertNotNull(gu.getTuttiUtenti(), "La lista utenti non deve essere null");
        assertTrue(gu.getTuttiUtenti().isEmpty(), "Un nuovo GestoreUtente deve essere vuoto");
        assertEquals(0, gu.numeroUtenti(), "Il numero utenti deve essere 0");
    }

    /**
     * Verifica che un utente valido venga aggiunto correttamente.
     *
     * @throws Exception se i parametri non sono validi
     */
    @Test
    void testAggiungiUtenteValido() throws Exception {
        Utente u = new Utente("Mario Rossi", "mario.rossi@email.com");
        gestore.aggiungiUtente(u);
        assertEquals(1, gestore.numeroUtenti());
    }

    /**
     * Verifica che l'aggiunta di un utente null generi un'eccezione.
     */
    @Test
    void testAggiungiUtenteNull() {
        assertThrows(GestoreUtenteException.class, () -> gestore.aggiungiUtente(null));
    }

    /**
     * Verifica che non sia possibile aggiungere due utenti con la stessa email.
     *
     * @throws Exception se i parametri non sono validi
     */
    @Test
    void testAggiungiUtenteDuplicato() throws Exception {
        Utente u1 = new Utente("Mario Rossi", "mario.rossi@email.com");
        Utente u2 = new Utente("Giulia Verdi", "mario.rossi@email.com");
        gestore.aggiungiUtente(u1);
        assertThrows(GestoreUtenteException.class, () -> gestore.aggiungiUtente(u2));
    }

    /**
     * Verifica che la ricerca di un utente esistente per email funzioni correttamente.
     *
     * @throws Exception se i parametri non sono validi
     */
    @Test
    void testCercaUtenteEsistente() throws Exception {
        Utente u = new Utente("Mario Rossi", "mario.rossi@email.com");
        gestore.aggiungiUtente(u);
        assertEquals(u, gestore.cercaUtente("mario.rossi@email.com"));
    }

    /**
     * Verifica che la ricerca di un utente non esistente restituisca null.
     */
    @Test
    void testCercaUtenteNonEsistente() {
        assertNull(gestore.cercaUtente("non.esiste@email.com"));
    }
    
    /**
     * Verifica che la ricerca di un utente sia case-insensitive:
     * un'email inserita in maiuscolo deve comunque trovare l'utente
     * registrato con la stessa email in minuscolo.
     *
     * @throws Exception se i parametri non sono validi
     */
    @Test
    void testCercaUtenteCaseInsensitive() throws Exception {
        Utente u = new Utente("Mario Rossi", "mario.rossi@email.com");
        gestore.aggiungiUtente(u);
        assertEquals(u, gestore.cercaUtente("MARIO.ROSSI@EMAIL.COM"));
    }

    /**
     * Verifica che non sia possibile aggiungere due utenti con la stessa email
     * differente solo per maiuscole/minuscole. L'email deve essere considerata
     * univoca in modo case-insensitive.
     *
     * @throws Exception se i parametri non sono validi
     */
    @Test
    void testAggiungiUtenteDuplicatoCaseDiverso() throws Exception {
        gestore.aggiungiUtente(new Utente("Mario", "mario@mail.com"));
        assertThrows(GestoreUtenteException.class,
            () -> gestore.aggiungiUtente(new Utente("Altro", "MARIO@mail.com")));
    }

    /**
     * Verifica che getTuttiUtenti restituisca la lista completa
     * degli utenti registrati.
     *
     * @throws Exception se i parametri non sono validi
     */
    @Test
    void testGetTuttiUtenti() throws Exception {
        Utente u1 = new Utente("Mario Rossi", "mario.rossi@gmail.com");
        Utente u2 = new Utente("Luca Bianchi", "luca.bianchi@gmail.com");

        gestore.aggiungiUtente(u1);
        gestore.aggiungiUtente(u2);

        List<Utente> utenti = gestore.getTuttiUtenti();

        assertEquals(2, utenti.size(), "Devono esserci due utenti registrati");
        assertTrue(utenti.contains(u1));
        assertTrue(utenti.contains(u2));
    }
}