package tk.alessiomanai.fozzatorrese.model;

public class PartitaCalendario extends Partita {

    private String data;
    private String orario;
    private String casaTrasfertaTorres;
    private String urlLogo;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public String getCasaTrasfertaTorres() {
        return casaTrasfertaTorres;
    }

    public void setCasaTrasfertaTorres(String casaTrasfertaTorres) {
        this.casaTrasfertaTorres = casaTrasfertaTorres;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
