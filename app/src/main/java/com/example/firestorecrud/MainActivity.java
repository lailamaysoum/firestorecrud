package com.example.firestorecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    //Views
    EditText mTitleEt, mDescriptionEt;
    Button mSaveBtn, mListView;

    //progress dialog
    ProgressDialog pd;

    //Firestore instance

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         //actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Data");

        //initialize views with its xml
        mTitleEt = findViewById(R.id.titleEt);
        mDescriptionEt = findViewById(R.id.descriptionEt);
        mSaveBtn = findViewById(R.id.saveBtn);
        mListView = findViewById(R.id.listBtn);
        //progress dialog
        pd = new ProgressDialog(this);
        //Firestore
        db = FirebaseFirestore.getInstance();

        //click button to upload data
        mSaveBtn.setOnClickListener( (v) -> {


                //input data
                String title = mTitleEt.getText().toString().trim();
                String description = mDescriptionEt.getText().toString().trim();
                //function call to upload data
               uploadData(title, description);
            });

        //click btn to start ListActivity
        mListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                finish();
            }
        });


    }


    private void uploadData(String title, String description) {
        //set title of progress bar
        pd.setTitle("Adding data to Firestore");
        //show progress bar when user click save button
        pd.show();
        //random id for each data to be stored
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);//id of data
        doc.put("title", title);
        doc.put("description",description);

        //add this data
        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       //this will be called when data is added successfully

                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // this will be called if there is any error while uploading

                        pd.dismiss();
                        // get and show error message
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}