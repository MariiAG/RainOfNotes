package com.example.rainofnotes;

import android.os.Bundle;

import com.example.rainofnotes.models.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateActivity extends BaseActivity {

    FloatingActionButton fab_create_save, fab_create_clean, fab_create_cancel;
    ImageView iv_create_image;
    EditText et_create_content, et_create_title;
    TextView tv_create_clic_image_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.init();
        init();

        fab_create_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, content;

                title = et_create_title.getText().toString();
                content = et_create_content.getText().toString();

                if (title.isEmpty() || content.isEmpty()){
                    makeSimpleAlertDialog("Informacion", "Por favor complete todos los campos");
                }else{
                    model = new NoteModel();
                    model.setTitle(title);
                    model.setContent(content);

                    save(model);
                }
            }
        });

        fab_create_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        fab_create_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToList();
            }
        });
    }

    private void save(NoteModel model) {
        if (collectionReference != null){
            collectionReference.add(model)
            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()){
                        if (task.getResult() != null){
                            makeSimpleAlertDialog("Info", "Nota guardada correctamente");
                            clear();
                        }else {
                            makeSimpleAlertDialog("Error", "No se ha podido guardar la nota");
                        }
                    }
                    else {
                        makeSimpleAlertDialog("Error", task.getException().getMessage());
                    }
                }
            });
        }else {
            makeSimpleAlertDialog("Error", "No hay conexion a la base de datos");
        }
    }

    protected void init(){
        fab_create_cancel = findViewById(R.id.fab_create_cancel);
        fab_create_clean = findViewById(R.id.fab_create_clean);
        fab_create_save = findViewById(R.id.fab_create_save);
        iv_create_image = findViewById(R.id.iv_create_image);
        tv_create_clic_image_new = findViewById(R.id.tv_note_list_item_content);
        et_create_content = findViewById(R.id.et_create_content);
        et_create_title = findViewById(R.id.et_create_title);

    }

    protected void clear(){
        et_create_title.setText("");
        et_create_content.setText("");

        et_create_title.requestFocus();

        iv_create_image.setImageResource(R.drawable.ic_history_edu_black_18dp);
    }
}