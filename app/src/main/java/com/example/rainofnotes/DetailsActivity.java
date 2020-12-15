package com.example.rainofnotes;

import android.os.Bundle;

import com.example.rainofnotes.models.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Notation;

import java.util.ArrayList;

public class DetailsActivity extends BaseActivity {

    private FloatingActionButton fab_details_list;
    private TextView et_details_title, et_details_content;
    private Button btn_details_edit, btn_details_update;
    private static String title, content, idFirebase;

    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();

        super.init();
        init();

        et_details_title.setEnabled(false);
        et_details_content.setEnabled(false);

        final String id = getIntent().getStringExtra("idFirebase");
        if(id != null){
            et_details_title.setText(id);
            update(id);
        }else{
            et_details_title.setText("La nota esta vacia");
        }

        btn_details_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_details_title.setEnabled(true);
                et_details_content.setEnabled(true);
            }
        });

        btn_details_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TitleNew = et_details_title.getText().toString();
                String ContentNew = et_details_content.getText().toString();
                if (!TitleNew.equals("") && !ContentNew.equals("")){
                    if (id != null){
                        model = new NoteModel(TitleNew, ContentNew);
                        documentReference.set(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        makeSimpleToast("Elemento guardado satisfactoriamente", 1);
                                        goToList();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        makeSimpleToast("Debe completar todos los campos", 1);
                                    }
                                });
                    }
                }
            }
        });

    }
    protected void init(){
        fab_details_list = findViewById(R.id.fab_details_list);
        et_details_title = findViewById(R.id.et_details_title);
        et_details_content = findViewById(R.id.et_details_content);
        btn_details_edit = findViewById(R.id.btn_details_edit);
        btn_details_update = findViewById(R.id.btn_details_update);
    }
    private void update(String id){
        documentReference = db.collection(COLLECTION_NAME).document(id);
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            model = document.toObject(NoteModel.class);
                            model.setIdFbN(document.getId());
                            if(model != null){
                                et_details_title.setText(model.getTitle());
                                et_details_content.setText(model.getContent());
                            }
                        }
                    }
                });
    }

}