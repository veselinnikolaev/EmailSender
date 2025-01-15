package org.example.emailsender.emailSender;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.campaigns.CampaignDelivery;
import com.mailerlite.sdk.campaigns.SingleCampaign;
import com.mailerlite.sdk.emails.EmailBase;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.GroupSubscribersList;
import com.mailerlite.sdk.groups.SingleGroup;
import com.mailerlite.sdk.susbcribers.Subscriber;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.service.InfluencerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
public class MailerLiteSender extends EmailSender {
    private static final String API_KEY = "";
    private static final String GROUP_NAME = "";
    private final MailerLite mailerLite;

    public MailerLiteSender(InfluencerService influencerService) {
        super(influencerService);
        this.mailerLite = new MailerLite();
        mailerLite.setToken(API_KEY);
    }

    @Override
    public List<Influencer> sendEmails(List<Influencer> influencers) throws MailerLiteException {
        List<Influencer> influencersToDelete = new ArrayList<>();

        while(influencersToDelete.size() == influencers.size()) {
            SingleGroup group = mailerLite.groups().builder().create(GROUP_NAME);
            for (Influencer influencer : influencers.subList(influencersToDelete.size(), influencers.size())) {
                try {
                    SingleGroup singleGroup = assignSubscriberToGroup(influencer, group);
                    if (singleGroup.responseStatusCode != 200 && singleGroup.responseStatusCode != 201) {
                        break;
                    }
                    LOGGER.info("Email sent to " + influencer.getEmail());
                    influencersToDelete.add(influencer);
                } catch (MailerLiteException e) {
                    LOGGER.severe("Failed to add subscriber " + influencer.getEmail());
                }
            }

            SingleCampaign campaign = createCampaign(group.group.id);
            scheduleCampaign(campaign.campaign.id);

            unassignSubscribers(group.group.id);
        }
        return influencersToDelete;
    }

    private void unassignSubscribers(String groupId) throws MailerLiteException {
        GroupSubscribersList subscribersList = mailerLite.groups().subscribers(groupId).get();

        for (Subscriber subscriber : subscribersList.subscribers) {
            mailerLite.groups().subscribers(groupId).unassign(subscriber.id);
        }
    }

    private SingleCampaign createCampaign(String groupId) throws MailerLiteException {
        EmailBase email = new EmailBase();
        email.content = CONTENT_HTML;
        email.from = EMAIL_FROM;
        email.fromName = "Cursico";
        email.subject = SUBJECT;

        return mailerLite.campaigns().builder()
                .name(COURSE_NAME)
                .email(email)
                .type("regular")
                .groupId(groupId)
                .create();
    }

    private SingleGroup assignSubscriberToGroup(Influencer influencer, SingleGroup group) throws MailerLiteException {
        Subscriber subscriber = mailerLite.subscribers().builder().email(influencer.getEmail())
                .addField("name", influencer.getNickname())
                .create()
                .subscriber;

        return mailerLite.groups().subscribers(group.group.id).assign(subscriber.id);
    }

    private void scheduleCampaign(String campaignId) throws MailerLiteException {
        mailerLite.campaigns().scheduler()
                .delivery(CampaignDelivery.INSTANT)
                .schedule(campaignId);
    }
}
