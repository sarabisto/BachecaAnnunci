package gui.controllo;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import gui.vista.ContentPanelBacheca;
import gui.vista.ContentPanelUtente;
import codice.*;
import eccezioni.GestoreBachecaException;
import gui.vista.DialogoAnnuncio;

/**
 * Controllore della bacheca. Gestisce le azioni dell'utente provenienti
 * dall'interfaccia grafica, come aggiunta, modifica, ricerca, rimozione e
 * salvataggio degli annunci. Aggiorna le viste collegate dopo ogni operazione.
 * Le ricerche per email sono case-insensitive.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class ControlloBacheca implements ActionListener {

    private final GestoreBacheca gestore;
    private final ContentPanelBacheca view;
    private final ContentPanelUtente utentiView;

    /**
     * Costruttore che inizializza il controllore con vista bacheca e gestore.
     *
     * @param view vista della bacheca
     * @param gestore gestore della bacheca
     * @throws IllegalArgumentException se uno dei parametri è null
     */
    public ControlloBacheca(ContentPanelBacheca view, GestoreBacheca gestore) {
        this(view, gestore, null);
    }

    /**
     * Costruttore che inizializza il controllore con vista bacheca, gestore e vista utenti.
     *
     * @param view vista della bacheca
     * @param gestore gestore della bacheca
     * @param utentiView vista utenti da aggiornare (può essere null)
     * @throws IllegalArgumentException se view o gestore sono null
     */
    public ControlloBacheca(ContentPanelBacheca view, GestoreBacheca gestore, ContentPanelUtente utentiView) {
        if (view == null) throw new IllegalArgumentException("view non può essere null");
        if (gestore == null) throw new IllegalArgumentException("gestore non può essere null");
        this.view = view;
        this.gestore = gestore;
        this.utentiView = utentiView;
    }

    /**
     * Gestisce le azioni generate dai pulsanti della GUI. In base al testo del
     * pulsante esegue l'operazione corrispondente. Al termine aggiorna sempre le viste.
     *
     * @param e evento generato dal pulsante
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String action = source.getText();

        try {
            if (action.equals("Aggiungi Annuncio")) {
                String email = JOptionPane.showInputDialog("Inserisci email autore:");
                if (email == null || email.isBlank()) return;
                Utente autore = gestore.getGestoreUtente().cercaUtente(email.trim().toLowerCase());
                if (autore == null) {
                    JOptionPane.showMessageDialog(null, "Utente non registrato.");
                    return;
                }

                DialogoAnnuncio dialog = new DialogoAnnuncio();
                Object[] dati = dialog.getInputs();
                if (dati != null) {
                    boolean vendita = (boolean) dati[0];
                    String nome = (String) dati[1];
                    double prezzo = (double) dati[2];
                    String[] keywords = (String[]) dati[3];
                    java.time.LocalDate scadenza = (java.time.LocalDate) dati[4];

                    Annuncio nuovo;
                    if (vendita) {
                        nuovo = new Annuncio(nome, prezzo, java.util.Arrays.asList(keywords), scadenza, autore);
                    } else {
                        nuovo = new Annuncio(nome, prezzo, java.util.Arrays.asList(keywords), autore);
                    }

                    if (nuovo.isAcquisto()) {
                        List<Annuncio> compatibili = gestore.aggiungiAnnuncioAcquisto(nuovo);
                        if (!compatibili.isEmpty()) {
                            StringBuilder msg = new StringBuilder("Annunci compatibili trovati:\n");
                            compatibili.forEach(a -> msg.append(a).append("\n"));
                            JOptionPane.showMessageDialog(null, msg.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Nessun annuncio compatibile trovato.");
                        }
                    } else {
                        gestore.aggiungiAnnuncio(nuovo);
                    }
                }
            }

            else if (action.equals("Rimuovi Annuncio")) {
                String idStr = JOptionPane.showInputDialog("Inserisci ID annuncio da rimuovere:");
                if (idStr == null) return;
                int id = Integer.parseInt(idStr.trim());

                String email = JOptionPane.showInputDialog("Inserisci email autore:");
                if (email == null || email.isBlank()) return;
                Utente autore = gestore.getGestoreUtente().cercaUtente(email.trim().toLowerCase());
                if (autore == null) {
                    JOptionPane.showMessageDialog(null, "Utente non registrato.");
                    return;
                }

                gestore.rimuoviAnnuncio(id, autore);
            }

            else if (action.equals("Cerca Annuncio")) {
                String input = JOptionPane.showInputDialog("Inserisci parole chiave separate da virgola:");
                if (input == null || input.isBlank()) return;

                List<String> parole = new ArrayList<>();
                for (String p : input.split(",")) {
                    if (!p.isBlank()) parole.add(p.trim());
                }

                try {
                    List<Annuncio> trovati = gestore.cercaPerParoleChiave(parole);
                    StringBuilder risultati = new StringBuilder("Risultati:\n");
                    trovati.forEach(a -> risultati.append(a).append("\n"));
                    JOptionPane.showMessageDialog(null, risultati.toString());
                } catch (GestoreBachecaException ex) {
                    JOptionPane.showMessageDialog(null, "Risultati: Nessuno");
                }
            }

            else if (action.equals("Cerca per ID")) {
                String idStr = JOptionPane.showInputDialog("Inserisci ID:");
                if (idStr == null) return;
                int id = Integer.parseInt(idStr.trim());
                Annuncio a = gestore.cercaPerId(id);
                JOptionPane.showMessageDialog(null, (a != null) ? a : "Annuncio non trovato.");
            }

            else if (action.equals("Aggiungi Parole Chiave")) {
                String idStr = JOptionPane.showInputDialog("ID annuncio:");
                if (idStr == null) return;
                int id = Integer.parseInt(idStr.trim());
                Annuncio target = gestore.cercaPerId(id);
                if (target == null) {
                    JOptionPane.showMessageDialog(null, "Annuncio non trovato.");
                    return;
                }

                String email = JOptionPane.showInputDialog("Inserisci email autore:");
                if (email == null || email.isBlank()) return;
                Utente autore = gestore.getGestoreUtente().cercaUtente(email.trim().toLowerCase());
                if (autore == null || !autore.equals(target.getAutore())) {
                    JOptionPane.showMessageDialog(null, "Non sei autorizzato a modificare questo annuncio.");
                    return;
                }

                String parole = JOptionPane.showInputDialog("Nuove parole chiave (separate da virgola):");
                if (parole == null) return;
                gestore.aggiungiParoleChiave(id, java.util.List.of(parole.split(",")));
            }

            else if (action.equals("Pulisci Scaduti")) {
                gestore.pulisciBacheca();
            }

            else if (action.equals("Salva su File")) {
                gestore.salvaSuFile("bacheca.csv");
                JOptionPane.showMessageDialog(null, "Bacheca salvata su file.");
            }

            else if (action.equals("Carica da File")) {
                gestore.leggiDaFile("bacheca.csv");
                JOptionPane.showMessageDialog(null, "Bacheca caricata da file.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
        }

        if (view != null) {
            view.aggiornaVista();
        }
        if (utentiView != null) {
            utentiView.aggiornaVista();
        }
    }
}