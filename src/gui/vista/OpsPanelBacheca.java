package gui.vista;

import javax.swing.*;
import java.awt.*;
import gui.controllo.ControlloBacheca;

/**
 * Pannello dei comandi per la gestione della bacheca e degli annunci.
 * Organizza i pulsanti in tre righe:
 * Riga 1 contiene le operazioni sugli annunci (aggiungi, rimuovi, modifica).
 * Riga 2 contiene le operazioni di ricerca e aggiunta di parole chiave.
 * Riga 3 contiene le operazioni di manutenzione e gestione file.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class OpsPanelBacheca extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Costruisce il pannello delle operazioni della bacheca.
     * I pulsanti sono disposti su tre righe e collegati al controllore.
     *
     * @param controllo controllore che gestisce le azioni dei pulsanti
     */
    public OpsPanelBacheca(ControlloBacheca controllo) {
        setLayout(new GridLayout(3, 1, 10, 10));

        JPanel riga1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton addBtn = new JButton("Aggiungi Annuncio");
        JButton removeBtn = new JButton("Rimuovi Annuncio");
        addBtn.addActionListener(controllo);
        removeBtn.addActionListener(controllo);
        riga1.add(addBtn);
        riga1.add(removeBtn);

        JPanel riga2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton searchBtn = new JButton("Cerca Annuncio");
        JButton searchIdBtn = new JButton("Cerca per ID");
        JButton keywordsBtn = new JButton("Aggiungi Parole Chiave");
        searchBtn.addActionListener(controllo);
        searchIdBtn.addActionListener(controllo);
        keywordsBtn.addActionListener(controllo);
        riga2.add(searchBtn);
        riga2.add(searchIdBtn);
        riga2.add(keywordsBtn);

        JPanel riga3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton cleanBtn = new JButton("Pulisci Scaduti");
        JButton saveBtn = new JButton("Salva su File");
        JButton loadBtn = new JButton("Carica da File");
        cleanBtn.addActionListener(controllo);
        saveBtn.addActionListener(controllo);
        loadBtn.addActionListener(controllo);
        riga3.add(cleanBtn);
        riga3.add(saveBtn);
        riga3.add(loadBtn);

        add(riga1);
        add(riga2);
        add(riga3);
    }
}