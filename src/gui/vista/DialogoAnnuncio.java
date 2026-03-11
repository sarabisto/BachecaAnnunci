package gui.vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Finestra di dialogo per l'inserimento di un nuovo annuncio.
 * 
 * @author Sara Bistoncini 20050074
 * @author Vanessa Tafarella 20044382
 */
public class DialogoAnnuncio {

    private JComboBox<String> tipoBox;
    private JTextField nomeField;
    private JTextField prezzoField;
    private JTextField paroleField;
    private JTextField scadenzaField;

    /** Costruttore per la creazione di un nuovo annuncio. */
    public DialogoAnnuncio() { }

    /**
     * Mostra la finestra di dialogo e raccoglie i dati dell'annuncio.
     * In caso di conferma, ritorna un array con i dati necessari a creare il nuovo Annuncio.
     * In caso di annullamento, ritorna null.
     */
    public Object[] getInputs() {
        tipoBox = new JComboBox<>(new String[] { "Vendita", "Acquisto" });
        nomeField = new JTextField(20);
        prezzoField = new JTextField(10);
        paroleField = new JTextField(20);
        scadenzaField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoBox);
        panel.add(new JLabel("Nome articolo:"));
        panel.add(nomeField);
        panel.add(new JLabel("Prezzo:"));
        panel.add(prezzoField);
        panel.add(new JLabel("Parole chiave (separate da ,):"));
        panel.add(paroleField);
        panel.add(new JLabel("Data scadenza (YYYY-MM-DD, solo per vendite):"));
        panel.add(scadenzaField);

        int result = JOptionPane.showConfirmDialog(
            null, panel,
            "Nuovo Annuncio",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean vendita = "Vendita".equals(tipoBox.getSelectedItem());

                String nome = nomeField.getText().trim();
                double prezzo = Double.parseDouble(prezzoField.getText().trim());
                String[] keywords = paroleField.getText().trim().isEmpty()
                                    ? new String[0]
                                    : paroleField.getText().split(",");

                LocalDate scadenza = null;
                if (vendita) {
                    String dataText = scadenzaField.getText().trim();
                    if (dataText.isEmpty()) {
                        throw new IllegalArgumentException("La data di scadenza è obbligatoria per una vendita.");
                    }
                    scadenza = LocalDate.parse(dataText);
                }

                return new Object[] { vendita, nome, prezzo, keywords, scadenza };

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Errore: prezzo non valido.");
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Errore: data non valida (usa formato YYYY-MM-DD).");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage());
            }
        }
        return null;
    }
}