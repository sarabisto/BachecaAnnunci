package codice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Rappresenta la bacheca che contiene una collezione di annunci.
 * Fornisce metodi per aggiungere, rimuovere, ottenere e iterare gli annunci, ovvero le operazioni base.
 * In {@link GestoreBacheca} si trova il controller.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class Bacheca implements Iterable<Annuncio> {

    public final List<Annuncio> annunci;
    
    /**
     * Costruttore che inizializza una nuova bacheca vuota.
     */
    public Bacheca() {
        this.annunci = new ArrayList<>();
    }

    /**
     * Aggiunge un annuncio alla bacheca.
     *
     * @param a annuncio da aggiungere
     */
    public void aggiungi(Annuncio a) {
        annunci.add(a);
    }

    /**
     * Rimuove un annuncio dalla bacheca.
     *
     * @param a annuncio da rimuovere
     */
    public void rimuovi(Annuncio a) {
        annunci.remove(a);
    }

    /**
     * Restituisce una copia difensiva della lista degli annunci presenti.
     *
     * @return nuova lista contenente tutti gli annunci della bacheca
     */
    public List<Annuncio> getAnnunci() {
        return new ArrayList<>(annunci);
    }

    /**
     * Svuota completamente la bacheca, rimuovendo tutti gli annunci.
     */
    public void clear() {
        annunci.clear();
    }

    /**
     * Restituisce un iteratore sugli annunci presenti.
     * Permette di usare la bacheca in un ciclo for-each.
     *
     * @return iteratore sugli annunci
     */
    @Override
    public Iterator<Annuncio> iterator() {
        return annunci.iterator();
    }

    /**
     * Restituisce una rappresentazione testuale della bacheca e dei suoi annunci.
     *
     * @return stringa con gli annunci presenti
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bacheca:\n");
        for (Annuncio a : annunci) {
            sb.append(a).append("\n");
        }
        return sb.toString();
    }
}