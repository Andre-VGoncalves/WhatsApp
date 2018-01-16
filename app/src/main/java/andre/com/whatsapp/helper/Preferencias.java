package andre.com.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String NOME_ARQUIVO = "whatsapp.preferencias";
    private String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private String CHAVE_NOME = "nomeUsuarioLogado";
    private int MODE = 0;

    public Preferencias(Context contexto) {
        this.contexto = contexto;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarDados(String identificadorUsuario, String nomeUsuario){
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();

    }

    public  String getIdentificador (){
        return preferences.getString(CHAVE_IDENTIFICADOR,null);
    }
    public  String getNome (){
        return preferences.getString(CHAVE_NOME,null);
    }



}
