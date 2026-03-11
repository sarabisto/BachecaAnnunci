package gui.vista;

import javax.swing.*;
import java.awt.*;
import codice.GestoreBacheca;

/**
 * Pannello contenitore per la bacheca.
 * Mostra un titolo in alto e l'area di testo con gli annunci.
 * L'aggiornamento del contenuto è gestito tramite {@link #aggiornaVista()}.
 * I pulsanti di gestione sono in {@link BachecaPanel}.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class ContentPanelBacheca extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextArea textArea;
    private final GestoreBacheca gestore;

    /**
     * Costruisce il pannello che mostra i contenuti della bacheca.
     *
     * @param gestore gestore della bacheca da cui recuperare gli annunci
     */
    public ContentPanelBacheca(GestoreBacheca gestore) {
        this.gestore = gestore;
        setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Contenuto Bacheca");
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
     * Aggiorna il contenuto del pannello leggendo gli annunci dal gestore.
     * Se la bacheca è vuota, mostra un messaggio dedicato.
     */
    public void aggiornaVista() {
        try {
            textArea.setText(gestore.stampaTuttiAnnunci());
        } catch (Exception e) {
            textArea.setText("Nessun annuncio presente.");
        }
    }
}