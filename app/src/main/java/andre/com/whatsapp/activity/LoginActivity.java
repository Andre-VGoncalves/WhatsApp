package andre.com.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Random;

import andre.com.whatsapp.R;
import andre.com.whatsapp.helper.Preferencias;
import andre.com.whatsapp.mascaras.MascaraCadastrar;

public class LoginActivity extends AppCompatActivity {

    private EditText edtCelular;
    private EditText edtNome;
    private EditText edtCodPais;
    private EditText edtCodArea;
    private Button btnCadastrar;
    private MascaraCadastrar mascaraCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtCelular   = findViewById(R.id.edtCelular);
        edtNome      = findViewById(R.id.edtNome);
        edtCodArea   = findViewById(R.id.edtCodArea);
        edtCodPais   = findViewById(R.id.edtCodPais);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        mascaraCadastrar = new MascaraCadastrar(edtCodPais,edtCodArea,edtCelular);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeUser = edtNome.getText().toString();
                String telefoneCompleto =
                        edtCodPais.getText().toString() +
                        edtCodArea.getText().toString() +
                        edtCelular.getText().toString();

                telefoneCompleto = telefoneCompleto.replace("+", "");
                telefoneCompleto = telefoneCompleto.replace("-","");

                Random randomico = new Random();

                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;

                String token = String.valueOf(numeroRandomico);
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.saveUserPreferencias(nomeUser,telefoneCompleto,token);

                /*
                HashMap<String, String> user = preferencias.getDadosUser();

                Log.i("Token", user.get("token") + " Fone " + user.get("telefone") + " Nome " + user.get("nome"));
                */

                //Envio SMS
                boolean enviandoSMS = enviaSMS("+" + telefoneCompleto, token);



            }
        });
    }

    private boolean enviaSMS(String telefone, String mensagem){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagem,null,null);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
