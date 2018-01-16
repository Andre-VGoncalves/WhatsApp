package andre.com.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import andre.com.whatsapp.Adapter.ContatoAdapter;
import andre.com.whatsapp.R;
import andre.com.whatsapp.activity.ConversaActivity;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.helper.Preferencias;
import andre.com.whatsapp.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "OnStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "OnStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //montar a lista e o adapter
        listView = view.findViewById(R.id.lvContatos);
        contatos = new ArrayList<>();
        /*adapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_contato,
                contatos

        );*/
        adapter = new ContatoAdapter(getActivity(),contatos);
        listView.setAdapter(adapter);

        //recuperar contatos
        Preferencias  preferencias = new Preferencias(getActivity());
        String identificadorUserLogado = preferencias.getIdentificador();

        firebase = ConfigFirebase.getFirebase()
                    .child("contatos")
                    .child(identificadorUserLogado);

        //Listener para recuperar os contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                contatos.clear();
                //Listar contatos
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                //avisar que tem novo contatos
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Contato contato = contatos.get(position);

                //enviando dados para activity
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());

                startActivity(intent);
            }
        });

        return view;
    }

}
