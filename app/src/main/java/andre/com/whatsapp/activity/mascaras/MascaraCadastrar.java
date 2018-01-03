package andre.com.whatsapp.activity.mascaras;

import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class MascaraCadastrar {

    private MascaraCelular mascaraCelular = new MascaraCelular();
    private MascaraCodArea mascaraCodArea = new MascaraCodArea();
    private MascaraCodPais mascaraCodPais = new MascaraCodPais();


    public MascaraCadastrar(EditText edtCodPais,EditText edtCodArea,EditText edtCelular) {
        mascaraCodPais.maskCodPais(edtCodPais);
        mascaraCodArea.maskCodArea(edtCodArea);
        mascaraCelular.maskCelular(edtCelular);
    }


}
