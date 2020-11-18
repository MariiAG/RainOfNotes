package com.example.rainofnotes.connection;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConnection {

    private static FirebaseAuth mAuth;


    private static FirebaseStorage mFirebaseStorage;


    private static FirebaseFirestore db;


    public static FirebaseAuth ConnectionAuth(){
        return mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseStorage ConnectionFirebaseStorage(){
        return mFirebaseStorage = FirebaseStorage.getInstance();
    }

    public static FirebaseFirestore ConnectionFirestore() {
        return db = FirebaseFirestore.getInstance();
    }
}
