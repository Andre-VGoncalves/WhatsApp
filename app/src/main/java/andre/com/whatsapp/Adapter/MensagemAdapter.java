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
import andre.com.whatsapp.helper.Preferencias;
import andre.com.whatsapp.model.Mensagem;

/**
 * Created by andrevieira on 16/01/2018.
 */

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(Context c, ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (mensagens !=null){
            //recuperar os dados do remetente
            Preferencias preferencias = new Preferencias(context);
            String idUserRemetente = preferencias.getIdentificador();

            //inicializando o objeto para montagem do layout;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //recuperar mensagem
            Mensagem mensagem = mensagens.get(position);

            //montar a view a partir de um xml
            if (idUserRemetente.equals(mensagem.getIdUser())){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);

            }

            //recuperar o elemento para exibir
            TextView txtMensagem =(TextView) view.findViewById(R.id.tvMensagem);
            txtMensagem.setText(mensagem.getMenssagem());
        }

        return view;
    }
}
