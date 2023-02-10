package fesma.nl.Firebase;
import com.google.firebase.FirebaseApp;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;


@Service
public class FireBaseInitializer {
    @PostConstruct
    public void initialize() throws FileNotFoundException {
        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\DenizvanIerselFesma\\profile-1c1bc-firebase-adminsdk-rtg1z-6b1530fe7f.json");

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FirebaseApp.initializeApp(options);
    }
}
