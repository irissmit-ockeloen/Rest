package fesma.nl.Firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quickstart {

    private final Firestore db;

    public Quickstart() {
        FirebaseApp.initializeApp();
        db = FirestoreClient.getFirestore();
    }

    public static void main(String[] args) throws Exception {
        Quickstart quickStart = new Quickstart();
        quickStart.run();
        quickStart.close();
    }
    private void run() throws Exception {
        // Adding document 1
        System.out.println("########## Adding document 1 ##########");
        addAlovelace();

        // Adding document 2
        System.out.println("########## Adding document 2 ##########");
        addAturing();

        // Adding document 3
        System.out.println("########## Adding document 3 ##########");
        addCbabbage();

        // retrieve all users born before 1900
        System.out.println("########## users born before 1900 ##########");
        retrieveUsersBornBefore1900();

        // retrieve all users
        System.out.println("########## All users ##########");
        retrieveAllDocuments();
        System.out.println("###################################");
    }

    private void close() throws Exception {
        db.close();
    }

    private void addAlovelace() throws Exception {
        DocumentReference docRef = db.collection("users").document("alovelace");

        Map<String, Object> data = new HashMap<>();
        data.put("first", "Ada");
        data.put("last", "Lovelace");
        data.put("born", 1815);

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    private void addAturing() throws Exception {
        DocumentReference docRef = db.collection("users").document("aturing");

        Map<String, Object> data = new HashMap<>();
        data.put("first", "Alan");
        data.put("middle", "Mathison");
        data.put("last", "Turing");
        data.put("born", 1912);

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    private void addCbabbage() throws Exception {
        DocumentReference docRef = db.collection("users").document("cbabbage");

        Map<String, Object> data =
                new ImmutableMap.Builder<String, Object>()
                        .put("first", "Charles")
                        .put("last", "Babbage")
                        .put("born", 1791)
                        .build();

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    private void retrieveUsersBornBefore1900() throws Exception {
        ApiFuture<QuerySnapshot> query =
                db.collection("users").whereLessThan("born", 1900).get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        printDocuments(documents);
    }

    private void retrieveAllDocuments() throws Exception {
        ApiFuture<QuerySnapshot> query = db.collection("users").get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        printDocuments(documents);
    }

    private void printDocuments(List<QueryDocumentSnapshot> documents) {
        for (QueryDocumentSnapshot document : documents) {
            printDocument(document);
        }
    }

    private void printDocument(QueryDocumentSnapshot document) {
        System.out.println("User: " + document.getId());
        System.out.println("First: " + document.getString("first"));
        if (document.contains("middle")) {
            System.out.println("Middle: " + document.getString("middle"));
        }
        System.out.println("Last: " + document.getString("last"));
        System.out.println("Born: " + document.getLong("born"));
    }
}