package com.example.mobileappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDeleteAnime extends BaseFunctionality {

    protected EditText animeName_ET;
    protected EditText studio_ET;
    protected EditText episodeCount_ET;
    protected EditText licensedBy_ET;
    protected EditText aniemGenre_ET;
    protected String ID;

    protected Button update_button;
    protected Button delete_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_anime);

        animeName_ET=findViewById(R.id.title_Tbox);
        studio_ET=findViewById(R.id.studioName_Tbox);
        episodeCount_ET=findViewById(R.id.episodeCount_Tbox);
        licensedBy_ET=findViewById(R.id.licensedBy_Tbox);
        aniemGenre_ET=findViewById(R.id.genreDescr_Tbox);

        update_button= findViewById(R.id.edit_Button);
        delete_button= findViewById(R.id.delete_Button);
        Bundle b =getIntent().getExtras();

        if(b!=null)
        {
            ID = b.getString("ID");
            animeName_ET.setText(b.getString("animeName"));
            studio_ET.setText(b.getString("studio"));
            episodeCount_ET.setText(b.getString("episodeCount"));
            licensedBy_ET.setText(b.getString("licensedBy"));
            aniemGenre_ET.setText(b.getString("animeGenre"));
        }

        update_button.setOnClickListener(onClick);
        delete_button.setOnClickListener(onClick);

    }

    protected View.OnClickListener onClick =new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.edit_Button:
                    try {
                        ExecSQL(
                                "UPDATE ANIME SET animeName=? , studio=? , episodeCount=? ,licensedBy=? ,animeGenre=?" +
                                        "WHERE ID=?",
                                new Object[]{
                                        animeName_ET.getText().toString(),
                                        studio_ET.getText().toString(),
                                        episodeCount_ET.getText().toString(),
                                        licensedBy_ET.getText().toString(),
                                        aniemGenre_ET.getText().toString(),
                                        ID
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

                case R.id.delete_Button:

                    try {
                        ExecSQL(
                                "DELETE FROM ANIME " +
                                        "WHERE ID=?",
                                new Object[]{
                                        ID
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