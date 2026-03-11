package test;

import codice.Annuncio;
import codice.Bacheca;
import codice.Utente;
import eccezioni.AnnuncioException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe di test per {@link Bacheca}.
 * Verifica il corretto funzionamento delle operazioni fondamentali:
 * creazione di una bacheca, aggiunta e rimozione di annunci,
 * restituzione della lista annunci, iterazione con for-each e
 * contenuto del metodo toString.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
class BachecaTest {

    private Bacheca bacheca;
    private Utente autore;
    private Annuncio annuncioAcquisto;
    private Annuncio annuncioVendita;

    /**
     * Inizializza una bacheca vuota e crea alcuni annunci di test.
     *
     * @throws AnnuncioException se i dati degli annunci non sono validi
     */
    @BeforeEach
    void setUp() throws AnnuncioException {
        bacheca = new Bacheca();
        autore = new Utente("Andrea Pozzi", "andrea.pozzi@example.com");
        annuncioAcquisto = new Annuncio("Libro", 10.0, List.of("studio"), autore);
        annuncioVendita = new Annuncio("Laptop", 500.0, List.of("pc"), LocalDate.now().plusDays(5), autore);
    }
    
    /**
     * Verifica che il costruttore inizializzi una nuova bacheca vuota.
     */
    @Test
    void testCostruttoreBacheca() {
        Bacheca b = new Bacheca();

        assertNotNull(b.getAnnunci(), "La lista annunci non deve essere null");
        assertTrue(b.getAnnunci().isEmpty(), "Una nuova bacheca deve essere vuota");
    }

    /**
     * Verifica che una bacheca appena creata sia vuota. Equivalente a test costruttore per bacheca implementata con ArrayList
     */
    @Test
    void testBachecaVuota() {
        assertTrue(bacheca.getAnnunci().isEmpty(), "Una nuova bacheca deve essere vuota");
    }

    /**
     * Verifica che l'aggiunta di annunci li inserisca correttamente nella bacheca.
     */
    @Test
    void testAggiungiAnnunci() {
        bacheca.aggiungi(annuncioAcquisto);
        bacheca.aggiungi(annuncioVendita);

        assertEquals(2, bacheca.getAnnunci().size());
        assertTrue(bacheca.getAnnunci().contains(annuncioAcquisto));
        assertTrue(bacheca.getAnnunci().contains(annuncioVendita));
    }

    /**
     * Verifica che la rimozione di annunci li elimini correttamente dalla bacheca.
     */
    @Test
    void testRimuoviAnnunci() {
        bacheca.aggiungi(annuncioAcquisto);
        bacheca.aggiungi(annuncioVendita);

        bacheca.rimuovi(annuncioAcquisto);
        bacheca.rimuovi(annuncioVendita);

        assertEquals(0, bacheca.getAnnunci().size());
        assertFalse(bacheca.getAnnunci().contains(annuncioAcquisto));
        assertFalse(bacheca.getAnnunci().contains(annuncioVendita));
    }

    /**
     * Verifica che getAnnunci restituisca una copia difensiva
     * e non la lista interna della bacheca.
     */
    @Test
    void testGetAnnunciCopiaDifensiva() {
        bacheca.aggiungi(annuncioAcquisto);
        List<Annuncio> copia = bacheca.getAnnunci();
        copia.clear();
        assertFalse(bacheca.getAnnunci().isEmpty(), "La bacheca interna non deve essere modificata");
    }

    /**
     * Verifica che sia possibile iterare sugli annunci della bacheca
     * usando il costrutto for-each grazie a {@link Iterable}.
     */
    @Test
    void testIterableForEach() {
        bacheca.aggiungi(annuncioAcquisto);
        bacheca.aggiungi(annuncioVendita);

        List<Annuncio> raccolti = new ArrayList<>();
        for (Annuncio a : bacheca) {
            raccolti.add(a);
        }

        assertEquals(2, raccolti.size(), "Il for-each deve iterare su tutti gli annunci presenti");
        assertTrue(raccolti.contains(annuncioAcquisto));
        assertTrue(raccolti.contains(annuncioVendita));
    }

    /**
     * Verifica che il metodo toString contenga le informazioni sugli annunci presenti.
     */
    @Test
    void testToStringContieneAnnunci() {
        bacheca.aggiungi(annuncioAcquisto);
        String s = bacheca.toString();
        assertTrue(s.contains("Libro"));
        assertTrue(s.contains("Andrea Pozzi"));
    }
}