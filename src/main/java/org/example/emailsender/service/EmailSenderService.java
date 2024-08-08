package org.example.emailsender.service;

import lombok.RequiredArgsConstructor;
import org.example.emailsender.emailSender.EmailSender;
import org.example.emailsender.entity.Influencer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final InfluencerService influencerService;
    private final List<EmailSender> emailSenders;

    public void sendEmails() {
        List<Influencer> influencers = influencerService.findAll();
        for (EmailSender emailSender : emailSenders) {
            if (influencers.isEmpty()) {
                break;
            }
            try {
                List<Influencer> sentInfluencers = emailSender.sendEmails(influencers);
                influencers.removeAll(sentInfluencers);
                influencerService.deleteAll(sentInfluencers);
            } catch (Exception e) {
                System.out.println("Failed to send email: " + e.getMessage());
            }
        }
    }

    public void sendTestEmail(){
        for (EmailSender emailSender : emailSenders) {
            try {
                emailSender.sendEmails(List.of(new Influencer("vesko", "veselin.nikolaev-25zh@mgberon.com")));
            } catch (Exception e) {
                System.out.println("Failed to send email: " + e.getMessage());
            }
        }
    }
}
