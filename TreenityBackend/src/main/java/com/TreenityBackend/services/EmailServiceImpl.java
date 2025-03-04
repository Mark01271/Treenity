package com.TreenityBackend.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * Implementazione del servizio per l'invio di email. 
 * Il servizio include metodi per inviare email di conferma agli utenti e notifiche agli amministratori.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    /**
     * Invia una email di conferma di ricezione della richiesta all'utente.
     * Il contenuto dell'email è in formato HTML.
     *
     * @param userEmail L'email dell'utente a cui inviare la conferma
     * @param contactPerson Il nome della persona di contatto per personalizzare il messaggio
     */
    @Override
    public void sendUserConfirmationEmail(String userEmail, String contactPerson) {
        // Crea il contenuto dell'email in formato HTML
        String htmlContent = """
        	    <div style="font-family: Arial, sans-serif; line-height: 1.6; padding: 20px; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 10px; position: relative;">
        	    
                <div style="text-align: center; margin-bottom: 20px;">
                    <img src="https://i.imgur.com/2OZaSMP.png" alt="Cascina Giovane" style="max-width: 150px;">
                </div>
              
                <div style="text-align: center; margin-bottom: 20px;">
                    <img src="https://images.pexels.com/photos/955388/pexels-photo-955388.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Immagine ringraziamento" style="width: 100%; height: auto; border-radius: 0;">
                </div>
            
                <div style="text-align: center; margin-bottom: 20px;">
                    <h1 style="color: #333;">La tua richiesta è stata ricevuta.</h1>
                    <h1 style="color: #333;">Grazie!</h1>
                    <p style="color: #555;">""" + contactPerson +
                    """
                    ,</p>
                </div>
                
                <p style="color: #555; text-align: center; margin-bottom: 20px;">
                    Abbiamo ricevuto con successo il tuo messaggio, il nostro staff avrà cura di risponderti e risolvere eventuali dubbi.
                </p>
                <p style="color: #555; text-align: center;">Nel frattempo ti invitiamo a:</p>
                <ul style="color: #555; margin: 20px 0; padding-left: 20px;">
                    <li style="margin-bottom: 10px;">Fare un salto sul portale se volessi compilare un’altra richiesta, ottenere informazioni riguardo il gruppo di Cascina Giovane.</li>
                    <li>Visitare il sito ufficiale di Cascina Caccia per trovare ulteriori informazioni dettagliate sulle nostre attività educative, laboratori e collaborazioni.</li>
                </ul>
                
                <div style="text-align: center; margin-top: 20px;">
                    <a href="https://www.cascinacaccia.net" style="display: inline-block; margin: 10px; padding: 10px 20px; color: white; background-color: #f70084; text-decoration: none; border-radius: 5px;">Esplora il sito</a>
                    <a href="http://localhost:3000/" style="display: inline-block; margin: 10px; padding: 10px 20px; color: white; background-color: #f70084; text-decoration: none; border-radius: 5px;">Vai al portale</a>
                </div>
            
                <table width="100%" style="margin-top: 30px; font-size: 0.9em; color: #777; background-color: #ffeb3b; padding: 10px 20px;">
                  <tr>
                    <td align="center" style="padding: 10px;">
                      <a href="https://www.facebook.com/cascinacaccia/?locale=it_IT" style="margin-right: 10px;">
                        <img src="https://i.imgur.com/TU0YhXd.png" alt="Facebook" style="width: 20px; height: auto; vertical-align: middle;">
                      </a>
                      <span style="margin: 0; vertical-align: middle;">
                        © Cascina Caccia | <a href="https://www.cascinacaccia.net" style="color: #555; text-decoration: none;">www.cascinacaccia.net</a>
                      </span>
                      <a href="https://www.instagram.com/cascina_caccia/" style="margin-left: 10px;">
                        <img src="https://i.imgur.com/2uMykpH.png" alt="Instagram" style="width: 20px; height: auto; vertical-align: middle;">
                      </a>
                    </td>
                  </tr>
                </table>
            </div>
        """; // Fine del contenuto HTML

        // Invio dell'email tramite il JavaMailSender
        try {
            // Creazione di un oggetto MimeMessage per l'invio dell'email HTML
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(userEmail);  // Destinatario
            helper.setSubject("Conferma di ricezione richiesta"); // Oggetto
            helper.setText(htmlContent, true); // Imposta il corpo del messaggio come HTML

            // Invio dell'email
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Errore nell'invio dell'email di conferma", e);  // Gestione dell'errore
        }
    }

    /**
     * Invia una notifica email all'amministratore riguardo una nuova richiesta ricevuta.
     * La notifica include i dettagli della richiesta.
     *
     * @param adminEmail L'email dell'amministratore a cui inviare la notifica
     * @param requestDetails I dettagli della richiesta ricevuta
     */
    @Override
    public void sendAdminNotificationEmail(String adminEmail, String requestDetails) {
        // Crea un messaggio semplice per notificare l'amministratore
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail); // Destinatario
        message.setSubject("Nuova richiesta ricevuta"); // Oggetto
        message.setText("Hai ricevuto una nuova richiesta:\n\n" + requestDetails);  // Corpo del messaggio

        // Invio dell'email all'amministratore
        javaMailSender.send(message);
    }
}
