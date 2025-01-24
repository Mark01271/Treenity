package com.TreenityBackend.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendUserConfirmationEmail(String userEmail, String contactPerson) {
        // Crea il messaggio HTML
        String htmlContent = """
        	    <html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .email-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 10px;
            overflow: hidden;
        }
        .header {
            text-align: center;
            padding: 20px;
        }
        .header img {
            max-width: 150px;
        }
        .content {
            padding: 20px;
            color: #333;
        }
        .content h1 {
            color: #333;
        }
        .content p {
            margin: 10px 0;
        }
        .cta {
            text-align: center;
            margin-top: 20px;
        }
        .cta a {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            color: white;
            background-color: #f70084;
            text-decoration: none;
            border-radius: 5px;
        }
        .footer {
            text-align: center;
            font-size: 0.9em;
            color: #777;
            background-color: #ffeb3b;
            padding: 10px;
        }
        .footer img {
            width: 20px;
            height: auto;
            vertical-align: middle;
        }
        .footer a {
            color: #555;
            text-decoration: none;
            margin: 0 10px;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <!-- Header Section -->
        <div class="header">
            <img src="https://i.imgur.com/2OZaSMP.png" alt="Cascina Giovane">
        </div>

        <!-- Content Section -->
        <div class="content">
            <div style="text-align: center; margin-bottom: 20px;">
                <img src="https://images.pexels.com/photos/955388/pexels-photo-955388.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Immagine ringraziamento" style="width: 100%; height: auto;">
            </div>
            <h1>La tua richiesta è stata ricevuta.</h1>
            <h1>Grazie!</h1>
            <p>Ciao <strong>""" + contactPerson + """
            </strong>,</p>
            <p>Abbiamo ricevuto con successo il tuo messaggio. Il nostro staff avrà cura di risponderti e risolvere eventuali dubbi.</p>
            <p>Nel frattempo ti invitiamo a:</p>
            <ul>
                <li>Fare un salto sul portale per compilare un’altra richiesta o ottenere informazioni sul gruppo di Cascina Giovane.</li>
                <li>Visitare il sito ufficiale di Cascina Caccia per maggiori dettagli sulle nostre attività educative e laboratori.</li>
            </ul>
            <div class="cta">
                <a href="https://www.cascinacaccia.net">Esplora il sito</a>
                <a href="http://localhost:3000/">Vai al portale</a>
            </div>
        </div>

        <!-- Footer Section -->
        <div class="footer">
            <a href="https://www.facebook.com/cascinacaccia/?locale=it_IT">
                <img src="https://i.imgur.com/TU0YhXd.png" alt="Facebook">
            </a>
            <span>
                © Cascina Caccia |<a href="https://www.cascinacaccia.net">www.cascinacaccia.net</a>
            </span>
            <a href="https://www.instagram.com/cascina_caccia/">
                <img src="https://i.imgur.com/2uMykpH.png" alt="Instagram">
            </a>
        </div>
    </div>
</body>
</html>
        """;

        // Invio della mail
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(userEmail);
            helper.setSubject("Conferma di ricezione richiesta");
            helper.setText(htmlContent, true); // Attiva il formato HTML

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Errore nell'invio dell'email di conferma", e);
        }
    }

    @Override
    public void sendAdminNotificationEmail(String adminEmail, String requestDetails) {
        // Crea il messaggio per l'amministratore
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("Nuova richiesta ricevuta");
        message.setText("Hai ricevuto una nuova richiesta:\n\n" + requestDetails);

        // Invio dell'email
        javaMailSender.send(message);
    }
}
