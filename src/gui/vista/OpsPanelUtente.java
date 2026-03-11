package gui.vista;

import javax.swing.*;
import java.awt.*;
import gui.controllo.ControlloUtente;

/**
 * Pannello dei comandi per la gestione degli utenti.
 * Contiene i pulsanti principali per aggiungere, cercare
 * e visualizzare tutti gli utenti registrati.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class OpsPanelUtente extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Costruisce il pannello dei comandi degli utenti.
     * I pulsanti sono collegati al controllore che gestisce le azioni.
     *
     * @param controllo controllore che gestisce le azioni dei pulsanti
     */
    public OpsPanelUtente(ControlloUtente controllo) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

        JButton addBtn = new JButton("Aggiungi Utente");
        JButton searchBtn = new JButton("Cerca Utente");
        JButton showAllBtn = new JButton("Mostra Tutti");

        addBtn.addActionListener(controllo);
        searchBtn.addActionListener(controllo);
        showAllBtn.addActionListener(controllo);

        add(addBtn);
        add(searchBtn);
        add(showAllBtn);
    }
}