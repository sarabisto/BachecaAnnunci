package codice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jbook.util.Input;
import eccezioni.AnnuncioException;
import eccezioni.GestoreBachecaException;
import eccezioni.GestoreBachecaException.RimozioneNonAutorizzataException;
import eccezioni.GestoreUtenteException;

/**
 * Classe che fornisce un'interfaccia a riga di comando per interagire con la
 * bacheca. Consente di gestire utenti e annunci, eseguendo operazioni di
 * aggiunta, ricerca, rimozione, salvataggio e caricamento da file.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class InterfacciaRigaComando {

	private static Bacheca bacheca = new Bacheca();
	private static GestoreUtente gestoreUtente = new GestoreUtente();
	private static GestoreBacheca gestoreBacheca = new GestoreBacheca(bacheca, gestoreUtente);

	private static final String FILE_PATH = "../ProgettoParadigmi/bacheca.csv";

	/**
	 * Metodo principale che avvia il programma e gestisce il menu interattivo.
	 *
	 * @param args argomenti da linea di comando
	 */
	public static void main(String[] args) {
		try {
			gestoreBacheca.leggiDaFile(FILE_PATH);
			System.out.println("Bacheca caricata da " + FILE_PATH);
		} catch (Exception e) {
			System.out.println("Nessun file trovato, verrà creato " + FILE_PATH);
		}

		int scelta;
		do {
			mostraMenu();
			scelta = Input.readInt("Scelta: ");
			try {
				switch (scelta) {
				case 1 -> aggiungiUtente();
				case 2 -> cercaUtente();
				case 3 -> mostraUtenti();
				case 4 -> aggiungiAnnuncio();
				case 5 -> rimuoviAnnuncio();
				case 6 -> cercaPerId();
				case 7 -> cercaPerParolaChiave();
				case 8 -> aggiungiParoleChiave();
				case 9 -> gestoreBacheca.pulisciBacheca();
				case 10 -> System.out.println(gestoreBacheca.stampaTuttiAnnunci());
				case 11 -> salvaSuFile();
				case 12 -> leggiDaFile();
				case 0 -> System.out.println("Uscita dal programma.");
				default -> System.out.println("Scelta non valida.");
				}
			} catch (Exception e) {
				System.out.println("Errore: " + e.getMessage());
			}
		} while (scelta != 0);
	}

	/**
	 * Mostra il menu principale delle operazioni disponibili.
	 */
	private static void mostraMenu() {
		System.out.println("\n--- MENU ---");
		System.out.println("1) Aggiungi utente");
		System.out.println("2) Cerca utente");
		System.out.println("3) Mostra tutti gli utenti");
		System.out.println("4) Aggiungi annuncio");
		System.out.println("5) Rimuovi annuncio");
		System.out.println("6) Cerca annuncio per ID");
		System.out.println("7) Cerca annunci per parola chiave");
		System.out.println("8) Aggiungi parole chiave a un annuncio");
		System.out.println("9) Pulisci bacheca da annunci scaduti");
		System.out.println("10) Mostra tutti gli annunci");
		System.out.println("11) Salva bacheca su file");
		System.out.println("12) Carica bacheca da file");
		System.out.println("0) Esci");
	}

	/**
	 * Aggiunge un nuovo utente leggendo i dati da input.
	 *
	 * @exception GestoreUtenteException se i dati non sono validi o l'email è già registrata
	 */
	private static void aggiungiUtente() throws GestoreUtenteException {
	    String nome = Input.readString("Nome: ");
	    String email = Input.readString("Email: ").trim().toLowerCase();   
	    gestoreUtente.aggiungiUtente(new Utente(nome, email));
	}

	/**
	 * Cerca un utente tramite email e ne stampa i dati.
	 *
	 * @exception GestoreUtenteException se l'utente non è presente nel sistema
	 */
	private static void cercaUtente() throws GestoreUtenteException {
	    String email = Input.readString("Email utente: ").trim().toLowerCase();  
	    Utente u = gestoreUtente.cercaUtente(email);
	    if (u == null) {
	        throw new GestoreUtenteException("Utente non trovato: " + email);
	    }
	    System.out.println(u);
	}

	/**
	 * Mostra tutti gli utenti registrati nella bacheca.
	 *
	 * @exception GestoreUtenteException se non è presente alcun utente registrato
	 */
	private static void mostraUtenti() throws GestoreUtenteException {
	    List<Utente> utenti = gestoreUtente.getTuttiUtenti();
	    if (utenti.isEmpty()) {
	        throw new GestoreUtenteException("Nessun utente registrato.");
	    }
	    utenti.forEach(System.out::println);
	}

	/**
	 * Aggiunge un annuncio leggendo i dati da input.
	 *
	 * @exception GestoreBachecaException se l'autore non è registrato o i dati dell'annuncio non sono validi
	 */
	private static void aggiungiAnnuncio() throws GestoreBachecaException {
	    try {
	        String nome = Input.readString("Nome annuncio: ");
	        double prezzo = Input.readDouble("Prezzo: ");
	        String email = Input.readString("Email autore: ").trim().toLowerCase();   
	        Utente autore = gestoreUtente.cercaUtente(email);
	        if (autore == null) {
	            throw new GestoreBachecaException("Autore non registrato: " + email);
	        }

	        String keywordsInput = Input.readString("Parole chiave separate da virgola: ");
	        List<String> keywords = new ArrayList<>();
	        if (!keywordsInput.isBlank()) {
	            for (String kw : keywordsInput.split(",")) {
	                String k = kw.trim();
	                if (!k.isBlank()) keywords.add(k);
	            }
	        }

	        String risposta = Input.readString("Vuoi inserire una data di scadenza (si/no)? ");

	        Annuncio a;
	        if (risposta.equalsIgnoreCase("si")) {
	            LocalDate scadenza = LocalDate.parse(Input.readString("Data scadenza (YYYY-MM-DD): "));
	            a = new Annuncio(nome, prezzo, keywords, scadenza, autore);
	        } else {
	            a = new Annuncio(nome, prezzo, keywords, autore);
	        }

	        if (a.isAcquisto()) {
	            List<Annuncio> compatibili = gestoreBacheca.aggiungiAnnuncioAcquisto(a);
	            System.out.println("Annuncio di acquisto aggiunto con successo!");
	            if (!compatibili.isEmpty()) {
	                System.out.println("Annunci compatibili trovati:");
	                compatibili.forEach(System.out::println);
	            } else {
	                System.out.println("Nessun annuncio compatibile trovato.");
	            }
	        } else {
	            gestoreBacheca.aggiungiAnnuncio(a);
	            System.out.println("Annuncio di vendita aggiunto con successo!");
	        }
	    } catch (AnnuncioException e) {
	        throw new GestoreBachecaException("Errore nella creazione dell'annuncio: " + e.getMessage());
	    }
	}

	/**
	 * Rimuove un annuncio dalla bacheca se l'autore corrisponde.
	 *
	 * @exception GestoreBachecaException          se si verifica un errore di
	 *                                             gestione
	 * @exception RimozioneNonAutorizzataException se l'autore non è autorizzato
	 *                                             alla rimozione
	 */
	private static void rimuoviAnnuncio() throws GestoreBachecaException, RimozioneNonAutorizzataException {
		int id = Input.readInt("ID annuncio: ");
		String email = Input.readString("Email autore: ").trim().toLowerCase();   
		Utente autore = gestoreUtente.cercaUtente(email);
		gestoreBacheca.rimuoviAnnuncio(id, autore);
	}

	/**
	 * Cerca un annuncio per ID e ne stampa i dati.
	 *
	 * @exception GestoreBachecaException se l'annuncio con l'ID indicato non esiste
	 */
	private static void cercaPerId() throws GestoreBachecaException {
	    int id = Input.readInt("ID: ");
	    Annuncio a = gestoreBacheca.cercaPerId(id);
	    if (a == null) {
	        throw new GestoreBachecaException("Annuncio non trovato: id=" + id);
	    }
	    System.out.println(a);
	}

	/**
	 * Cerca annunci tramite una o più parole chiave e li stampa.
	 *
	 * @exception GestoreBachecaException in caso di errore nella ricerca
	 */
	private static void cercaPerParolaChiave() throws GestoreBachecaException {
	    String input = Input.readString("Inserisci parole chiave separate da virgola: ");
	    List<String> parole = new ArrayList<>();
	    for (String p : input.split(",")) {
	        if (p != null && !p.isBlank()) parole.add(p.trim());
	    }
	    List<Annuncio> risultati = gestoreBacheca.cercaPerParoleChiave(parole);
	    risultati.forEach(System.out::println);
	}

	/**
	 * Aggiunge nuove parole chiave a un annuncio esistente (solo autore).
	 *
	 * @exception GestoreBachecaException se annuncio inesistente, autore non registrato/non autorizzato
	 */
	private static void aggiungiParoleChiave() throws GestoreBachecaException {
	    int id = Input.readInt("ID annuncio: ");

	    Annuncio target = gestoreBacheca.cercaPerId(id);
	    if (target == null) {
	        throw new GestoreBachecaException("Annuncio non trovato: id=" + id);
	    }

	    String email = Input.readString("Email autore: ").trim().toLowerCase();
	    Utente autore = gestoreUtente.cercaUtente(email);
	    if (autore == null) {
	        throw new GestoreBachecaException("Autore non registrato: " + email);
	    }
	    if (!autore.equals(target.getAutore())) {
	        throw new GestoreBachecaException("Non sei autorizzato ad aggiornare le parole chiave di questo annuncio.");
	    }

	    String parole = Input.readString("Nuove parole chiave (separate da virgola): ");
	    List<String> lista = new ArrayList<>();
	    for (String p : parole.split(",")) {
	        if (p != null && !p.isBlank()) lista.add(p.trim());
	    }
	    if (lista.isEmpty()) {
	        throw new GestoreBachecaException("Nessuna parola chiave valida.");
	    }

	    gestoreBacheca.aggiungiParoleChiave(id, lista);
	    System.out.println("Parole chiave aggiunte con successo.");
	}
	/**
	 * Salva la bacheca su file.
	 */
	private static void salvaSuFile() {
		try {
			gestoreBacheca.salvaSuFile(FILE_PATH);
			System.out.println("Bacheca salvata in: " + FILE_PATH);
		} catch (Exception e) {
			System.out.println("Errore salvataggio: " + e.getMessage());
		}
	}

	/**
	 * Carica la bacheca da file.
	 */
	private static void leggiDaFile() {
		try {
			gestoreBacheca.leggiDaFile(FILE_PATH);
			System.out.println("Bacheca caricata da: " + FILE_PATH);
		} catch (Exception e) {
			System.out.println("Errore caricamento: " + e.getMessage());
		}
	}
}