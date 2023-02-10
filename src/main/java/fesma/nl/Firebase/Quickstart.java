package fesma.nl.Firebase;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class Quickstart implements ProfileRepository {
    private Firestore db;


    public Quickstart() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\DenizvanIerselFesma\\profile-1c1bc-firebase-adminsdk-rtg1z-6b1530fe7f.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    public static void main(String[] args) throws Exception {
        Quickstart quickStart = new Quickstart();
        quickStart.run();
        quickStart.close();
    }

    private void run() throws Exception {
        for (Profile profile : findAll()) {
            deleteById(profile.getId());
        }

        Profile profile1 = new Profile("Java developer English", "Java developer", "As a java developer Deniz was responsible for creating the Java backend for the CV app.");
        Profile profile2 = new Profile("Java developer Dutch", "Java developer", "As a java developer James was responsible for creating the Java backend for the CV app.");
        Profile profile3 = new Profile("Java developer Dutch", "Java developer", "As a java developer Iris was responsible for creating the Java backend for the CV app.");
        save(profile1);
        save(profile2);
        save(profile3);

        System.out.println("######### findAll: ");
        List<Profile> profiles = findAll();
        for (Profile profile : profiles) {
            System.out.println(profile);
        }

        System.out.println("######### findById 0: ");
        Optional<Profile> profile = findById(profiles.get(0).getId());
        System.out.println(profile);

        System.out.println("######### findById 1: ");
        deleteById(profiles.get(1).getId());
    }

    private void close() throws Exception {
        db.close();
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

    @Override
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

    @Override
    public Profile save(Profile profile) {
        try {
            CollectionReference collectionReference = db.collection("profiles");
            Map<String, Object> data = fromProfile(profile);
            collectionReference.add(data);
            return profile;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Profile> findById(String id) {
        try {
            DocumentSnapshot data = db.collection("profiles").document(id).get().get();
            return Optional.of(toProfile(data));
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(String id) {
        db.collection("profiles").document(id).delete();
    }
}

