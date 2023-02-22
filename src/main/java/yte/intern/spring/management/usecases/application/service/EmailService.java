package yte.intern.spring.management.usecases.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public String sendMessageWithAttachment(String to, String img) {
        try {
            String htmlTemp = "<img src=\"cid:qrImage\" alt=\"qr code\">";

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            message.setSubject("Event Manager Joined Event");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent("this is an email for your attendance", "text/plain; charset=UTF-8");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlTemp, "text/html; charset=UTF-8");

            Multipart multiPart = new MimeMultipart("alternative");

            // create a new imagePart and add it to multipart so that the image is inline attached in the email
            byte[] rawImage = Base64.getDecoder().decode(img);
            BodyPart imagePart = new MimeBodyPart();
            ByteArrayDataSource imageDataSource = new ByteArrayDataSource(rawImage,"image/png");

            imagePart.setDataHandler(new DataHandler(imageDataSource));
            imagePart.setHeader("Content-ID", "<qrImage>");
            imagePart.setFileName("someFileName.png");

            multiPart.addBodyPart(imagePart);
            multiPart.addBodyPart(textPart);
            multiPart.addBodyPart(htmlPart);

            message.setContent(multiPart);

            emailSender.send(message);
            return "mail sent..";
        }catch (MessagingException e){
            return "error while sending mail...";
        }


    }

}
