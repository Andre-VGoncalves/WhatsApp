package andre.com.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String NOME_ARQUIVO = "whatsapp.preferencias";
    private String CHAVE_NOME = "nome";
    private String CHAVE_TELEFONE = "telefone";
    private String CHAVE_TOKEN = "token";
    private int MODE = 0;

    public Preferencias(Context contexto) {
        this.contexto = contexto;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void saveUserPreferencias(String nome, String telefone, String token){
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();

    }

    public HashMap<String, String> getDadosUser (){

        HashMap<String, String> dadosUser = new HashMap<>();

        dadosUser.put(CHAVE_NOME, preferences.getString(CHAVE_NOME,null));
        dadosUser.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUser.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN,null));

        return dadosUser;
    }


}
