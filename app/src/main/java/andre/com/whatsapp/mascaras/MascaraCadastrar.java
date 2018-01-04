package andre.com.whatsapp.mascaras;

import android.widget.EditText;

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
