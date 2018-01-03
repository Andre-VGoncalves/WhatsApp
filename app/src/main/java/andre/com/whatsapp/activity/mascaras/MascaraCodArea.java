package andre.com.whatsapp.activity.mascaras;

import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


public class MascaraCodArea {



    public void maskCodArea(EditText edtArea){
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NN");
        MaskTextWatcher maskArea = new MaskTextWatcher (edtArea, simpleMaskFormatter);

        edtArea.addTextChangedListener(maskArea);
    }
}
