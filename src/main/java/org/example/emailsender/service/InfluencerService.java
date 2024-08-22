package org.example.emailsender.service;

import lombok.RequiredArgsConstructor;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.repository.InfluencerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfluencerService {
    private final InfluencerRepository influencerRepository;

    public List<Influencer> findAll() {
        return influencerRepository.findAll();
    }

    public void deleteAll(List<Influencer> sentInfluencers) {
        influencerRepository.deleteAll(sentInfluencers);
    }

    public void delete(Influencer influencer) {
        influencerRepository.delete(influencer);
    }
}
