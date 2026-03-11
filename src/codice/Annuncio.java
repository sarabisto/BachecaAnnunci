package codice;

import java.time.LocalDate;
import java.util.List;

import eccezioni.AnnuncioException;

/**
 * Questa classe rappresenta un annuncio nella bacheca.
 * Un annuncio può essere di vendita o di acquisto.
 * Contiene nome, prezzo, autore, eventuali parole chiave e scadenza.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class Annuncio {
	
	//counter usato per dare ad ogni nuovo annuncio un ID univoco progressivo
    private static int counter = 0;

    private final int id;
    private final boolean vendita;
    private String nome;
    private double prezzo;
    private List<String> paroleChiave;
    private LocalDate dataScadenza;
    private final Utente autore;

    /**
     * Costruttore principale usato per creare un nuovo annuncio con ID generato automaticamente.
     *
     * @param vendita true se è un annuncio di vendita, false se è di acquisto
     * @param nome nome dell'annuncio
     * @param prezzo prezzo associato all'annuncio
     * @param paroleChiave lista di parole chiave
     * @param dataScadenza data di scadenza se vendita, null se acquisto
     * @param autore utente autore dell'annuncio
     * @exception AnnuncioException se i parametri non rispettano i vincoli
     */
    public Annuncio(boolean vendita, String nome, double prezzo, List<String> paroleChiave,
                    LocalDate dataScadenza, Utente autore) throws AnnuncioException {
        this(++counter, vendita, nome, prezzo, paroleChiave, dataScadenza, autore);
    }

    /**
     * Costruttore che accetta anche l'ID, usato per caricare annunci da file csv.
     *
     * @param id identificativo univoco dell'annuncio
     * @param vendita true se è un annuncio di vendita, false se è di acquisto
     * @param nome nome dell'annuncio
     * @param prezzo prezzo associato all'annuncio
     * @param paroleChiave lista di parole chiave
     * @param dataScadenza data di scadenza se vendita, null se acquisto
     * @param autore utente autore dell'annuncio
     * @exception AnnuncioException se i parametri non rispettano i vincoli
     */
    public Annuncio(int id, boolean vendita, String nome, double prezzo, List<String> paroleChiave,
                    LocalDate dataScadenza, Utente autore) throws AnnuncioException {
        if (nome == null || nome.isBlank()) {
            throw new AnnuncioException("Il nome non può essere nullo o vuoto.");
        }
        if (prezzo <= 0) {
            throw new AnnuncioException("Il prezzo deve essere positivo.");
        }
        if (autore == null) {
            throw new AnnuncioException("Ogni annuncio deve avere un autore.");
        }
        if (vendita && dataScadenza == null) {
            throw new AnnuncioException("Gli annunci di vendita devono avere una data di scadenza.");
        }
        if (!vendita && dataScadenza != null) {
            throw new AnnuncioException("Gli annunci di acquisto non devono avere una data di scadenza.");
        }

        this.id = id;
        this.vendita = vendita;
        this.nome = nome.trim();
        this.prezzo = prezzo;
        this.paroleChiave = paroleChiave;
        this.dataScadenza = dataScadenza;
        this.autore = autore;

        if (id > counter) {
            counter = id;
        }
    }

    /**
     * Costruttore rapido per annuncio di acquisto (senza data di scadenza).
     *
     * @param nome nome dell'annuncio
     * @param prezzo prezzo associato all'annuncio
     * @param paroleChiave lista di parole chiave
     * @param autore utente autore dell'annuncio
     * @exception AnnuncioException se i parametri non rispettano i vincoli
     */
    public Annuncio(String nome, double prezzo, List<String> paroleChiave, Utente autore) throws AnnuncioException {
        this(false, nome, prezzo, paroleChiave, null, autore);
    }

    /**
     * Costruttore rapido per annuncio di vendita con data di scadenza.
     *
     * @param nome nome dell'annuncio
     * @param prezzo prezzo associato all'annuncio
     * @param paroleChiave lista di parole chiave
     * @param dataScadenza data di scadenza
     * @param autore utente autore dell'annuncio
     * @exception AnnuncioException se i parametri non rispettano i vincoli
     */
    public Annuncio(String nome, double prezzo, List<String> paroleChiave, LocalDate dataScadenza, Utente autore)
            throws AnnuncioException {
        this(true, nome, prezzo, paroleChiave, dataScadenza, autore);
    }

    /** @return identificativo univoco dell'annuncio */
    public int getId() { return id; }

    /** @return true se è un annuncio di vendita */
    public boolean isVendita() { return vendita; }

    /** @return true se è un annuncio di acquisto */
    public boolean isAcquisto() { return !vendita; }

    /** @return nome dell'annuncio */
    public String getNome() { return nome; }

    /** @return prezzo dell'annuncio */
    public double getPrezzo() { return prezzo; }

    /** @return lista di parole chiave associate */
    public List<String> getParoleChiave() { return paroleChiave; }

    /** @return data di scadenza se presente */
    public LocalDate getDataScadenza() { return dataScadenza; }

    /** @return autore dell'annuncio */
    public Utente getAutore() { return autore; }

    /**
     * Modifica le parole chiave associate all'annuncio.
     *
     * @param paroleChiave nuova lista di parole chiave
     */
    public void setParoleChiave(List<String> paroleChiave) {
        this.paroleChiave = paroleChiave;
    }

    /**
     * Verifica se un annuncio di vendita è scaduto rispetto alla data attuale.
     *
     * @return true se l'annuncio è scaduto
     */
    public boolean isScaduto() {
        return vendita && dataScadenza != null && LocalDate.now().isAfter(dataScadenza);
    }

    /**
     * Restituisce una rappresentazione testuale dell'annuncio con tutte le informazioni principali.
     *
     * @return stringa formattata con i dettagli dell'annuncio
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Annuncio n. ").append(id).append(" (ID=").append(id).append(")\n");
        sb.append("  - Tipo: ").append(vendita ? "Vendita" : "Acquisto").append("\n");
        sb.append("  - Nome: ").append(nome).append("\n");
        sb.append("  - Prezzo: ").append(prezzo).append(" €\n");
        sb.append("  - Autore: ").append(autore.getNome())
          .append(" <").append(autore.getEmail()).append(">\n");
        sb.append("  - Keywords: ").append(paroleChiave != null ? paroleChiave : "[]").append("\n");
        if (vendita) {
            sb.append("  - Scadenza: ").append(dataScadenza != null ? dataScadenza : "non impostata").append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Reimposta il contatore degli ID degli annunci a zero.
     */
    public static void resetCounter() {
        counter = 0;
    }
}