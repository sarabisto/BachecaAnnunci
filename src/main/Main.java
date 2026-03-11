package main;

import javax.swing.SwingUtilities;

import codice.*;
import eccezioni.*;
import gui.main.MainGui;

/**
 * Classe di avvio dell'applicazione.
 * Contiene il metodo main che inizializza sia
 * l'interfaccia grafica che quella a riga di comando.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class Main {

    private static final String FILE_PATH = "../ProgettoParadigmi/bacheca.csv";

    private static Bacheca bacheca;
    private static GestoreUtente gestoreUtente;
    private static GestoreBacheca gestoreBacheca;

    /**
     * Metodo principale di avvio.
     * Avvia l'interfaccia grafica e quella a riga di comando,
     * dopo aver caricato i dati da file.
     *
     * @param args argomenti da linea di comando
     * @throws AnnuncioException se si verificano errori sugli annunci
     * @throws GestoreBachecaException se si verificano errori sulla bacheca
     * @throws GestoreUtenteException se si verificano errori sugli utenti
     */
    public static void main(String[] args)
            throws AnnuncioException, GestoreBachecaException, GestoreUtenteException {

        inizializzaGestori();

        interfacciaGrafica();
        interfacciaRigaComando();
    }

    /**
     * Inizializza bacheca e gestori, caricando da file.
     */
    private static void inizializzaGestori() {
        bacheca = new Bacheca();
        gestoreUtente = new GestoreUtente();
        gestoreBacheca = new GestoreBacheca(bacheca, gestoreUtente);

        try {
            gestoreBacheca.leggiDaFile(FILE_PATH);
            System.out.println("Bacheca caricata da: " + FILE_PATH);
        } catch (Exception e) {
            System.out.println("Nessun file trovato, verrà creato " + FILE_PATH);
        }
    }

    /**
     * Avvia l'interfaccia a riga di comando.
     */
    private static void interfacciaRigaComando() {
        codice.InterfacciaRigaComando.main(new String[0]);
    }

    /**
     * Avvia l'interfaccia grafica.
     */
    private static void interfacciaGrafica() {
        SwingUtilities.invokeLater(() -> new MainGui().setVisible(true));
    }
}