package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainMangaListActivity extends BaseFunctionality {

    protected Button addManga_but;
    protected Button gotoMM_btn;
    protected ListView manga_listView;

    protected void FillListView() throws Exception{
        final ArrayList<String> results=new ArrayList<>();
        SelectSQLManga("SELECT * FROM MANGA ORDER BY Title;", null, new BaseFunctionality.OnSelectElementManga()
        {
            @Override
            public void OnElementIterateManga(String title, String mangaka, String chaptersCount, String genre, String ID)
            {
                results.add(ID+"\t"+title+"\t"+mangaka+"\t"+chaptersCount+"\t"+genre+"\n");
            }
        });
        manga_listView.clearChoices();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.activity_manga_list,
                R.id.mangaTextView,
                results
        );
        manga_listView.setAdapter(arrayAdapter);

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
        setContentView(R.layout.activity_main_manga_list);

        addManga_but= findViewById(R.id.addManga_Button);
        gotoMM_btn = findViewById(R.id.goto_MM_from_mangalist);
        manga_listView = findViewById(R.id.manga_ListView);

        addManga_but.setOnClickListener(onClick);
        gotoMM_btn.setOnClickListener(gotoMM_onClick);

        try {
            initDb();
            FillListView();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    "DB broke",
                    Toast.LENGTH_LONG
            ).show();
        }

        manga_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView clickedText=view.findViewById(R.id.mangaTextView);
                String selected=clickedText.getText().toString();
                String[] elements=selected.split("\t");
                String ID=elements[0];
                String title=elements[1];
                String mangaka=elements[2];
                String chaptersCount=elements[3];
                String genre=elements[4];

                Intent intent=new Intent(MainMangaListActivity.this, UpdateDeleteManga.class);
                Bundle b=new Bundle();
                b.putString("ID", ID);
                b.putString("title", title);
                b.putString("mangaka", mangaka);
                b.putString("chaptersCount", chaptersCount);
                b.putString("genre", genre);
                intent.putExtras(b);
                startActivityForResult(intent, 200, b);
            }
        });
    }

    protected View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainMangaListActivity.this , MainMangaActivity.class));

        }
    };

    protected View.OnClickListener gotoMM_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}