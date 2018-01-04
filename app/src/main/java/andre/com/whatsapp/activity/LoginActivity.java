package andre.com.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

import andre.com.whatsapp.R;
import andre.com.whatsapp.helper.Permisao;
import andre.com.whatsapp.helper.Preferencias;
import andre.com.whatsapp.mascaras.MascaraCadastrar;

public class LoginActivity extends AppCompatActivity {

    private EditText edtCelular;
    private EditText edtNome;
    private EditText edtCodPais;
    private EditText edtCodArea;
    private Button btnCadastrar;
    private MascaraCadastrar mascaraCadastrar;
    private String[] permissoes = new String[]{
            Manifest.permission.SEND_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permisao.validarPermissoes(1,this, permissoes);

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
                boolean enviandoSMS = enviaSMS("+" + telefoneCompleto,"Bem vindo ao Dede Zap digite o Codigo para  " + token);

                if(enviandoSMS){

                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(LoginActivity.this,"Problema ao enviar tente novamente!!!", Toast.LENGTH_SHORT).show();
                }



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


    public void onRequestPermissionsResult(int requestCode, String[] permissons, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissons, grantResults);

        for(int result : grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermisao();
            }

        }

    }

    private void alertaValidacaoPermisao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissoes negadas");
        builder.setMessage("Para utilizar o APP é preciso aceitar as permissoes");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
