package andre.com.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import andre.com.whatsapp.R;
import andre.com.whatsapp.helper.Preferencias;

public class ValidadorActivity extends AppCompatActivity {

    private EditText edtValidar;
    private Button btnValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        edtValidar = findViewById(R.id.edtValidar);
        btnValidar = findViewById(R.id.btnValidar);

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> user = preferencias.getDadosUser();

                String tokenGerado = user.get("token");
                String tokenDigitado = edtValidar.getText().toString();

                if (tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "Codigo Correto", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ValidadorActivity.this,"Codigo Invalido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
