package andre.com.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import andre.com.whatsapp.R;
import andre.com.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private String nomeUserDest;
    private ImageButton btnEnviar;
    private EditText edtMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.tbConversa);
        btnEnviar = findViewById(R.id.btnEnviar);
        edtMensagem = findViewById(R.id.edtMensagem);

        Bundle extra = getIntent().getExtras();

        if(extra != null ){
            nomeUserDest = extra.getString("nome");
        }

        toolbar.setTitle(nomeUserDest);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtMensagem = edtMensagem.getText().toString();

                if (txtMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this,"Digite um texto", Toast.LENGTH_SHORT).show();
                }else{

                    Mensagem mensagem = new Mensagem();

                }
            }
        });
    }
}
