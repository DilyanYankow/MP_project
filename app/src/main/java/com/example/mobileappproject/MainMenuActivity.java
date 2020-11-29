package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends BaseFunctionality {

    Button animeList_button;
    Button addAnime_button;
    Button addManga_button;
    Button mangaList_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addAnime_button = findViewById(R.id.addAnime_Button);
        animeList_button = findViewById(R.id.animeList_Button);
        addManga_button = findViewById(R.id.addManga_Button);
        mangaList_button = findViewById(R.id.mangaList_Button);

        addAnime_button.setOnClickListener(onClick);
        animeList_button.setOnClickListener(onClick);
        mangaList_button.setOnClickListener(onClick);
        addManga_button.setOnClickListener(onClick);

        try {
            initDb();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext() , "DB broke" , Toast.LENGTH_LONG).show();
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;

            switch(v.getId())
            {
                case R.id.addAnime_Button:
                    intent = new Intent(MainMenuActivity.this , MainAnimeActivity.class);
                break;

                case R.id.animeList_Button:
                    intent = new Intent(MainMenuActivity.this , MainAnimeListActivity.class);
                break;
                case  R.id.mangaList_Button:
                    intent = new Intent(MainMenuActivity.this, MainMangaListActivity.class);
                    break;
                case R.id.addManga_Button:
                    intent = new Intent(MainMenuActivity.this, MainMangaActivity.class);
                    break;

                default:
                    intent = new Intent();
                break;
            }

            startActivity(intent);
        }
    };

}