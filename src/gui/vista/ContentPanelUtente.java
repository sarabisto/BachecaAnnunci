package gui.vista;

import javax.swing.*;
import java.awt.*;
import codice.GestoreUtente;

/**
 * Pannello per la visualizzazione degli utenti registrati.
 * Mostra un titolo in alto e un'area di testo con l'elenco degli utenti.
 * L'aggiornamento del contenuto è gestito tramite {@link #aggiornaVista()}.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class ContentPanelUtente extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextArea textArea;
    private final GestoreUtente gestore;

    /**
     * Costruisce il pannello che mostra gli utenti registrati.
     *
     * @param gestore gestore utenti da cui recuperare i dati
     */
    public ContentPanelUtente(GestoreUtente gestore) {
        this.gestore = gestore;
        setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Utenti Registrati");
        titolo.setFont(new Font("SansSerif", Font.BOLD, 14));
        titolo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(titolo, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createEmptyBorder()); 
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Aggiorna il contenuto del pannello leggendo gli utenti dal gestore.
     * Se non ci sono utenti, mostra un messaggio dedicato.
     */
    public void aggiornaVista() {
        StringBuilder sb = new StringBuilder();
        var tutti = gestore.getTuttiUtenti();
        if (tutti.isEmpty()) {
            sb.append("Nessun utente registrato.");
        } else {
            for (var u : tutti) {
                sb.append(u).append("\n");
            }
        }
        textArea.setText(sb.toString());
    }
}