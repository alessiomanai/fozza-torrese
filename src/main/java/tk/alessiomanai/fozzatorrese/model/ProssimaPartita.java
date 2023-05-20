package tk.alessiomanai.fozzatorrese.model;

import java.util.Date;

public class ProssimaPartita extends Partita{

    private String dataOra;
    private Date data;

    public String getDataOra() {
        return dataOra;
    }

    public void setDataOra(String dataOra) {
        this.dataOra = dataOra;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
