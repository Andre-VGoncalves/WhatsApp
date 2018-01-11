package andre.com.whatsapp.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;

import andre.com.whatsapp.Adapter.TabAdapter;
import andre.com.whatsapp.R;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.helper.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;


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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deslogarUser(){
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
