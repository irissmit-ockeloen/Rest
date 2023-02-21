package fesma.nl.Profile;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProfileFirestoreRepository {
    private final Firestore db;

    public ProfileFirestoreRepository() {
        FirebaseApp.initializeApp();
        db = FirestoreClient.getFirestore();
    }

    public List<Profile> findAll() {
        ApiFuture<QuerySnapshot> queryFuture = collection().get();
        QuerySnapshot query = fromFuture(queryFuture);
        List<QueryDocumentSnapshot> documents = query.getDocuments();

        List<Profile> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            result.add(toProfile(document));
        }
        return result;
    }

    public Profile save(Profile profile) {
        Map<String, Object> data = fromProfile(profile);
        if (profile.getId() == null) {
            ApiFuture<DocumentReference> documentFuture = collection().add(data);
            DocumentReference document = fromFuture(documentFuture);
            profile.setId(document.getId());
        } else {
            DocumentReference document = collection().document(profile.getId());
            document.set(data);
        }
        return profile;
    }

    public Optional<Profile> findById(String id) {
        ApiFuture<DocumentSnapshot> documentFuture = collection().document(id).get();
        DocumentSnapshot snapshot = fromFuture(documentFuture);
        if (snapshot.exists()) {
            return Optional.of(toProfile(snapshot));
        } else {
            return Optional.empty();
        }
    }

    public void deleteById(String id) {
        collection().document(id).delete();
    }

    private <T> T fromFuture(ApiFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private CollectionReference collection() {
        return db.collection("profiles");
    }

    private Profile toProfile(DocumentSnapshot document) {
        Profile profile = new Profile(
                document.getString("title"),
                document.getString("function"),
                document.getString("description"));
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
