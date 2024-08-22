package org.example.emailsender.init;

import lombok.RequiredArgsConstructor;
import org.example.emailsender.entity.Influencer;
import org.example.emailsender.reader.CsvReader;
import org.example.emailsender.repository.InfluencerRepository;
import org.example.emailsender.service.InfluencerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class InitialCsvReading implements CommandLineRunner {
    private final CsvReader csvReader;
    private final InfluencerRepository influencerRepository;
    private static final String PATH = "C:/Users/user/Downloads/influencers-100k+";
    private final InfluencerService influencerService;

    @Override
    public void run(String... args) {
        /*File directory = new File(PATH); // replace with your directory path
        String[] courseNames = new String[15];
        courseNames[0] = "mindfulnessMasteryPro";
        courseNames[1] = "equilibrium";
        courseNames[2] = "familySuccessFormula";
        courseNames[3] = "fearlessOfSocialMedia";
        courseNames[4] = "joyfulJourney";
        courseNames[5] = "habitsOfSuccess";
        courseNames[6] = "timeMastery";
        courseNames[7] = "charismaticConversation";
        courseNames[8] = "theConfidentLeader";
        courseNames[9] = "masteringLearning";
        courseNames[10] = "theConfidentWoman";
        courseNames[11] = "balancedBeginnings";
        courseNames[12] = "fromDoubtToDaring";
        courseNames[13] = "rizzAcademy";
        courseNames[14] = "theGentlemansCode";

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    List<Influencer> influencers = csvReader.readCsvFile(file.getPath());
                    String courseName = "";
                    for (String course : courseNames){
                        if (file.getName().contains(course)){
                            courseName = course;
                            break;
                        }
                    }
                    influencerRepository.deleteByCourseName(courseName);
                    for (Influencer influencer : influencers) {
                        influencer.setCourseName(courseName);
                        if (influencer.getEmail().endsWith(".com") &&
                                influencer.getEmail().contains("@") &&
                                influencer.getEmail().matches("^[\\x00-\\x7F]*$")) {
                                influencerRepository.save(influencer);
                        }
                    }
                }
            } else {
                System.out.println("The directory is empty or cannot be read.");
            }
        } else {
            System.out.println("The provided path is not a directory.");
        }*/
        List<Influencer> influencers = csvReader.readCsvFile("C:/Users/user/Downloads/influencers-random/course 1.csv");
        influencerRepository.deleteByCourseName("mindfulnessMastery");
        for (Influencer influencer : influencers) {
            influencer.setCourseName("mindfulnessMastery");
            if (influencer.getEmail().endsWith(".com") &&
                    influencer.getEmail().contains("@") &&
                    influencer.getEmail().matches("^[\\x00-\\x7F]*$")) {
                    influencerRepository.save(influencer);
            }
        }

    }
}
