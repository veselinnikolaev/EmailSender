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
    protected static final String EMAIL_FROM = "mindfulness.mastery@cursico.com";
    protected static final String SUBJECT = "Collaboration Opportunity";
    protected static final String COURSE_NAME = "Mindfulness Mastery";
    protected static final String CONTENT_HTML = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Email to Influencer</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                    }
                    .container {
                        width: 100%;
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                        border: 1px solid #ddd;
                        border-radius: 8px;
                        background-color: #f9f9f9;
                    }
                    h1, h2, h3 {
                        color: #0056b3;
                    }
                    .cta {
                        display: inline-block;
                        padding: 10px 20px;
                        margin: 10px 0;
                        border-radius: 5px;
                        background-color: #0056b3;
                        color: #fff;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <p>Hi [Influencer's Name],</p>
                    <p>I hope this email finds you well.</p>
                    <p>My name is Sandro Westley and I’m reaching out on behalf of Mindfulness Mastery.</p>
                    <p>We’ve been following your inspiring content and believe that your message aligns perfectly with our mission to promote mindfulness and well-being. We are excited to offer you an exclusive collaboration opportunity that we think you’ll find incredibly rewarding.</p>
                    <p>Our 30-day mindfulness course is designed to help individuals reduce stress, improve focus, and enhance their overall well-being. As an influencer who promotes a healthy and balanced lifestyle, we believe you would be an excellent partner to help us reach a wider audience.</p>

                    <h2>Here’s why you shouldn’t miss this opportunity:</h2>

                    <h3>1. Lucrative Commission Structure:</h3>
                    <p><strong>50% commission:</strong> on all sales generated through your unique referral link. This means your efforts will be directly rewarded, making this a highly profitable partnership for you.</p>

                    <h3>2. Exclusive Benefits Just for You:</h3>
                    <ul>
                        <li><strong>Early Access:</strong> Be among the first to access our exclusive content and previews of upcoming courses, giving you fresh and valuable insights to share with your audience.</li>
                        <li><strong>Special Discount Code:</strong> Offer your followers a unique discount code, making the course more appealing and accessible to them.</li>
                    </ul>

                    <h3>3. Comprehensive Support and Resources:</h3>
                    <ul>
                        <li><strong>Promotional Materials:</strong> We’ll provide all the necessary resources, including high-quality graphics, promotional materials, and a detailed guide to help you effectively promote the course.</li>
                        <li><strong>Dedicated Support:</strong> Our team is always here to assist you with any questions or needs during our collaboration.</li>
                    </ul>

                    <h3>4. Your Course Opportunity:</h3>
                    <p>We can create a fully personalized course tailored specifically for <strong>YOU</strong> and <strong>YOUR</strong> audience!!! This ensures the content resonates deeply with your followers, addressing their unique needs and interests.</p>

                    <p><strong>Act Fast:</strong> This is a limited-time offer, and we are reaching out to a select few influencers who we believe can truly make a difference. Don’t miss out on this opportunity to be part of something impactful and rewarding.</p>
                   \s
                    <p>We would love to discuss this further and tailor our collaboration to best fit your needs. Please let us know a convenient time for a call or feel free to reply to this email with any questions you might have.</p>
                   \s
                    <p>Thank you for considering this opportunity. We are excited about the potential of creating something truly special together.</p>

                    <p>Warm regards,<br>
                    Sandro Westley<br>
                    Marketing Manager<br>
                    Mindfulness Mastery<br>
                    <a href="mailto:mindfulness.mastery.course@gmail.com">mindfulness.mastery.course@gmail.com</a></p>
                </div>
            </body>
            </html>""";

    public abstract List<Influencer> sendEmails(List<Influencer> influencers) throws Exception;
}
