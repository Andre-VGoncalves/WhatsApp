package andre.com.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import andre.com.whatsapp.R;
import andre.com.whatsapp.activity.mascaras.MascaraCadastrar;

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



    }
}
