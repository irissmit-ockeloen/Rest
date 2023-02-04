package fesma.nl.Profile;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

public class Firebase {
    public static void main(String[] args) {
        FirebaseApp.initializeApp();

        Firestore db = FirestoreClient.getFirestore();
    }
}
