package andre.com.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import andre.com.whatsapp.R;
import andre.com.whatsapp.model.User;

public class CadastroUserActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtEmail;
    EditText edtSenha;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_user);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
    }

    public void cadastroUser (View view){

        user = new User();
        user.setNome(edtNome.getText().toString());
        user.setEmail(edtEmail.getText().toString());
        user.setSenha(edtSenha.getText().toString());

        cadastrarUser();


    }

    private void cadastrarUser() {

    }
}
