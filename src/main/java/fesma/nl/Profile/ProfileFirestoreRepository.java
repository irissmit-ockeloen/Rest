package fesma.nl.Profile;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProfileFirestoreRepository {
    private Firestore db;

    public ProfileFirestoreRepository() {
        FirebaseApp.initializeApp();
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
            CollectionReference collectionReference = db.collection("profiles");
            Map<String, Object> data = fromProfile(profile);
            collectionReference.add(data);
            return profile;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Optional<Profile> findById(String id) {
        try {
            DocumentSnapshot data = db.collection("profiles").document(id).get().get();
            if (data.exists()) {
                return Optional.of(toProfile(data));
            } else {
                return Optional.empty();
            }
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
