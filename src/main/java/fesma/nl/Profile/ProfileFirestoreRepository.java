package fesma.nl.Profile;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProfileFirestoreRepository {
    private Firestore db;

    public ProfileFirestoreRepository() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\DenizvanIerselFesma\\profile-1c1bc-firebase-adminsdk-rtg1z-6b1530fe7f.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    public List<Profile> findAll() {
        ApiFuture<QuerySnapshot> query = db.collection("profiles").get();

        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        List<Profile> result = new ArrayList<Profile>();
        for (QueryDocumentSnapshot document : documents) {
            result.add(toProfile(document));
        }
        return result;
    }

    public Profile save(Profile profile) {
        try {
            Map<String, Object> data = fromProfile(profile);
            if (profile.getId() == null) {
                CollectionReference collectionReference = db.collection("profiles");
                collectionReference.add(data);
            }
            else {
                DocumentReference collectionReference = db.collection("profiles").document(profile.getId());
                collectionReference.set(data);
            }
            return profile;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Optional<Profile> findById(String id) {
        try {
            DocumentSnapshot data = db.collection("profiles").document(id).get().get();
            return Optional.of(toProfile(data));
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }

    public void deleteById(String id) {
        db.collection("profiles").document(id).delete();
    }
    private Profile toProfile(DocumentSnapshot document) {
        Profile profile = new Profile(document.getString("title"), document.getString("function"), document.getString("description"));
        profile.setId(document.getId());
        return profile;
    }

    private Map<String, Object> fromProfile(Profile profile) {
        Map<String, Object> result = new HashMap<>();
        result.put("title", profile.getTitle());
        result.put("function", profile.getFunction());
        result.put("description", profile.getDescription());
        return result;
    }
}

