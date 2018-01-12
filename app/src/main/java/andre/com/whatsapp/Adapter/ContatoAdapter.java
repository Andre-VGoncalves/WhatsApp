package andre.com.whatsapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import andre.com.whatsapp.R;
import andre.com.whatsapp.model.Contato;

/**
 * Created by andrevieira on 12/01/2018.
 */

public class ContatoAdapter extends ArrayAdapter<Contato>{

    private  ArrayList<Contato> contatos;
    private Context context;


    public ContatoAdapter(Context c, ArrayList<Contato> objects) {
        super(c,0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verificar se a lista esta vazia
        if(contatos != null){

            //inicializar o objeto
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montar a View a Partir do XML
            view = inflater.inflate(R.layout.lista_contato,parent, false);

            //recuper elemento para exibicao
            TextView nomeContato = view.findViewById(R.id.txtNome);
            TextView emailContato = view.findViewById(R.id.txtEmail);

            Contato contato = contatos.get(position);
            nomeContato.setText(contato.getNome());
            emailContato.setText(contato.getEmail());
        }

        return view;
    }
}
