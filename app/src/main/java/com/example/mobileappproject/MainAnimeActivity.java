package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;

import java.util.ArrayList;

public class MainAnimeActivity extends BaseFunctionality {

    protected EditText title_Tbox;
    protected EditText studio_Tbox;
    protected EditText episodeCount_Tbox;
    protected EditText licensedBy_Tbox;
    protected EditText genre_Tbox;

    protected Button addAnime_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_Tbox= findViewById(R.id.title_Tbox);
        studio_Tbox=findViewById(R.id.studioName_Tbox);
        episodeCount_Tbox=findViewById(R.id.episodeCount_Tbox);
        licensedBy_Tbox=findViewById(R.id.licensedBy_Tbox);
        genre_Tbox=findViewById(R.id.genreDescr_Tbox);
        addAnime_button=findViewById(R.id.submit_Button);

        addAnime_button.setOnClickListener(onClick);

        try {
          initDb();
        }catch (Exception e){
            Toast.makeText(getApplicationContext() , "DB broke" , Toast.LENGTH_LONG).show();
        }

    }

    protected View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        try {
            ExecSQL(
                    "INSERT INTO ANIME(animeName ,studio ,episodeCount ,licensedBy ,animeGenre) " +
                            "VALUES(?,?,?,?,?)",
                    new Object[]{
                            title_Tbox.getText().toString(),
                            studio_Tbox.getText().toString(),
                            episodeCount_Tbox.getText().toString(),
                            licensedBy_Tbox.getText().toString(),
                            genre_Tbox.getText().toString()
                    },
                    new OnSuccess() {
                        @Override
                        public void OnSuccessDo() {
                            Toast.makeText(getApplicationContext(), "Anime Added", Toast.LENGTH_LONG).show();
                        }
                    }
            );

            finish();

        }
        catch (Exception e)
            {
            Toast.makeText(getApplicationContext() , e.getMessage() , Toast.LENGTH_LONG).show();
            }

        }
    };

}