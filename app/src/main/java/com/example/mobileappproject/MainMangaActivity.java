package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMangaActivity extends BaseFunctionality {

    protected EditText mangaTitle_EditText;
    protected EditText mangakaName_EditText;
    protected EditText chaptersCount_EditText;
    protected EditText genre_EditText;

    protected Button addMangaBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manga);

        mangaTitle_EditText= findViewById(R.id.mtitle_Tbox);
        mangakaName_EditText=findViewById(R.id.mangakaName_Tbox);
        chaptersCount_EditText=findViewById(R.id.chaptersCount_Tbox);
        genre_EditText=findViewById(R.id.mgenreDescr_Tbox);

        addMangaBtn=findViewById(R.id.msubmit_Button);

        addMangaBtn.setOnClickListener(onClick);

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
                        "INSERT INTO MANGA(Title , Mangaka , Chapters , Genre) " +
                                "VALUES(?,?,?,?)",
                        new Object[]{
                                mangaTitle_EditText.getText().toString(),
                                mangakaName_EditText.getText().toString(),
                                chaptersCount_EditText.getText().toString(),
                                genre_EditText.getText().toString()
                        },
                        new OnSuccess() {
                            @Override
                            public void OnSuccessDo() {
                                Toast.makeText(getApplicationContext(), "Manga Added", Toast.LENGTH_LONG).show();
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
