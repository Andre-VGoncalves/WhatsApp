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
import andre.com.whatsapp.model.Conversa;

/**
 * Created by andrevieira on 16/01/2018.
 */

public class ConversaAdapter extends ArrayAdapter<Conversa>{

    private ArrayList<Conversa> conversas;
    private Context context;


    public ConversaAdapter(@NonNull Context c,  @NonNull ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.context = c;
        this.conversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(conversas != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_conversas, parent,false);

            TextView nome = view.findViewById(R.id.txtTitulo);
            TextView msg = view.findViewById(R.id.txtSubTitulo);

            Conversa conversa = conversas.get(position);
            nome.setText((conversa.getNome()));
            msg.setText(conversa.getMensagem());
        }

        return view;
    }
}
