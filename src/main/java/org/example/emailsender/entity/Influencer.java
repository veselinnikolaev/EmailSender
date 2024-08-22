package org.example.emailsender.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "influencers")
@Data
@NoArgsConstructor
public class Influencer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "tiktok_url")
    private String tiktokUrl;
    private String nickname;
    private String username;
    private String country;
    private String email;
    @Column(name = "instagram_url")
    private String instagramUrl;
    @Column(name = "course_name")
    private String courseName;


    public Influencer(String tiktokUrl, String nickname, String username, String country, String email, String instagramUrl, String courseName) {
        this.tiktokUrl = tiktokUrl;
        this.nickname = nickname;
        this.username = username;
        this.country = country;
        this.email = email;
        this.instagramUrl = instagramUrl;
        this.courseName = courseName;
    }
}
