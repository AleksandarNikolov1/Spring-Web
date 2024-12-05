package com.likebookapp.init;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.enums.EMood;
import com.likebookapp.repository.MoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBSeeder implements CommandLineRunner {

    private final MoodRepository moodRepository;

    public DBSeeder(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (moodRepository.count() == 0) {

            for (EMood value : EMood.values()) {
                Mood mood = new Mood();
                mood.setName(value);

                switch (value.name()) {
                    case "HAPPY":
                        mood.setDescription("Happy mood :)");
                        break;
                    case "SAD":
                        mood.setDescription("Sad mood :(");
                        break;
                    case "INSPIRED":
                        mood.setDescription("Inspired mood :]!");
                        break;
                }

                moodRepository.save(mood);
            }
        }
    }
}
