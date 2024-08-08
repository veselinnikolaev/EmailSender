package org.example.emailsender.emailSender;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.campaigns.CampaignDelivery;
import com.mailerlite.sdk.campaigns.SingleCampaign;
import com.mailerlite.sdk.emails.EmailBase;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.SingleGroup;
import com.mailerlite.sdk.susbcribers.Subscriber;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.service.InfluencerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
public class MailerLiteSender extends EmailSender {
    private static final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0Iiwia" +
            "nRpIjoiOTZjZmRkZWRiOTEwNjYyZTA3NjdmMGE1Nzk4NDkzNzk5ZTQ5YjZmN2RlODNmYzUyZjY2MWM4NjY0Ym" +
            "U4ZDkzNTI4NTE4NjEyNDFmMzU1MDciLCJpYXQiOjE3MjMxMjI0NzAuNjAwMTIsIm5iZiI6MTcyMzEyMjQ3MC42MD" +
            "AxMjMsImV4cCI6NDg3ODc5NjA3MC41OTcxOTEsInN1YiI6IjEwNjQ0OTEiLCJzY29wZXMiOltdfQ.GutqF_ScIAtcwYx" +
            "PzsxHQH2uQiKPtTBD_NTuxo6qLGWeSvzpXUqMsrJeaoiATw_WmmrwZz4QKB8PHnmPU_IMYIYxc8QXJOOdOgwI-uWZEtkJt" +
            "iI5wNM8zbgsSkzO4OXB0w-rF-qdKgT2JmbB2bF1OLkaYYWc50Fy4ojqsWZ7BH8fbnNEttssLHMhhIyr9ZELzek6qVtDB0BPWlNS" +
            "fVWerSQpxzZmlwaHe0XIOxzZ9zI_ULGCWvfdGzpFC5JyMejQYXHUlcPydc0gCVEcrlkhqId1VKUwu9aC5hsHzBY3wTbHGK76zDjL_c" +
            "9eSwSaopGwpySJuW6K1DTBtN9xMMvX1MY7_oM9SLPzr_ZYhdfXGm9UATHXuYX4Z4fkS9o7K9GYcceAol77hi3RBi02iHaRQ" +
            "JQAFbu1niTSsE8De5hlT4ijDtb4pNWLg7Zwp_pbXzrO5j6B02jXAg5VGQN-7i_g5KvyRD1gC0syA03UZy42uuAinOkTHfar6LtD-kH7HNCen" +
            "edtgOJgCh6bwwMv_qPpbA0QVwYeWgprl03MKrKgtxz2W4SQTD306o5CW2xrxVWAHZM2d7lHFrY795V_lQJzAnJ-Z6tmTXHWCRSYESClluWjk" +
            "XaT4jty-Lc7F8eE0ngcYWR4PthZGjM-1EGtipwDzs7NV0iiafxA-5PHZEr-TfA";
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

        SingleGroup group = mailerLite.groups().builder().create(GROUP_NAME);
        for (Influencer influencer : influencers) {
            try {
                SingleGroup singleGroup = assignSubscriberToGroup(influencer, group);
                if(singleGroup.responseStatusCode != 200 && singleGroup.responseStatusCode != 201) {
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

        return influencersToDelete;
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
                .addField("name", influencer.getName())
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