package org.example.emailsender.innit;

import lombok.RequiredArgsConstructor;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.reader.CsvReader;
import org.example.emailsender.repository.InfluencerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialCsvReading implements CommandLineRunner {
    private final CsvReader csvReader;
    private final InfluencerRepository influencerRepository;
    private static final String PATH = "C:/Users/user/Downloads/course 1.csv";

    @Override
    public void run(String... args) {
        if (influencerRepository.count() == 0) {
            return;
        }

        List<Influencer> influencers = csvReader
                .readCsvFile(PATH);
        influencerRepository.saveAll(influencers);
    }
}
