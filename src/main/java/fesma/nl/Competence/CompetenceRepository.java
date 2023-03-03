package fesma.nl.Competence;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class CompetenceRepository {
    private final Firestore db;

    public CompetenceRepository() {
        FirebaseApp.initializeApp();
        db = FirestoreClient.getFirestore();
    }

    public List<Competence> findAll() {
        ApiFuture<QuerySnapshot> queryFuture = collection().get();
        QuerySnapshot query = fromFuture(queryFuture);
        List<QueryDocumentSnapshot> documents = query.getDocuments();

        List<Competence> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            result.add(toCompetence(document));
        }
        return result;
    }

    public Competence save(Competence competence) {
        Map<String, Object> data = fromCompetence(competence);
        if (competence.getId() == null) {
            ApiFuture<DocumentReference> documentFuture = collection().add(data);
            DocumentReference document = fromFuture(documentFuture);
            competence.setId(document.getId());
        } else {
            DocumentReference document = collection().document(competence.getId());
            document.set(data);
        }
        return competence;
    }
    public Optional<Competence> findById(String id) {
        ApiFuture<DocumentSnapshot> documentFuture = collection().document(id).get();
        DocumentSnapshot snapshot = fromFuture(documentFuture);
        if (snapshot.exists()) {
            return Optional.of(toCompetence(snapshot));
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
        return db.collection("competencies");
    }

    private Competence toCompetence(DocumentSnapshot document) {
        Competence competence = new Competence(
                document.getString("competence"),
                document.getString("description"));
       competence.setId(document.getId());
        return competence;
    }

    private Map<String, Object> fromCompetence(Competence competence) {
        Map<String, Object> result = new HashMap<>();
        result.put("competence", competence.getCompetence());
        result.put("description", competence.getDescription());
        return result;
    }
}
