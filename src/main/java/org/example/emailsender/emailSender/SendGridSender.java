package org.example.emailsender.emailSender;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.service.InfluencerService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SendGridSender extends EmailSender {
    private static final String API_KEY = "";
    public SendGridSender(InfluencerService influencerService) {
        super(influencerService);
    }

    @Override
    public List<Influencer> sendEmails(List<Influencer> influencers) throws IOException {
        List<Influencer> influencersToDelete = new ArrayList<>();
        Email from = new Email(EMAIL_FROM);
        for (Influencer influencer : influencers) {
            Email to = new Email(influencer.getEmail());

            Content content = new Content("text/html", CONTENT_HTML);
            Mail mail = new Mail(from, SUBJECT, to, content);

            SendGrid sg = new com.sendgrid.SendGrid(API_KEY);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() == 202) {
                influencerService.delete(influencer);
                LOGGER.info("Email sent to " + influencer.getEmail());
            } else {
                LOGGER.info("Failed to send email to " + influencer.getEmail());
            }
        }

        return influencersToDelete;
    }
}
