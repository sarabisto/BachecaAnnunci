package test;

import codice.*;
import eccezioni.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per {@link GestoreBacheca}.
 * Verifica la gestione degli annunci in bacheca attraverso
 * operazioni di aggiunta, ricerca compatibilità, rimozione
 * e pulizia degli annunci scaduti.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class GestoreBachecaTest {

    private Bacheca bacheca;
    private GestoreUtente gestoreUtente;
    private GestoreBacheca gestore;
    private Utente autore;

    /**
     * Inizializza una bacheca vuota, un gestore utenti e un autore valido
     * prima di ciascun test.
     *
     * @throws GestoreUtenteException se l'autore non può essere aggiunto
     */
    @BeforeEach
    void setup() throws GestoreUtenteException {
        bacheca = new Bacheca();
        gestoreUtente = new GestoreUtente();
        gestore = new GestoreBacheca(bacheca, gestoreUtente);

        autore = new Utente("Mario Rossi", "mario.rossi@gmail.com");
        gestoreUtente.aggiungiUtente(autore);
    }
    
    /**
     * Verifica che il costruttore inizializzi correttamente con argomenti validi.
     */
    @Test
    void testCostruttoreGestoreBacheca() {
        Bacheca b = new Bacheca();
        GestoreUtente gu = new GestoreUtente();
        GestoreBacheca g = new GestoreBacheca(b, gu);

        assertSame(b, g.getBacheca(), "La bacheca deve essere inizializzata correttamente");
        assertSame(gu, g.getGestoreUtente(), "Il gestore utenti deve essere inizializzato correttamente");
    }

    /**
     * Verifica che un annuncio di vendita venga aggiunto correttamente.
     *
     * @throws Exception se si verificano errori di gestione
     */
    @Test
    void testAggiungiAnnuncio() throws Exception {
        Annuncio vendita = new Annuncio("Notebook", 800.0, List.of("pc", "tech"), LocalDate.of(2025, 10, 10), autore);
        gestore.aggiungiAnnuncio(vendita);

        assertEquals(1, bacheca.getAnnunci().size());
        assertEquals("Notebook", bacheca.getAnnunci().get(0).getNome());
    }

    /**
     * Verifica che l'aggiunta di un annuncio di acquisto individui correttamente
     * gli annunci compatibili già presenti in bacheca.
     *
     * @throws Exception se si verificano errori di gestione
     */
    @Test
    void testAggiungiAnnuncioAcquistoConCompatibili() throws Exception {
        Annuncio vendita = new Annuncio("Bici", 300.0, List.of("bici", "sport"), LocalDate.of(2025, 12, 1), autore);
        gestore.aggiungiAnnuncio(vendita);

        Annuncio acquisto = new Annuncio("Acquisto bici", 250.0, List.of("bici", "usato"), autore);

        List<Annuncio> compatibili = gestore.aggiungiAnnuncioAcquisto(acquisto);

        assertEquals(2, bacheca.getAnnunci().size());
        assertTrue(compatibili.contains(vendita));
    }

    /**
     * Verifica che la rimozione di un annuncio da parte dell'autore
     * avvenga correttamente.
     *
     * @throws Exception se si verificano errori di gestione
     */
    @Test
    void testRimuoviAnnuncioAutoreCorretto() throws Exception {
        Annuncio vendita = new Annuncio("PlayStation", 400.0, List.of("gaming"), LocalDate.of(2025, 9, 15), autore);
        gestore.aggiungiAnnuncio(vendita);

        assertEquals(1, bacheca.getAnnunci().size());

        gestore.rimuoviAnnuncio(vendita.getId(), autore);

        assertEquals(0, bacheca.getAnnunci().size());
    }

    /**
     * Verifica che la rimozione di un annuncio da parte di un utente diverso
     * dall'autore generi un'eccezione di non autorizzazione.
     *
     * @throws Exception se si verificano errori di gestione
     */
    @Test
    void testRimuoviAnnuncioAutoreSbagliato() throws Exception {
        Utente altro = new Utente("Luigi Bianchi", "luigi.bianchi@gmail.com");
        gestoreUtente.aggiungiUtente(altro);

        Annuncio vendita = new Annuncio("Stampante", 90.0, List.of("ufficio"), LocalDate.of(2025, 10, 1), autore);
        gestore.aggiungiAnnuncio(vendita);

        assertThrows(GestoreBachecaException.RimozioneNonAutorizzataException.class,
                () -> gestore.rimuoviAnnuncio(vendita.getId(), altro));
    }

    /**
     * Verifica che la pulizia della bacheca rimuova gli annunci di vendita scaduti
     * lasciando intatti quelli ancora validi.
     *
     * @throws Exception se si verificano errori di gestione
     */
    @Test
    void testPulisciScaduti() throws Exception {
        Annuncio scaduto = new Annuncio("Vecchio PC", 100.0, List.of("pc"), LocalDate.of(2022, 1, 1), autore);
        Annuncio valido = new Annuncio("Nuovo PC", 900.0, List.of("pc"), LocalDate.of(2025, 10, 1), autore);

        gestore.aggiungiAnnuncio(scaduto);
        gestore.aggiungiAnnuncio(valido);

        assertEquals(2, bacheca.getAnnunci().size());

        gestore.pulisciBacheca();

        assertEquals(1, bacheca.getAnnunci().size());
        assertEquals("Nuovo PC", bacheca.getAnnunci().get(0).getNome());
    }
}