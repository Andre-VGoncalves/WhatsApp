package andre.com.whatsapp.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import andre.com.whatsapp.R;
import andre.com.whatsapp.config.ConfigFirebase;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference referenceFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referenceFirebase = ConfigFirebase.getFirebase();

    }

    public void abrirCadastroUser (View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUserActivity.class);
        startActivity(intent);

    }
}
