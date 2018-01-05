package andre.com.whatsapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import andre.com.whatsapp.R;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.model.User;

public class CadastroUserActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastrar;
    private User user;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_user);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User();
                user.setNome(edtNome.getText().toString());
                user.setEmail(edtEmail.getText().toString());
                user.setSenha(edtSenha.getText().toString());

                cadastrarUser();
            }
        });
    }

    private void cadastrarUser() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroUserActivity.this,"Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    Log.i("sucesso", "Cadastrado");

                    FirebaseUser userFirebase = task.getResult().getUser();
                    user.setId(userFirebase.getUid());
                    user.salvar();

                }else{
                    Toast.makeText(CadastroUserActivity.this,"Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                    Log.i("erro", "Aconteceu algum erro ao cadastrar");
                }
            }
        });

    }
}
