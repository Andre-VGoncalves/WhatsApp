package andre.com.whatsapp.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import andre.com.whatsapp.R;
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void abrirCadastroUser (View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUserActivity.class);
        startActivity(intent);

    }
}
