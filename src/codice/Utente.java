package codice;

/**
 * Rappresenta un utente registrato nella bacheca.
 * Ogni utente è identificato univocamente dalla propria email
 * (gestita in modo case-insensitive) e possiede un nome.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class Utente {

    private final String nome;
    private final String email;

    /**
     * Espressione regolare per la validazione delle email.
     * L'indirizzo deve contenere un testo prima della @,
     * un dominio dopo la @ e un'estensione di almeno due lettere.
     * Esempi validi: mario.rossi@email.it, user@test.com
     */
    private static final String EMAIL_REGEX = "[\\w\\.]+@[\\w\\.]+\\.[\\w]{2,}";

    /**
     * Costruttore che crea un nuovo utente con nome ed email.
     * L'email viene normalizzata in minuscolo per garantire
     * confronti case-insensitive.
     *
     * @param nome  nome dell'utente
     * @param email email dell'utente, usata come identificativo univoco
     * @throws IllegalArgumentException se nome o email sono null, vuoti o non validi
     */
    public Utente(String nome, String email) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere nullo o vuoto.");
        }
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Email non valida.");
        }
        this.nome = nome;
        this.email = email.toLowerCase(); // normalizzazione
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce l'email dell'utente in minuscolo.
     *
     * @return email normalizzata
     */
    public String getEmail() {
        return email;
    }

    /**
     * Restituisce una rappresentazione testuale dell'utente
     * che include nome ed email.
     *
     * @return stringa con nome ed email
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Utente\n");
        sb.append("  - Nome: ").append(nome).append("\n");
        sb.append("  - Email: ").append(email).append("\n");
        sb.append("\n");
        return sb.toString();
    }
}