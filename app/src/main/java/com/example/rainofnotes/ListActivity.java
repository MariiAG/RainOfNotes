package com.example.rainofnotes;

import android.os.Bundle;

import com.example.rainofnotes.adapters.NoteAdapter;
import com.example.rainofnotes.models.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends BaseActivity {

    ListView lv_list_listNote;
    FloatingActionButton fab_list_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        super.init();

        //actividad para crear nota
        fab_list_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreat();
            }
        });

        //evento listar
        lv_list_listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    protected void init(){
        fab_list_create = findViewById(R.id.fab_list_create);
        lv_list_listNote = findViewById(R.id.lv_list_listNote);
    }
    // clase para obtener las notas
    protected void getNote(){
        if (collectionReference != null){
            collectionReference.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (task.getResult() != null){
                                    modelArrayList= new ArrayList<>();
                                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                                        model = snapshot.toObject(NoteModel.class);
                                        modelArrayList.add(model);
                                    }
                                    if (modelArrayList.size() > 0){
                                        paintNota(modelArrayList);
                                    }else {
                                        makeSimpleAlertDialog("Alerta", "No se ha encontrado ninguna nota");
                                    }
                                }else {
                                    makeSimpleAlertDialog("Alerta", "No se ha encontrado la nota");
                                }
                            }else{
                                makeSimpleAlertDialog("Error", task.getException().getMessage());
                            }
                        }
                    });
        }else{
            makeSimpleToast("Error en la base se datos", 1);
        }
    }
    // Adaptador
    private void paintNota(ArrayList<NoteModel> modelArrayList) {
        adapter = new NoteAdapter(this, modelArrayList);
        lv_list_listNote.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNote();
    }
}