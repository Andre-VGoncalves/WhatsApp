package andre.com.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import andre.com.whatsapp.Adapter.MensagemAdapter;
import andre.com.whatsapp.R;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.helper.Base64Custom;
import andre.com.whatsapp.helper.Preferencias;
import andre.com.whatsapp.model.Conversa;
import andre.com.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private ImageButton btnEnviar;
    private EditText edtMensagem;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;

    //dados do destinatario
    private String nomeUserDest;
    private String idUserDest;
    //dados do remetente
    private String idUserRemetente;
    private String nomeUsuarioRementente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.tbConversa);
        btnEnviar = findViewById(R.id.btnEnviar);
        edtMensagem = findViewById(R.id.edtMensagem);
        listView = findViewById(R.id.lvConversas);

        //dados do usuario logaod
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUserRemetente = preferencias.getIdentificador();
        nomeUsuarioRementente = preferencias.getNome();


        Bundle extra = getIntent().getExtras();

        if(extra != null ){
            nomeUserDest = extra.getString("nome");
            String emailDest = extra.getString("email");
            idUserDest = Base64Custom.codificarBase64(emailDest);
        }

        toolbar.setTitle(nomeUserDest);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //montar a lista e adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listView.setAdapter(adapter);

        //recuperar a mensagem do firebase
        firebase = ConfigFirebase.getFirebase()
                .child("mensagens")
                .child(idUserRemetente)
                .child(idUserDest);

        // criar um listener mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerMensagem);


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtMensagem = edtMensagem.getText().toString();

                if (txtMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this,"Digite um texto", Toast.LENGTH_SHORT).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUser(idUserRemetente);
                    mensagem.setMenssagem(txtMensagem);

                    //remetente
                    boolean retornoMsgRemetente = salvarMensagem(idUserRemetente,idUserDest, mensagem);
                    if (!retornoMsgRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao enviar tente novamente", Toast.LENGTH_SHORT).show();
                    }else {
                        //destinatario
                        boolean retornoMsgDest = salvarMensagem(idUserDest,idUserRemetente, mensagem);
                        if(!retornoMsgDest){
                            Toast.makeText(ConversaActivity.this, "Problema ao enviar tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }


                    //salvar conversa para o remetente
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario(idUserDest);
                    conversa.setNome(nomeUserDest);
                    conversa.setMensagem(txtMensagem);
                     boolean retornoConversaRemetente = salvarConversa(idUserRemetente, idUserDest, conversa);
                     if (!retornoConversaRemetente){
                         Toast.makeText(ConversaActivity.this, "Problema ao salvar a conversa tente novamente", Toast.LENGTH_SHORT).show();
                     }else{
                         // salvar conversa para o destinatario
                         conversa = new Conversa();
                         conversa.setIdUsuario(idUserRemetente);
                         conversa.setNome(nomeUsuarioRementente);
                         conversa.setMensagem(txtMensagem);

                         boolean retornoCoversaDest = salvarConversa(idUserDest,idUserRemetente, conversa);
                         if (!retornoCoversaDest){
                             Toast.makeText(ConversaActivity.this, "Problema ao salvar a conversa tente novamente", Toast.LENGTH_SHORT).show();

                         }
                     }

                    edtMensagem.setText("");

                }
            }
        });
    }

    private boolean salvarMensagem(String idRemetente,String idDestinatario, Mensagem mensagem) {

        try {

            firebase = ConfigFirebase.getFirebase().child("mensagens");

            firebase.child(idRemetente).child(idDestinatario).push().setValue(mensagem);


            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private boolean salvarConversa(String idRemetente, String idDest, Conversa conversa){
        try {
            firebase = ConfigFirebase.getFirebase().child("conversas");
            firebase.child(idRemetente)
                    .child(idDest)
                    .setValue(conversa);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }
}

