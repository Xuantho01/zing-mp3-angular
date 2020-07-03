package com.bolero.boleroteam.service.impl;

import com.bolero.boleroteam.model.Style;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
//service firebase
@Service
public class FirebaseServiceImpl {
    public String saveStyle(Style style) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("style").document(style.getName()).set(style);
        return collectionApiFuture.get().getUpdateTime().toString();
    }
}
