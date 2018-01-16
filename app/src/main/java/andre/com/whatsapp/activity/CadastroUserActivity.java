package andre.com.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import andre.com.whatsapp.R;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.helper.Base64Custom;
import andre.com.whatsapp.helper.Preferencias;
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


                if (edtEmail.getText().toString().equals("") ||
                        edtNome.getText().toString().equals("") ||
                        edtSenha.getText().toString().equals(""))
                {
                    Toast.makeText(CadastroUserActivity.this,"Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    user = new User();
                    user.setNome(edtNome.getText().toString());
                    user.setEmail(edtEmail.getText().toString());
                    user.setSenha(edtSenha.getText().toString());
                    
                    cadastrarUser();
                }
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

                    //FirebaseUser userFirebase = task.getResult().getUser();

                    String identificadorUser = Base64Custom.codificarBase64(user.getEmail());
                    user.setId(identificadorUser);
                    user.salvar();

                    Preferencias preferencias = new Preferencias(CadastroUserActivity.this);
                    preferencias.salvarDados(identificadorUser, user.getNome());

                    //autenticacao.signOut();
                    //finish();

                    abrirLoginUser();

                }else{
                    String erro ="";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha mais forte.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro += "Email Invalido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Email ja cadastrado.";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar o Usuario.";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUserActivity.this,erro, Toast.LENGTH_SHORT).show();
                    Log.i("erro", "Aconteceu algum erro ao cadastrar");
                }
            }
        });

    }

    private void abrirLoginUser() {
        Intent intent = new Intent(CadastroUserActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
