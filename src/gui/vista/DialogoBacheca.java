package gui.vista;

import javax.swing.*;
import java.awt.*;
import gui.controllo.ControlloBacheca;
import codice.GestoreBacheca;

/**
 * Finestra di dialogo per la gestione della bacheca.
 * 
 * Mostra:
 * - il titolo e la lista degli annunci (ContentPanelBacheca)
 * - i bottoni per le operazioni principali sulla bacheca (OpsPanelBacheca)
 */
public class DialogoBacheca extends JDialog {

    private static final long serialVersionUID = 1L;

    /**
     * Costruttore della finestra di dialogo.
     * 
     * @param parent  la finestra padre (può essere un JFrame)
     * @param gestore il gestore della bacheca da gestire
     */
    public DialogoBacheca(JFrame parent, GestoreBacheca gestore) {
        super(parent, "Gestione Bacheca", true);

        setLayout(new BorderLayout());

        // Vista con il contenuto della bacheca (🔹 ora passo il GestoreBacheca intero)
        ContentPanelBacheca content = new ContentPanelBacheca(gestore);

        // Controllore che collega vista e modello
        ControlloBacheca controllo = new ControlloBacheca(content, gestore);

        // Pannello in basso con i bottoni
        OpsPanelBacheca ops = new OpsPanelBacheca(controllo);

        add(content, BorderLayout.CENTER);
        add(ops, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}