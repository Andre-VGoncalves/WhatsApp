package andre.com.whatsapp.mascaras;

import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class MascaraCodPais {

    public void maskCodPais(EditText edtCodPais){
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskPais = new MaskTextWatcher(edtCodPais, simpleMaskFormatter);
        edtCodPais.addTextChangedListener(maskPais);
    }


}
