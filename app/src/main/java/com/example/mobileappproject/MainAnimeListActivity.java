package com.example.mobileappproject;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainAnimeListActivity extends BaseFunctionality {

    protected Button addAnime_button;
    protected Button gotoMainMenu_btn;
    protected ListView anime_listView;

    protected void FillListView() throws Exception{
        final ArrayList<String> results=new ArrayList<>();
        SelectSQLAnime(
                "SELECT * FROM ANIME ORDER BY animeName;",
                null,
                new BaseFunctionality.OnSelectElement() {
                    @Override
                    public void OnElementIterate(String animeName, String studio, String episodeCount, String licensedBy, String animeGenre, String ID)
                    {
                        results.add(ID+"\t"+animeName+"\t"+studio+"\t"+episodeCount+"\t"+licensedBy+"\t"+animeGenre+"\n");
                    }
                }
        );
        anime_listView.clearChoices();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.activity_anime_list,
                R.id.textView,
                results
        );
        anime_listView.setAdapter(arrayAdapter);

    }

    @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            FillListView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anime_list);

        addAnime_button= findViewById(R.id.addAnime_Button);
        gotoMainMenu_btn=findViewById(R.id.goto_mainMenu_Btn);
        anime_listView = findViewById(R.id.anime_ListView);

        addAnime_button.setOnClickListener(onClick);
        gotoMainMenu_btn.setOnClickListener(onClick);

        anime_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView clickedText=view.findViewById(R.id.textView);
                String selected=clickedText.getText().toString();
                String[] elements=selected.split("\t");
                String ID=elements[0];
                String animeName=elements[1];
                String studio=elements[2];
                String episodeCount=elements[3];
                String licensedBy=elements[4];
                String animeGenre=elements[5];
                Intent intent=new Intent(MainAnimeListActivity.this, UpdateDeleteAnime.class);
                Bundle b=new Bundle();
                b.putString("ID", ID);
                b.putString("animeName", animeName);
                b.putString("studio", studio);
                b.putString("episodeCount", episodeCount);
                b.putString("licensedBy", licensedBy);
                b.putString("animeGenre", animeGenre);
                intent.putExtras(b);
                startActivityForResult(intent, 200, b);
            }
        });

        try {
            initDb();
            FillListView();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }

    }

    protected View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {

                case R.id.addAnime_Button:
                    startActivity(new Intent(MainAnimeListActivity.this , MainAnimeActivity.class));
                    break;

                case R.id.goto_mainMenu_Btn:
                    finish();
                    break;

            }
        }
    };

}