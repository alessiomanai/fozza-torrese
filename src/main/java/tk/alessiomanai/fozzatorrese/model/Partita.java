package tk.alessiomanai.fozzatorrese.model;

import java.util.List;

public class Partita {

    private String squadraCasa;
    private String squadraTrasferta;
    private String squadraCasaPunteggio;
    private String squadraTrasfertaPunteggio;
    private String risultato;
    private String tempo;
    private String formazioneCasa;
    private String formazioneTrasferta;
    private String marcatori;
    private String logoCasa;
    private String logoTrasferta;

    private List<String> cronaca;

    public String getSquadraCasa() {
        return squadraCasa;
    }

    public void setSquadraCasa(String squadraCasa) {
        this.squadraCasa = squadraCasa;
    }

    public String getSquadraTrasferta() {
        return squadraTrasferta;
    }

    public void setSquadraTrasferta(String squadraTrasferta) {
        this.squadraTrasferta = squadraTrasferta;
    }

    public String getSquadraCasaPunteggio() {
        return squadraCasaPunteggio;
    }

    public void setSquadraCasaPunteggio(String squadraCasaPunteggio) {
        this.squadraCasaPunteggio = squadraCasaPunteggio;
    }

    public String getSquadraTrasfertaPunteggio() {
        return squadraTrasfertaPunteggio;
    }

    public void setSquadraTrasfertaPunteggio(String squadraTrasfertaPunteggio) {
        this.squadraTrasfertaPunteggio = squadraTrasfertaPunteggio;
    }

    public String getRisultato() {
        return risultato;
    }

    public void setRisultato(String risultato) {
        this.risultato = risultato;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getFormazioneCasa() {
        return formazioneCasa;
    }

    public void setFormazioneCasa(String formazioneCasa) {
        this.formazioneCasa = formazioneCasa;
    }

    public String getFormazioneTrasferta() {
        return formazioneTrasferta;
    }

    public void setFormazioneTrasferta(String formazioneTrasferta) {
        this.formazioneTrasferta = formazioneTrasferta;
    }

    public String getMarcatori() {
        return marcatori;
    }

    public void setMarcatori(String marcatori) {
        this.marcatori = marcatori;
    }

    public String getLogoCasa() {
        return logoCasa;
    }

    public void setLogoCasa(String logoCasa) {
        this.logoCasa = logoCasa;
    }

    public String getLogoTrasferta() {
        return logoTrasferta;
    }

    public void setLogoTrasferta(String logoTrasferta) {
        this.logoTrasferta = logoTrasferta;
    }

    public List<String> getCronaca() {
        return cronaca;
    }

    public void setCronaca(List<String> cronaca) {
        this.cronaca = cronaca;
    }
}
