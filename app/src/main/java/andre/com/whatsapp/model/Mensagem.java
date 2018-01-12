package andre.com.whatsapp.model;

/**
 * Created by andrevieira on 12/01/2018.
 */

public class Mensagem {

    private String idUser;
    private String menssagem;

    public Mensagem() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }
}
