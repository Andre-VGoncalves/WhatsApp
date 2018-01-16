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

import andre.com.whatsapp.Adapter.ConversaAdapter;
import andre.com.whatsapp.R;
import andre.com.whatsapp.activity.ConversaActivity;
import andre.com.whatsapp.config.ConfigFirebase;
import andre.com.whatsapp.helper.Preferencias;
import andre.com.whatsapp.model.Contato;
import andre.com.whatsapp.model.Conversa;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerConversas;

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerConversas);
        Log.i("ValueEventListener", "OnSart");
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerConversas);
        Log.i("ValueEventListener", "OnStop");
    }

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_conversas,container, false );
        listView = view.findViewById(R.id.lvConversas);

        conversas = new ArrayList<>();
        adapter = new ConversaAdapter(getActivity(), conversas);
        listView.setAdapter(adapter);

        Preferencias preferencias = new Preferencias(getActivity());
        String idUserLogado = preferencias.getIdentificador();

        firebase = ConfigFirebase.getFirebase()
                .child("conversas")
                .child(idUserLogado);


        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                conversas.clear();

                for(DataSnapshot dados :  dataSnapshot.getChildren()) {

                    Conversa conversa = dados.getValue(Conversa.class);
                    conversas.add(conversa);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return view;
    }
}
