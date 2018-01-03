package andre.com.whatsapp.activity.mascaras;


import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class MascaraCelular {


    public void maskCelular(EditText edtCelular) {
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskArea = new MaskTextWatcher(edtCelular, simpleMaskFormatter);

        edtCelular.addTextChangedListener(maskArea);
    }
}
