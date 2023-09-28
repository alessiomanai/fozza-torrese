package tk.alessiomanai.fozzatorrese.model;

import java.util.ArrayList;

public class GiornataCampionato extends ArrayList<Partita> {

    private String numeroGiornata;


    public String getNumeroGiornata() {
        return numeroGiornata;
    }

    public void setNumeroGiornata(String numeroGiornata) {
        this.numeroGiornata = numeroGiornata;
    }
}
