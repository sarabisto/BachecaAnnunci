# Bacheca Annunci

Progetto per il corso di **Paradigmi di Programmazione in Java**  
Anno Accademico **2024/2025**

**Autori**
- Sara Bistoncini (matricola 20050074)
- Vanessa Tafarella (matricola 20044382)

---

# Descrizione del progetto

Il progetto consiste nella realizzazione di una **piattaforma per la gestione di annunci di compravendita**.

Il sistema permette agli utenti di pubblicare, cercare e gestire annunci di vendita o di acquisto attraverso due diverse modalità di utilizzo:

- **Interfaccia testuale (CLI)**
- **Interfaccia grafica (GUI) realizzata con Java Swing**

Il progetto è stato sviluppato seguendo i **principi della programmazione orientata agli oggetti (OOP)** e utilizza **eccezioni personalizzate** per una gestione chiara degli errori.

I dati della bacheca vengono salvati e caricati tramite file **CSV (bacheca.csv)**.

---

# Funzionalità principali

La piattaforma offre le seguenti funzionalità:

- creazione e gestione degli utenti
- inserimento di annunci di vendita o acquisto
- rimozione degli annunci
- ricerca annunci per **ID**
- ricerca annunci tramite **parole chiave**
- aggiunta di parole chiave agli annunci
- eliminazione automatica degli annunci scaduti
- caricamento e salvataggio degli annunci su file CSV
- utilizzo tramite **CLI o GUI**

---

# Struttura del progetto

Il sistema è organizzato in diverse classi principali.

## Annuncio

Rappresenta un singolo annuncio pubblicato nella bacheca.

### Attributi principali

- id → identificativo univoco
- vendita → indica se l’annuncio è di vendita o acquisto
- nome → titolo dell’annuncio
- prezzo → prezzo associato
- paroleChiave → lista di parole chiave
- dataScadenza → data di scadenza (solo per annunci di vendita)
- autore → utente che ha creato l’annuncio

### Metodi principali

- getId()
- getNome()
- setPrezzo()
- setDataScadenza()
- setParoleChiave()
- isVendita()
- isAcquisto()
- isScaduto()
- matchParolaChiave()
- toString()

---

## Bacheca

Rappresenta la raccolta di tutti gli annunci presenti nel sistema.

Gestisce un contenitore dinamico di oggetti **Annuncio** e implementa l’interfaccia:

Iterable<Annuncio>

per consentire l’uso dei cicli **for-each**.

### Metodi principali

- aggiungi(Annuncio a)
- rimuovi(Annuncio a)
- getAnnunci()
- clear()
- iterator()
- toString()

---

## Utente

Rappresenta un utente della piattaforma.

Ogni utente è identificato univocamente dalla **email**.

### Attributi

- nome
- email

L’email viene validata tramite **espressione regolare (regex)**.

### Metodi principali

- getNome()
- getEmail()
- toString()

---

## GestoreBacheca

Gestisce tutte le operazioni sugli annunci.

### Funzionalità principali

- aggiunta di annunci
- rimozione annunci
- ricerca per parola chiave
- ricerca per ID
- aggiunta di parole chiave
- eliminazione annunci scaduti
- stampa annunci
- salvataggio su file CSV
- caricamento da file CSV

---

## GestoreUtente

Gestisce gli utenti registrati.

Utilizza una struttura dati:

Map<String, Utente>

dove la **chiave è l’email** dell’utente.

### Funzioni principali

- aggiunta utenti
- ricerca utenti
- conteggio utenti registrati
- elenco utenti

---

# Eccezioni personalizzate

Per migliorare la gestione degli errori sono state implementate diverse eccezioni.

## AnnuncioException

Segnala errori nella creazione o modifica di un annuncio.

Esempi:
- nome nullo o vuoto
- prezzo non valido
- autore mancante
- data di scadenza non valida

---

## GestoreBachecaException

Segnala errori nella gestione della bacheca.

Esempi:
- annuncio non trovato
- operazioni non valide
- bacheca vuota

### RimozioneNonAutorizzataException

Eccezione lanciata quando un utente tenta di rimuovere un annuncio che non gli appartiene.

---

## GestoreUtenteException

Segnala errori nella gestione degli utenti.

Esempi:
- utente nullo
- email già registrata
- dati non validi

---

# Interfaccia CLI

Il sistema include una **interfaccia a riga di comando (CLI)** che permette di utilizzare tutte le funzionalità della bacheca.

Funzionalità disponibili:

- registrazione nuovi utenti
- ricerca utenti per email
- visualizzazione elenco utenti
- inserimento annunci
- rimozione annunci
- ricerca per ID
- ricerca per parola chiave
- aggiunta parole chiave
- pulizia annunci scaduti
- salvataggio e caricamento dati da file

---

# Interfaccia GUI

È stata sviluppata anche una **interfaccia grafica utilizzando Java Swing**.

La GUI è composta da due sezioni principali:

- **Utenti**
- **Bacheca**

Gestite tramite un componente:

JTabbedPane

### Componenti principali

- MainGui → finestra principale
- UtentePanel → gestione utenti
- BachecaPanel → gestione annunci

### Controller

Le azioni dell’interfaccia sono gestite da:

- ControlloUtente
- ControlloBacheca

che collegano la GUI con la logica del sistema.

---

# Persistenza dei dati

Gli annunci vengono salvati e caricati tramite il file:

bacheca.csv

Questo consente di mantenere lo stato della bacheca tra diverse esecuzioni del programma.

---

# Tecnologie utilizzate

- Java
- Java Swing
- Programmazione orientata agli oggetti (OOP)
- CSV per la persistenza dei dati
- Eccezioni personalizzate
