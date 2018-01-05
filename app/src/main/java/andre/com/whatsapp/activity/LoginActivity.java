package andre.com.whatsapp.activity;


import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;

import andre.com.whatsapp.R;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;
    private User user;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificaUserLogado();

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtEmail.getText().toString().equals("") || edtSenha.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Digite todos os campos", Toast.LENGTH_SHORT).show();
                }else {
                    user = new User();

                    user.setEmail(edtEmail.getText().toString());
                    user.setSenha(edtSenha.getText().toString());

                    validarLogin();
                }


            }
        });


    }

    private void verificaUserLogado() {

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abriTelaPrincipal();
        }
    }

    private void validarLogin() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                user.getEmail(),
                user.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String erro ="";
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Bem Vindo", Toast.LENGTH_SHORT).show();
                    abriTelaPrincipal();
                }else{
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        erro = "Email Invalido";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Senha Invalida";
                    } catch (Exception e) {
                        erro = "Erro ao realizar o login";
                        Log.i("erro", "Ocorreu um erro");
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, erro, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void abriTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUser (View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUserActivity.class);
        startActivity(intent);

    }
}
