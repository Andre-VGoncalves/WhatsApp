package andre.com.whatsapp.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import andre.com.whatsapp.config.ConfigFirebase;

public class User {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public User() {
    }

    public void salvar(){
        DatabaseReference referenceFirebase = ConfigFirebase.getFirebase();
        referenceFirebase.child("usuarios").child(getId()).setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
