package com.example.rainofnotes;

import android.os.Bundle;

import com.example.rainofnotes.models.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Notation;

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

        super.init();
        init();

        et_details_title.setEnabled(false);
        et_details_content.setEnabled(false);

        idFirebase = getIntent().getStringExtra("idFirebase");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        /*if (title != null && content != null){
            et_details_title.setText(title);
            et_details_content.setText(content);
            //makeSimpleAlertDialog("Acceso", "Nota" + model.getTitle());
        }else{
            makeSimpleAlertDialog("Error", "Nota vacía");
        }*/

        fab_details_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToList();
            }
        });

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
                if (idFirebase != null){
                    update(idFirebase);
                }else {
                    makeSimpleToast("No se estan recibiendo datos", 5);
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

    private void update(String idFirebase){
            documentReference = db.collection(COLLECTION_NAME).document(idFirebase);
            documentReference.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot snapshot = task.getResult();
                            model = snapshot.toObject(NoteModel.class);
                            model.setIdFbN(snapshot.getId());
                            if (model != null){
                                et_details_title.setText(model.getTitle());
                                et_details_content.setText(model.getContent());
                                makeSimpleToast("nota" + et_details_content.getText() + et_details_title.getText(), 2);
                                goToList();
                            }
                            else {
                                updateNota(model);
                            }
                        }
                    });
    }

    private void updateNota(NoteModel model){
        documentReference = db.collection(COLLECTION_NAME).document(model.getIdFbN());
        documentReference.set(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            makeSimpleToast("UPDATE correcto", 5);
                        }
                    }
                });
    }

}