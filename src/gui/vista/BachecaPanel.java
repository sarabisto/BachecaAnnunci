package gui.vista;

import javax.swing.*;
import java.awt.*;
import gui.controllo.ControlloBacheca;
import codice.GestoreBacheca;

/**
 * Pannello principale per la gestione della bacheca.
 * Contiene la vista degli annunci e i pulsanti di controllo associati.
 * Si occupa di collegare la vista al controllore e al gestore.
 *
 * All'inizializzazione aggiorna la vista della bacheca.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class BachecaPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Costruisce il pannello principale della bacheca.
     * Inserisce al centro il content panel e in basso i pulsanti di gestione.
     *
     * @param content vista della bacheca con gli annunci
     * @param gestore gestore della bacheca
     * @param utentiView vista degli utenti, usata per aggiornare le informazioni
     * @throws IllegalArgumentException se {@code content} o {@code gestore} sono null
     */
    public BachecaPanel(ContentPanelBacheca content, GestoreBacheca gestore, ContentPanelUtente utentiView) {
        if (content == null) throw new IllegalArgumentException("ContentPanelBacheca non può essere null");
        if (gestore == null) throw new IllegalArgumentException("GestoreBacheca non può essere null");

        setLayout(new BorderLayout());

        ControlloBacheca controllo = new ControlloBacheca(content, gestore, utentiView);
        OpsPanelBacheca ops = new OpsPanelBacheca(controllo);

        add(content, BorderLayout.CENTER);
        add(ops, BorderLayout.SOUTH);

        // Aggiorna la vista al primo caricamento
        content.aggiornaVista();
        if (utentiView != null) {
            utentiView.aggiornaVista();
        }
    }
}