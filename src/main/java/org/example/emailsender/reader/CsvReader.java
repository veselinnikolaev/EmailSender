package org.example.emailsender.reader;

import org.example.emailsender.entity.Influencer;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
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
                    String name = values[1].trim().replaceAll("\"", "");
                    String email = values[4].trim().replaceAll("\"", "");

                    Influencer influencer = new Influencer(name, email);
                    influencers.add(influencer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return influencers;
    }
}