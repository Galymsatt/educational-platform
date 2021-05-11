package kz.eduplatform.courseadapter.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendMail(String sendTo, String title, String html) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(sendTo);
        helper.setSubject(title);
        helper.setText(html,true);
        mailSender.send(message);
    }
}
