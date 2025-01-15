package org.example.emailsender.emailSender;

import lombok.RequiredArgsConstructor;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.service.EmailSenderService;
import org.example.emailsender.service.InfluencerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public abstract class EmailSender {
    protected final InfluencerService influencerService;
    protected static final Logger LOGGER = Logger.getLogger(EmailSenderService.class.getName());
    protected static final String EMAIL_FROM = "";
    protected static final String SUBJECT = "";
    protected static final String COURSE_NAME = "";
    protected static final String CONTENT_HTML = "";

    public abstract List<Influencer> sendEmails(List<Influencer> influencers) throws Exception;
}
