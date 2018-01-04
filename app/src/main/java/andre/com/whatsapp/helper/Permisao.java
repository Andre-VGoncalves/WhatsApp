package andre.com.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class Permisao {

    public static  boolean validarPermissoes (int requestCod, Activity activity, String[] permissoes){

        List<String> listPermissoes = new ArrayList<String>();
        if(Build.VERSION.SDK_INT >= 23){

            for (String permissao : permissoes){

                boolean validaPermissao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;

                if (!validaPermissao)listPermissoes.add(permissao);
            }

            String[] novasPermissoes = new String[listPermissoes.size()];
            listPermissoes.toArray(novasPermissoes);

            ActivityCompat.requestPermissions(activity,novasPermissoes, requestCod);

        }

        return true;

    }
}
