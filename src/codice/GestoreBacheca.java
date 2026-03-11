package codice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import eccezioni.AnnuncioException;
import eccezioni.GestoreUtenteException;
import eccezioni.GestoreBachecaException;
import eccezioni.GestoreBachecaException.RimozioneNonAutorizzataException;

/**
 * Classe che gestisce la bacheca e fornisce metodi per aggiungere, rimuovere,
 * cercare e gestire gli annunci. Include funzionalità per il salvataggio e il
 * caricamento da file. Si appoggia al {@link GestoreUtente} per la gestione degli
 * autori.
 *
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class GestoreBacheca {

	private final Bacheca bacheca;
	private final GestoreUtente gestoreUtente;

	/**
	 * Costruttore del gestore bacheca.
	 *
	 * @param bacheca       bacheca da gestire
	 * @param gestoreUtente gestore degli utenti
	 * @exception IllegalArgumentException se uno dei parametri è null
	 */
	public GestoreBacheca(Bacheca bacheca, GestoreUtente gestoreUtente) {
		if (bacheca == null)
			throw new IllegalArgumentException("La bacheca non può essere null");
		if (gestoreUtente == null)
			throw new IllegalArgumentException("GestoreUtente non può essere null");
		this.bacheca = bacheca;
		this.gestoreUtente = gestoreUtente;
	}

	/**
	 * Aggiunge un annuncio alla bacheca.
	 *
	 * @param annuncio annuncio da aggiungere
	 * @exception GestoreBachecaException se l'annuncio è null
	 */
	public void aggiungiAnnuncio(Annuncio annuncio) throws GestoreBachecaException {
		if (annuncio == null) {
			throw new GestoreBachecaException("L'annuncio non può essere null");
		}
		bacheca.aggiungi(annuncio);
	}

	/**
	 * Aggiunge un annuncio di acquisto e restituisce una lista di annunci
	 * compatibili. Due annunci sono compatibili se condividono almeno una parola
	 * chiave.
	 *
	 * @param annuncio annuncio di acquisto da aggiungere
	 * @return lista di annunci compatibili già presenti in bacheca
	 * @exception GestoreBachecaException se l'annuncio è null o non è di acquisto
	 */
	public List<Annuncio> aggiungiAnnuncioAcquisto(Annuncio annuncio) throws GestoreBachecaException {
		if (annuncio == null) {
			throw new GestoreBachecaException("L'annuncio non può essere null");
		}
		if (!annuncio.isAcquisto()) {
			throw new GestoreBachecaException("Questo metodo è valido solo per annunci di acquisto");
		}

		bacheca.aggiungi(annuncio);

		List<Annuncio> compatibili = new ArrayList<>();
		for (Annuncio a : bacheca) {
			if (a.getId() != annuncio.getId() && a.getParoleChiave() != null) {
				for (String parola : annuncio.getParoleChiave()) {
					if (a.getParoleChiave().contains(parola)) {
						compatibili.add(a);
						break;
					}
				}
			}
		}
		return compatibili;
	}

	/**
	 * Rimuove un annuncio dalla bacheca, verificando che l'autore coincida.
	 *
	 * @param id     identificativo dell'annuncio
	 * @param autore utente che richiede la rimozione
	 * @exception GestoreBachecaException          se l'annuncio non esiste
	 * @exception RimozioneNonAutorizzataException se l'autore non è autorizzato
	 */
	public void rimuoviAnnuncio(int id, Utente autore)
			throws GestoreBachecaException, RimozioneNonAutorizzataException {
		Annuncio target = cercaPerId(id);
		if (target == null) {
			throw new GestoreBachecaException("Annuncio con id " + id + " non trovato");
		}
		if (!target.getAutore().equals(autore)) {
			throw new RimozioneNonAutorizzataException("Utente non autorizzato a rimuovere questo annuncio");
		}
		bacheca.rimuovi(target);
	}

	/**
	 * Cerca annunci che contengono una o molte parola chiave.
	 *
	 * @param parola parola chiave da cercare
	 * @return lista di annunci che contengono almeno una parola
	 */
	public List<Annuncio> cercaPerParoleChiave(List<String> parole) throws GestoreBachecaException {
	    List<Annuncio> risultati = new ArrayList<>();

	    for (Annuncio a : bacheca) {
	        if (a.getParoleChiave() != null) {
	            for (String parola : parole) {
	                if (a.getParoleChiave().contains(parola)) {
	                    risultati.add(a);
	                    break;
	                }
	            }
	        }
	    }

	    if (risultati.isEmpty()) {
	        throw new GestoreBachecaException("Risultati: Nessuno");
	    }

	    return risultati;
	}

	/**
	 * Aggiunge nuove parole chiave a un annuncio identificato da ID.
	 *
	 * @param id          identificativo dell'annuncio
	 * @param nuoveParole lista di nuove parole da aggiungere
	 * @exception GestoreBachecaException se l'annuncio non esiste
	 */
	public void aggiungiParoleChiave(int id, List<String> nuoveParole) throws GestoreBachecaException {
		Annuncio annuncio = cercaPerId(id);
		if (annuncio == null) {
			throw new GestoreBachecaException("Annuncio con ID " + id + " non trovato.");
		}

		List<String> esistenti = annuncio.getParoleChiave();
		if (esistenti == null)
			esistenti = new ArrayList<>();
		esistenti.addAll(nuoveParole);

		annuncio.setParoleChiave(new ArrayList<>(new java.util.HashSet<>(esistenti)));
	}

	/**
	 * Cerca un annuncio tramite ID.
	 *
	 * @param id identificativo dell'annuncio
	 * @return annuncio se trovato, null altrimenti
	 */
	public Annuncio cercaPerId(int id) {
		for (Annuncio a : bacheca) {
			if (a.getId() == id)
				return a;
		}
		return null;
	}

	/**
	 * Rimuove dalla bacheca tutti gli annunci di vendita scaduti.
	 */
	public void pulisciBacheca() {
		List<Annuncio> daRimuovere = new ArrayList<>();
		for (Annuncio a : bacheca) {
			if (a.isVendita() && a.isScaduto()) {
				daRimuovere.add(a);
			}
		}
		for (Annuncio a : daRimuovere) {
			bacheca.rimuovi(a);
		}
	}

	/**
	 * Restituisce la rappresentazione testuale di tutti gli annunci.
	 *
	 * @return stringa con tutti gli annunci
	 * @exception GestoreBachecaException se la bacheca è vuota
	 */
	public String stampaTuttiAnnunci() throws GestoreBachecaException {
		if (bacheca.getAnnunci().isEmpty()) {
			throw new GestoreBachecaException("Nessun annuncio presente in bacheca.");
		}

		StringBuilder sb = new StringBuilder();
		for (Annuncio a : bacheca.getAnnunci()) {
			sb.append(a).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Salva il contenuto della bacheca su file in formato CSV.
	 *
	 * @param filePath percorso del file
	 * @exception IOException se si verificano errori di scrittura
	 */
	public void salvaSuFile(String filePath) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
			for (Annuncio a : bacheca.getAnnunci()) {
				StringBuilder sb = new StringBuilder();
				sb.append(a.getId()).append(";");
				sb.append(a.getNome()).append(";");
				sb.append(a.getPrezzo()).append(";");
				sb.append(a.getAutore().getEmail()).append(";");
				sb.append(a.getAutore().getNome()).append(";");
				sb.append(String.join(",", a.getParoleChiave() == null ? List.of() : a.getParoleChiave()));
				if (a.isVendita()) {
					sb.append(";").append(a.getDataScadenza());
				}
				writer.write(sb.toString());
				writer.newLine();
			}
		}
	}

	/**
	 * Legge il contenuto di una bacheca da file CSV e popola la bacheca.
	 *
	 * @param filePath percorso del file
	 * @exception IOException       se si verificano errori di lettura
	 * @exception AnnuncioException se i dati non rispettano i vincoli degli annunci
	 */
	public void leggiDaFile(String filePath) throws IOException, AnnuncioException {
		bacheca.clear();
		Annuncio.resetCounter();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				if (linea.isBlank())
					continue;
				String[] parti = linea.split(";");

				int id = Integer.parseInt(parti[0].trim());
				String nome = parti[1].trim();
				double prezzo = Double.parseDouble(parti[2].trim());
				String emailAutore = parti[3].trim().toLowerCase();
				String nomeAutore = parti[4].trim();
				Utente autore = gestoreUtente.cercaUtente(emailAutore);
				if (autore == null) {
					autore = new Utente(nomeAutore, emailAutore);
					try {
						gestoreUtente.aggiungiUtente(autore);
					} catch (GestoreUtenteException e) {
						System.out.println("Utente già esistente: " + emailAutore);
					}
				}

				List<String> parole = new ArrayList<>();
				if (parti.length > 5 && !parti[5].isBlank()) {
					for (String kw : parti[5].split(",")) {
						parole.add(kw.trim());
					}
				}

				Annuncio nuovo;
				if (parti.length > 6) {
					LocalDate scadenza = LocalDate.parse(parti[6].trim());
					nuovo = new Annuncio(id, true, nome, prezzo, parole, scadenza, autore);
				} else {
					nuovo = new Annuncio(id, false, nome, prezzo, parole, null, autore);
				}
				bacheca.aggiungi(nuovo);
			}
		}
	}

	/**
	 * Restituisce la bacheca gestita.
	 *
	 * @return bacheca
	 */
	public Bacheca getBacheca() {
		return this.bacheca;
	}

	/**
	 * Restituisce il gestore degli utenti associato.
	 *
	 * @return gestore utenti
	 */
	public GestoreUtente getGestoreUtente() {
		return this.gestoreUtente;
	}
}