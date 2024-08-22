package org.example.emailsender.reader;

import lombok.RequiredArgsConstructor;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.utils.HttpRequestUtil;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvReader {
    /*public List<Influencer> readCsvFile(String path) {
        List<Influencer> influencers = new ArrayList<>();

        try (FileReader reader = new FileReader(path);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withSkipHeaderRecord()
                     .withIgnoreSurroundingSpaces()
                     .withIgnoreEmptyLines())) {

            for (CSVRecord record : csvParser) {
                String name = record.get("Nickname");
                String email = record.get("Email");

                Influencer influencer = new Influencer(name, email);
                influencers.add(influencer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return influencers;
    }*/

    public List<Influencer> readCsvFile(String path) {
        List<Influencer> influencers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // skip the header row
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 1) {
                    String tiktokUrl = values[0].trim().replaceAll("\"", "");
                    String name = values[1].trim().replaceAll("\"", "");
                    String username = values[2].trim().replaceAll("\"", "");
                    String country = values[3].trim().replaceAll("\"", "");
                    String email = values[4].trim().replaceAll("\"", "");
                    String instagram = values[5].trim().replaceAll("\"", "");

                    Influencer influencer = new Influencer(tiktokUrl, name, username, country, email, instagram, "SeekSocial");
                    influencers.add(influencer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return influencers;
    }
}