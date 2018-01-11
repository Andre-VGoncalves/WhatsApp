package andre.com.whatsapp.model;

/**
 * Created by andrevieira on 11/01/2018.
 */

public class Contato {

    private String identificadoUser;
    private String nome;
    private String email;

    public Contato() {

    }

    public String getIdentificadoUser() {
        return identificadoUser;
    }

    public void setIdentificadoUser(String identificadoUser) {
        this.identificadoUser = identificadoUser;
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
 }
