package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseFunctionality {

    protected EditText username_reg_ET;
    protected EditText password_reg_ET;
    Button apply_reg_Btn;
    Button goto_logIn_Btn;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_reg_ET=findViewById(R.id.username_reg_Tbox);
        password_reg_ET=findViewById(R.id.password_reg_Tbox);

        apply_reg_Btn=findViewById(R.id.create_acc_Btn);
        goto_logIn_Btn=findViewById(R.id.goto_Lo);

        apply_reg_Btn.setOnClickListener(onClick);
        goto_logIn_Btn.setOnClickListener(goTo_LogIn_onClick);


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
                        "INSERT INTO User(username , password)" +
                                "VALUES(?,?)",
                        new Object[]{
                                username_reg_ET.getText().toString(),
                                password_reg_ET.getText().toString()
                        },
                        new OnSuccess() {
                            @Override
                            public void OnSuccessDo() {
                                Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_LONG).show();
                            }
                        }
                );

                startActivity(new Intent(getApplicationContext() , LogInActivity.class));
                finish();

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext() , e.getMessage() , Toast.LENGTH_LONG).show();
            }

        }
    };
    protected View.OnClickListener goTo_LogIn_onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext() , LogInActivity.class));
            finish();
        }
    };
}
