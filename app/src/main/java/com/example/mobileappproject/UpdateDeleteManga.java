package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UpdateDeleteManga extends BaseFunctionality {

    protected EditText mangaTitle_EditText;
    protected EditText mangakaName_EditText;
    protected EditText chaptersCount_EditText;
    protected EditText genre_EditText;
    protected String Id;
    protected Button edit_B;
    protected Button delete_B;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_manga);

        mangaTitle_EditText = findViewById(R.id.mangaTitle_TB);
        mangakaName_EditText = findViewById(R.id.mangakaName_TB);
        chaptersCount_EditText = findViewById(R.id.chaptersCount_TB);
        genre_EditText = findViewById(R.id.genre_TB);

        edit_B = findViewById(R.id.edit_Btn);
        delete_B = findViewById(R.id.delete_Btn);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            Id = b.getString("ID");
            mangaTitle_EditText.setText(b.getString("title"));
            mangakaName_EditText.setText(b.getString("mangaka"));
            chaptersCount_EditText.setText(b.getString("chaptersCount"));
            genre_EditText.setText(b.getString("genre"));
        }

        edit_B.setOnClickListener(clickBtn);
        delete_B.setOnClickListener(clickBtn);
    }


    protected View.OnClickListener clickBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.edit_Btn:
                    try {
                        ExecSQL(
                                "UPDATE MANGA SET Title= ? , Mangaka= ? , Chapters= ? , Genre= ?" +
                                        "WHERE ID= ? ",
                                new Object[]{
                                        mangaTitle_EditText.getText().toString(),
                                        mangakaName_EditText.getText().toString(),
                                        chaptersCount_EditText.getText().toString(),
                                        genre_EditText.getText().toString(),
                                        Id
                                },
                                new OnSuccess() {
                                    @Override
                                    public void OnSuccessDo() {
                                        Toast.makeText(getApplicationContext(),"Update Succesful",Toast.LENGTH_LONG).show();
                                    }
                                }
                        );

                        finishActivity(200);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Update Failed",Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.delete_Btn:

                    try {
                        ExecSQL(
                                "DELETE FROM MANGA " +
                                        "WHERE ID= ? ",
                                new Object[]{
                                        Id
                                },
                                new OnSuccess() {
                                    @Override
                                    public void OnSuccessDo() {
                                        Toast.makeText(getApplicationContext(),"Delete Succesful", Toast.LENGTH_LONG).show();
                                    }
                                }
                        );

                        finishActivity(200);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Delete Failed",Toast.LENGTH_LONG).show();
                    }
                    break;
            }

        }
    };
}