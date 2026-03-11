package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import codice.Utente;

/**
 * Classe di test per {@link Utente}.
 * Verifica la creazione con valori validi e non validi,
 * il comportamento di equals/hashCode (case-insensitive)
 * e il contenuto di toString.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
class UtenteTest {

    private Utente utenteValido;

    /**
     * Inizializza un utente valido da riutilizzare nei test.
     */
    @BeforeEach
    void setUp() {
        utenteValido = new Utente("Andrea Pozzi", "andrea.pozzi@gmail.com");
    }

    /**
     * Verifica che un utente venga creato correttamente con valori validi.
     */
    @Test
    void testCostruttoreValido() {
        assertEquals("Andrea Pozzi", utenteValido.getNome());
        assertEquals("andrea.pozzi@gmail.com", utenteValido.getEmail());
    }

    /**
     * Verifica che l'email venga sempre normalizzata in lowercase
     * indipendentemente da come è scritta in input.
     */
    @Test
    void testEmailNormalizzataInLowercase() {
        Utente u = new Utente("Andrea Pozzi", "ANDREA.POZZI@GMAIL.COM");
        assertEquals("andrea.pozzi@gmail.com", u.getEmail(),
                "L'email deve essere salvata in lowercase");
    }

    /**
     * Verifica che il costruttore lanci un'eccezione se il nome è vuoto.
     */
    @Test
    void testCostruttoreNomeVuoto() {
        assertThrows(IllegalArgumentException.class, () -> new Utente("", "andrea.pozzi@gmail.com"));
    }

    /**
     * Verifica che il costruttore lanci un'eccezione se il nome è null.
     */
    @Test
    void testCostruttoreNomeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Utente(null, "andrea.pozzi@gmail.com"));
    }

    /**
     * Verifica che il costruttore lanci un'eccezione se l'email non rispetta la regex.
     */
    @Test
    void testCostruttoreEmailNonValida() {
        assertThrows(IllegalArgumentException.class, () -> new Utente("Andrea Pozzi", "email-non-valida"));
    }

    /**
     * Verifica che il costruttore lanci un'eccezione se l'email è null.
     */
    @Test
    void testCostruttoreEmailNull() {
        assertThrows(IllegalArgumentException.class, () -> new Utente("Andrea Pozzi", null));
    }

    /**
     * Verifica che il metodo toString contenga il nome e l'email dell'utente.
     */
    @Test
    void testToStringContieneInfo() {
        String result = utenteValido.toString();
        assertTrue(result.contains("Andrea Pozzi"));
        assertTrue(result.contains("andrea.pozzi@gmail.com"));
    }

    /**
     * Verifica che il metodo toString rispetti il formato previsto
     * (multi-linea con trattini).
     */
    @Test
    void testToStringFormato() {
        String expected = "Utente\n" +
                          "  - Nome: Andrea Pozzi\n" +
                          "  - Email: andrea.pozzi@gmail.com\n\n";
        assertEquals(expected, utenteValido.toString());
    }
}