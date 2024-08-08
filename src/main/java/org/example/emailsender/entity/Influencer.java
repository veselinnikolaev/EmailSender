package org.example.emailsender.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "influencers")
@Data
@NoArgsConstructor
public class Influencer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    public Influencer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
