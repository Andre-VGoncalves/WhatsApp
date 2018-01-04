package andre.com.whatsapp.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public final class ConfigFirebase {

    private static DatabaseReference referenceFirebase;

    public static DatabaseReference getFirebase(){
        referenceFirebase = FirebaseDatabase.getInstance().getReference();
        return referenceFirebase;
    }
}
