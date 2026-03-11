package gui.main;

import javax.swing.*;

import codice.*;
import gui.vista.*;

/**
 * Finestra principale dell'applicazione con interfaccia grafica.
 * Contiene due schede principali: gestione utenti e gestione bacheca.
 * All'avvio carica automaticamente i dati da file e aggiorna le viste.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class MainGui extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Percorso CSV usato anche dalla CLI per il caricamento iniziale. */
    private static final String FILE_PATH = "../ProgettoParadigmi/bacheca.csv";

    /**
     * Costruttore che inizializza la finestra principale e i suoi componenti.
     * Crea i gestori, carica la bacheca da file, costruisce i pannelli
     * e sincronizza le viste con i dati caricati.
     */
    public MainGui() {
        setTitle("Bacheca Annunci - Gestione Completa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        Bacheca bacheca = new Bacheca();
        GestoreUtente gestoreUtente = new GestoreUtente();
        GestoreBacheca gestoreBacheca = new GestoreBacheca(bacheca, gestoreUtente);

        try {
            gestoreBacheca.leggiDaFile(FILE_PATH);
            System.out.println("Bacheca caricata (GUI) da: " + FILE_PATH);
        } catch (Exception e) {
            System.out.println("Nessun file trovato per la GUI, verrà creato: " + FILE_PATH);
        }

        ContentPanelUtente contentUtenti = new ContentPanelUtente(gestoreUtente);
        ContentPanelBacheca contentBacheca = new ContentPanelBacheca(gestoreBacheca);

        contentUtenti.aggiornaVista();
        contentBacheca.aggiornaVista();

        UtentePanel utentiPanel = new UtentePanel(contentUtenti, gestoreUtente);
        BachecaPanel bachecaPanel = new BachecaPanel(contentBacheca, gestoreBacheca, contentUtenti);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Utenti", utentiPanel);
        tabs.addTab("Bacheca", bachecaPanel);

        add(tabs);
    }

    /**
     * Avvia l'interfaccia grafica.
     *
     * @param args argomenti da linea di comando
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGui gui = new MainGui();
            gui.setVisible(true);
        });
    }
}