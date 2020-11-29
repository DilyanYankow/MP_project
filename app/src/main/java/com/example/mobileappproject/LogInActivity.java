package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends BaseFunctionality{


    protected EditText username_login_ET;
    protected EditText password_login_ET;
    Button apply_login_Btn;
    Button goto_register_Btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username_login_ET = findViewById(R.id.username_login_Tbox);
        password_login_ET = findViewById(R.id.password_login_Tbox);
        apply_login_Btn = findViewById(R.id.login_Btn);
        goto_register_Btn = findViewById(R.id.login_to_create_acc_Btn);

        apply_login_Btn.setOnClickListener(onClick);
        goto_register_Btn.setOnClickListener(goto_register_onClick);

        try {
            initDb();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext() , "DB broke" , Toast.LENGTH_LONG).show();
        }

    }

    protected View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                if (checkLogin(username_login_ET.getText().toString(), password_login_ET.getText().toString()))
                {
                    startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                    finish();
                }
                else
                {
                 Toast.makeText(getApplicationContext(),"Wrong login info", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext() , e.getMessage() , Toast.LENGTH_LONG).show();
            }


        }
    };

    protected View.OnClickListener goto_register_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    };

}
