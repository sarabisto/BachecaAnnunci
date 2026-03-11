package gui.controllo;

import java.awt.event.*;
import javax.swing.*;

import gui.vista.ContentPanelUtente;
import codice.*;

/**
 * Controllore per la gestione degli utenti.
 * Gestisce le azioni provenienti dall'interfaccia grafica
 * legate agli utenti: aggiunta, ricerca e visualizzazione.
 * Dopo ogni operazione aggiorna la vista utenti.
 *
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class ControlloUtente implements ActionListener {

    private final GestoreUtente gestore;
    private final ContentPanelUtente view;

    /**
     * Costruttore che inizializza il controllore con la vista utenti e il gestore.
     *
     * @param view vista degli utenti da aggiornare
     * @param gestore gestore che mantiene e gestisce gli utenti
     * @throws IllegalArgumentException se uno dei parametri è null
     */
    public ControlloUtente(ContentPanelUtente view, GestoreUtente gestore) {
        if (view == null) throw new IllegalArgumentException("view non può essere null");
        if (gestore == null) throw new IllegalArgumentException("gestore non può essere null");
        this.view = view;
        this.gestore = gestore;
    }

    /**
     * Gestisce le azioni dei pulsanti relativi agli utenti.
     * In base al testo del pulsante esegue l'operazione corrispondente:
     * aggiunta utente, ricerca utente o visualizzazione di tutti gli utenti.
     * Al termine aggiorna sempre la vista utenti.
     *
     * @param e evento generato dal pulsante
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String action = source.getText();

        try {
            if (action.equals("Aggiungi Utente")) {
                String nome = JOptionPane.showInputDialog("Nome:");
                if (nome == null || nome.isBlank()) return;

                String email = JOptionPane.showInputDialog("Email:");
                if (email == null || email.isBlank()) return;

                Utente nuovo = new Utente(nome.trim(), email.trim().toLowerCase());
                gestore.aggiungiUtente(nuovo);

                JOptionPane.showMessageDialog(null, "Utente aggiunto con successo.");
            }
            else if (action.equals("Cerca Utente")) {
                String email = JOptionPane.showInputDialog("Email utente da cercare:");
                if (email == null || email.isBlank()) return;

                Utente u = gestore.cercaUtente(email.trim().toLowerCase());
                JOptionPane.showMessageDialog(null, (u != null) ? u.toString() : "Utente non trovato.");
            }
            else if (action.equals("Mostra Tutti")) {
                view.aggiornaVista();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
        }

        view.aggiornaVista();
    }
}