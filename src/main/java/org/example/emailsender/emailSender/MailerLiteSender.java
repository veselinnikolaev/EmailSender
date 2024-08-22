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
    private static final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNDhmNzE2NjBkMjY1ZDE5YjQ5OTc2MDRiNzA2N2Q4ZWRjZjBmN2U2NTczYWU5N2U4YzQ4Y2Q5YTM4YTE0YWNjNGYwODViMmIwYmM1YTJiYWUiLCJpYXQiOjE3MjMxOTU1ODguMDYzOTM1LCJuYmYiOjE3MjMxOTU1ODguMDYzOTM3LCJleHAiOjQ4Nzg4NjkxODguMDU3MTAzLCJzdWIiOiIxMDY2MjcwIiwic2NvcGVzIjpbXX0.a39kcXfKtUGLitTHqJliEANZN3hCx1AGgPLalbuH9nWCc2prh2I3zdvZfKPvhUjNZV2ViYQoKaGRjQdCbh4cpbCUKLz5qPmIDy03HqgNYxDIg8rfE6BBYt-pTLGmvo8oSbEPNJuafTlwA-A576mVpbFvRFAOPe1q1wG_iMpYu8x3PgtPK1fDJjNaxx6jiZiZ2jm5YebyQmjS3ioVs8ND33dzLCW1UGGu_ZmGlvPixyJLkC7DMyvxGdJX8t_EI0jtJaq0GrC2eNnWLL4304g_sHLDbYO2Y5tMR-WUeebOXrGk15DtnCBpLdYG3oVm9XlfO7UT8b-8x9pnFlgQjbb-IIkFgLBplKErCOp2uu0yDlVpmlyxvJGj0i1pIoAQjV-3tU2AeiG9zgA4I5WHfOiDQ4L7jsmr4I4572d_DIosQP-Mw2WTgk0lA6Zb45n-HSKYPzCQevfvaUvMIcxPuvqrRRETe_762axbqykG-9sBpy134-msT2T0wCMf-DqkhCRKZ5D71Azk1lDOFOIPcJSWaI8L7lGwWwTo_62MBKv2B-x_iZW5snM9BiRbnLXEgvWavZc-m00kPSFh9G_FkPO7_J10aKkzSF7dKTmQPLEoN-LC7nBUQu4Mt_8Kt-2uYtCuLwsLZQercePyRvtXeC2LhzPpSR3hsQZlYKVwSAy1otw";
    private static final String GROUP_NAME = "MindfulnessMastery";
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