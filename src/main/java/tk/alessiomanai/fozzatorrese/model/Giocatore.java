package tk.alessiomanai.fozzatorrese.model;

import java.io.Serializable;

public class Giocatore implements Serializable {

    private String numero;
    private String nome;
    private String ruolo;
    private String eta;
    private String contratto;
    private String valore;
    private String foto;
    private String urlDettagli;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUrlDettagli() {
        return urlDettagli;
    }

    public void setUrlDettagli(String urlDettagli) {
        this.urlDettagli = urlDettagli;
    }
}
