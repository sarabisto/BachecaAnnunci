package test;

import codice.Annuncio;
import codice.Utente;
import eccezioni.AnnuncioException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe di test per {@link Annuncio}.
 * Verifica la correttezza della creazione, modifica e gestione
 * degli annunci, inclusi i vincoli sui parametri.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
class AnnuncioTest {

    private Utente autore;

    /**
     * Inizializza un utente autore valido da usare nei test.
     */
    @BeforeEach
    void setUp() {
        autore = new Utente("Andrea Pozzi", "andrea.pozzi@example.com");
    }
    
    /**
     * Verifica la creazione di un annuncio tramite il costruttore generale.
     *
     * @throws AnnuncioException se i parametri non sono validi
     */
    @Test
    void testAnnuncioGenerale() throws AnnuncioException {
        LocalDate scadenza = LocalDate.now().plusDays(3);
        Annuncio a = new Annuncio(true, "Telefono", 250.0, List.of("tech","usato"), scadenza, autore);

        assertTrue(a.isVendita());
        assertEquals("Telefono", a.getNome());
        assertEquals(250.0, a.getPrezzo());
        assertEquals(scadenza, a.getDataScadenza());
        assertEquals(autore, a.getAutore());
    }

    /**
     * Verifica la creazione di un annuncio di acquisto valido.
     *
     * @throws AnnuncioException se i parametri non sono validi
     */
    @Test
    void testAnnuncioAcquistoValido() throws AnnuncioException {
        Annuncio a = new Annuncio("Libro", 20.0, List.of("studio", "scuola"), autore);
        assertTrue(a.isAcquisto());
        assertFalse(a.isVendita());
        assertEquals("Libro", a.getNome());
        assertEquals(20.0, a.getPrezzo());
        assertNull(a.getDataScadenza());
    }

    /**
     * Verifica la creazione di un annuncio di vendita valido con scadenza.
     *
     * @throws AnnuncioException se i parametri non sono validi
     */
    @Test
    void testAnnuncioVenditaValido() throws AnnuncioException {
        LocalDate scadenza = LocalDate.now().plusDays(5);
        Annuncio a = new Annuncio("Laptop", 500.0, List.of("pc", "usato"), scadenza, autore);
        assertTrue(a.isVendita());
        assertFalse(a.isAcquisto());
        assertEquals("Laptop", a.getNome());
        assertEquals(500.0, a.getPrezzo());
        assertEquals(scadenza, a.getDataScadenza());
    }

    /**
     * Verifica che un annuncio con nome vuoto generi un'eccezione.
     */
    @Test
    void testAnnuncioNomeVuoto() {
        assertThrows(AnnuncioException.class, () ->
            new Annuncio("   ", 100.0, List.of("test"), autore)
        );
    }

    /**
     * Verifica che un annuncio con prezzo negativo generi un'eccezione.
     */
    @Test
    void testAnnuncioPrezzoNegativo() {
        assertThrows(AnnuncioException.class, () ->
            new Annuncio("Oggetto", -10.0, List.of("test"), autore)
        );
    }

    /**
     * Verifica che un annuncio con autore null generi un'eccezione.
     */
    @Test
    void testAnnuncioAutoreNull() {
        assertThrows(AnnuncioException.class, () ->
            new Annuncio("Oggetto", 100.0, List.of("test"), null)
        );
    }

    /**
     * Verifica che un annuncio di vendita senza scadenza generi un'eccezione.
     */
    @Test
    void testAnnuncioVenditaSenzaScadenza() {
        assertThrows(AnnuncioException.class, () ->
            new Annuncio("Oggetto", 100.0, List.of("test"), null, autore)
        );
    }

    /**
     * Verifica che un annuncio di acquisto con scadenza generi un'eccezione.
     */
    @Test
    void testAnnuncioAcquistoConScadenza() {
        assertThrows(AnnuncioException.class, () ->
            new Annuncio(false, "Oggetto", 100.0, List.of("test"), LocalDate.now(), autore)
        );
    }  

    /**
     * Verifica che il setter delle parole chiave aggiorni correttamente la lista.
     */
    @Test
    void testSetParoleChiave() throws AnnuncioException {
        Annuncio a = new Annuncio("Libro", 20.0, List.of("scuola"), autore);
        a.setParoleChiave(List.of("studio", "lettura"));
        assertTrue(a.getParoleChiave().contains("lettura"));
    }

    /**
     * Verifica che il metodo isScaduto restituisca true se la data è già passata.
     */
    @Test
    void testIsScaduto() throws AnnuncioException {
        LocalDate ieri = LocalDate.now().minusDays(1);
        Annuncio a = new Annuncio("Laptop", 500.0, List.of("pc"), ieri, autore);
        assertTrue(a.isScaduto());
    }
}