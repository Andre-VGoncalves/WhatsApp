package andre.com.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andre.com.whatsapp.Adapter.TabAdapter;
import andre.com.whatsapp.R;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.helper.Base64Custom;
import andre.com.whatsapp.helper.Preferencias;
import andre.com.whatsapp.helper.SlidingTabLayout;
import andre.com.whatsapp.model.Contato;
import andre.com.whatsapp.model.User;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    private String identificadorContato;
    private DatabaseReference referenceFireBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("DedeZap");
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stlTabs);
        viewPager = (ViewPager) findViewById(R.id.vpPagina);

        //configurar slidings
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        //configurar adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSair:
                deslogarUser();
                return true;
            case R.id.itemConfig:
                return true;
            case R.id.itemAdd:
                abrirCadastroContato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroContato() {
        //criando dialog de adcionar copntato
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        //configurar a dialog
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do Usuário");
        alertDialog.setCancelable(false);
        final EditText edtEmail = new EditText(MainActivity.this);
        alertDialog.setView(edtEmail);

        //configurar Botões
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String emailContato =  edtEmail.getText().toString();
                if (emailContato.equals("")){
                    Toast.makeText(MainActivity.this, "Digite o E-mail do contato", Toast.LENGTH_SHORT).show();
                }else{
                    //verificar se o email existe
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //recuperar a instacia
                    referenceFireBase = ConfigFirebase.getFirebase().child("usuarios").child(identificadorContato);

                    referenceFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null){

                                //recuperar dados do contato
                                User userContato = dataSnapshot.getValue(User.class);

                                //Recuperar o usuario logdo da base64
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadoUser = preferencias.getIdentificador();

                                Toast.makeText(MainActivity.this,identificadoUser , Toast.LENGTH_SHORT).show();


                                referenceFireBase = ConfigFirebase.getFirebase().child("contatos")
                                        .child(identificadoUser)
                                        .child(identificadorContato);
                                //referenceFireBase = referenceFireBase
                                Contato contato = new Contato();
                                contato.setIdentificadoUser(identificadorContato);
                                contato.setEmail(userContato.getEmail());
                                contato.setNome(userContato.getNome());

                                referenceFireBase.setValue(contato);

                            }else{
                                Toast.makeText(MainActivity.this, "Esse E-mail não esta cadastrado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private void deslogarUser(){
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
