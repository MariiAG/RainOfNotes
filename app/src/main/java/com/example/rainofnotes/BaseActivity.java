package com.example.rainofnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rainofnotes.adapters.NoteAdapter;
import com.example.rainofnotes.connection.FirebaseConnection;
import com.example.rainofnotes.models.NoteModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    //clases
    protected NoteModel model;
    protected ArrayList<NoteModel> modelArrayList;
    protected NoteAdapter adapter;
    //conexiones
    protected FirebaseAuth mAuth;
    protected FirebaseStorage mFirebaseStorage;
    protected FirebaseFirestore db;
    //consultas
    protected Query query;
    protected CollectionReference collectionReference;
    protected StorageReference mStorageReference, fileReference;

    protected final String COLLECTION_NAME = "notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init(){
        model = new NoteModel();
        db = FirebaseConnection.ConnectionFirestore();
        mAuth = FirebaseConnection.ConnectionAuth();
        mFirebaseStorage = FirebaseConnection.ConnectionFirebaseStorage();
        collectionReference = db.collection(COLLECTION_NAME);
    }

    // Mensaje 
    protected void makeSimpleToast(String text, int duration){
        Toast.makeText(this, text, duration).show();
    }

    // Mensaje de alerta
    protected void makeSimpleAlertDialog(String title, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text);
        builder.setTitle(title);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void goToList(){
        Intent change = new Intent(this, ListActivity.class);
        startActivity(change);
    }

    protected void goToCreat(){
        Intent change = new Intent(this, CreateActivity.class);
        startActivity(change);
    }

    protected void goToEdit(){
        Intent change = new Intent(this, BaseActivity.class);
        startActivity(change);
    }

    protected void goToSearch(){
        Intent change = new Intent(this, BaseActivity.class);
        startActivity(change);
    }
}