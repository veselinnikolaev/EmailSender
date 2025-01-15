package org.example.emailsender.emailSender;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.service.InfluencerService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MailgunSender extends EmailSender {
    private static final String API_KEY = "";
    private static final String DOMAIN_NAME = "";

    public MailgunSender(InfluencerService influencerService) {
        super(influencerService);
    }

    @Override
    public List<Influencer> sendEmails(List<Influencer> influencers) throws Exception {
        List<Influencer> influencersToDelete = new ArrayList<>();

        Configuration configuration = new Configuration()
                .domain(DOMAIN_NAME)
                .apiKey("key-" + API_KEY)
                .from("Cursico", EMAIL_FROM);

        for (Influencer influencer : influencers) {
            Response send = Mail.using(configuration)
                    .to(influencer.getEmail())
                    .subject(SUBJECT)
                    .html(CONTENT_HTML)
                    .build()
                    .send();

            if(send.isOk()){
                LOGGER.info("Email sent to " + influencer.getEmail());
                influencersToDelete.add(influencer);
            } else {
                LOGGER.info("Failed to send email to " + influencer.getEmail());
                LOGGER.info("Message ID: " + send.responseMessage());
                LOGGER.info("Error code: " + send.responseCode());
                break;
            }
        }

        return influencersToDelete;
    }
}

