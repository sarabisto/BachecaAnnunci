package gui.vista;

import javax.swing.*;
import java.awt.*;
import codice.GestoreUtente;
import gui.controllo.ControlloUtente;

/**
 * Pannello principale della scheda Utenti.
 * Contiene la vista con l'elenco degli utenti registrati
 * e i pulsanti per le operazioni di gestione.
 *
 * All'inizializzazione aggiorna la vista degli utenti.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class UtentePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Costruisce il pannello principale degli utenti.
     * Inserisce al centro la vista contenente l'elenco utenti
     * e in basso i comandi per la gestione tramite {@link OpsPanelUtente}.
     *
     * @param content vista con l'elenco degli utenti
     * @param gestoreUtente gestore per la gestione degli utenti
     * @throws IllegalArgumentException se {@code content} o {@code gestoreUtente} sono null
     */
    public UtentePanel(ContentPanelUtente content, GestoreUtente gestoreUtente) {
        if (content == null) throw new IllegalArgumentException("ContentPanelUtente non può essere null");
        if (gestoreUtente == null) throw new IllegalArgumentException("GestoreUtente non può essere null");

        setLayout(new BorderLayout());

        ControlloUtente controllo = new ControlloUtente(content, gestoreUtente);
        OpsPanelUtente ops = new OpsPanelUtente(controllo);

        add(content, BorderLayout.CENTER);
        add(ops, BorderLayout.SOUTH);

        content.aggiornaVista();
    }
}