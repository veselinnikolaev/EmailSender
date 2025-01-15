package org.example.emailsender.emailSender;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import com.sparkpost.model.responses.Response;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.service.InfluencerService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SparkPostSender extends EmailSender {
    private static final String API_KEY = "";

    public SparkPostSender(InfluencerService influencerService) {
        super(influencerService);
    }

    @Override
    public List<Influencer> sendEmails(List<Influencer> influencers) throws SparkPostException {
        List<Influencer> influencersToDelete = new ArrayList<>();
        Client client = new Client(API_KEY);

        for (Influencer influencer : influencers) {
                Response response = client.sendMessage(
                        EMAIL_FROM,
                        influencer.getEmail(),
                        SUBJECT,
                        "",
                        CONTENT_HTML);
            if(response.getResponseCode() == 202) {
                LOGGER.info("Email sent to " + influencer.getEmail());
                influencersToDelete.add(influencer);
            }
        }

        return influencersToDelete;
    }
}
