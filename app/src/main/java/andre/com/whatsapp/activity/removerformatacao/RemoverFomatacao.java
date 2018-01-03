package andre.com.whatsapp.activity.removerformatacao;

/**
 * Created by andrevieira on 03/01/2018.
 */

public class RemoverFomatacao {

    public String remover (String texto){

        texto = texto.replace("+","");
        texto = texto.replace("-","");

        return texto;


    }
}
