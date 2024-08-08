package org.example.emailsender.emailSender;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.service.InfluencerService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostmarkSender extends EmailSender {
    private static final String API_KEY = "e4a60dcc-585d-41cb-b513-daf9af47d598";
    private final ApiClient client;

    public PostmarkSender(InfluencerService influencerService) {
        super(influencerService);
        this.client = Postmark.getApiClient(API_KEY);
    }

    @Override
    public List<Influencer> sendEmails(List<Influencer> influencers) throws Exception {
        List<Influencer> influencersToDelete = new ArrayList<>();

        for (Influencer influencer : influencers) {
             MessageResponse response = sendEmail(influencer);
            if (response.getErrorCode() != 0) {
                influencersToDelete.add(influencer);
            }
        }
        return influencersToDelete;
    }

    private MessageResponse sendEmail(Influencer influencer) throws PostmarkException, IOException {
        Message message = new Message(EMAIL_FROM,
                influencer.getEmail(),
                SUBJECT, CONTENT_HTML);

        return client.deliverMessage(message);
    }
}
