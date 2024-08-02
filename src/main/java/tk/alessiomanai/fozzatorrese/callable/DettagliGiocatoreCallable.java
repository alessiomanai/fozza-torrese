package tk.alessiomanai.fozzatorrese.callable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.DettaglioGiocatore;
import tk.alessiomanai.fozzatorrese.model.Giocatore;

public class DettagliGiocatoreCallable implements Callable<Giocatore>  {

    private String url;

    public DettagliGiocatoreCallable(String url){
        this.url = url;
    }

    public DettaglioGiocatore parseDettaglioGiocatore(@NonNull Document doc) throws JSONException {
        DettaglioGiocatore dettaglioGiocatore = new DettaglioGiocatore();

        Elements checkAssenza = doc.getElementsByClass("verletzungsbox");

        if(checkAssenza.size() > 0) {
            dettaglioGiocatore.setCausaAssenza(doc.getElementsByClass("verletzungsbox").get(0).text());
        }

        Element tableDettagli = doc.getElementsByClass("large-6 large-pull-6 small-12 columns spielerdatenundfakten").get(0);
        Elements nomeDettagli = tableDettagli.getElementsByClass("info-table__content info-table__content--regular");
        Elements dettagli = tableDettagli.getElementsByClass("info-table__content info-table__content--bold");

        JSONObject json = new JSONObject();

        //rimozione elementi inutili al json
        for (int j = 0; j < nomeDettagli.size(); j++) {
            if (nomeDettagli.get(j).text().equals("Procuratore:") ||
                    nomeDettagli.get(j).text().contains("Squadra attuale")) {
                nomeDettagli.remove(j);
                j--;
            }
        }

        for (int i=0; i < dettagli.size()-1; i++){
            json.put(nomeDettagli.get(i).text(), dettagli.get(i).text());
        }

        dettaglioGiocatore.setDataNascita(json.optString("Nato il:"));
        dettaglioGiocatore.setLuogoNascita(json.optString("Luogo di nascita:"));
        dettaglioGiocatore.setAltezza(json.optString("Altezza:"));
        dettaglioGiocatore.setNazionalita(json.optString("Nazionalità:"));
        dettaglioGiocatore.setPosizione(json.optString("Posizione:"));
        dettaglioGiocatore.setPiede(json.optString("Piede:"));
        dettaglioGiocatore.setInRosaDa(json.optString("In rosa da:"));
        dettaglioGiocatore.setScadenzaContratto(json.optString("Scadenza:"));

        return dettaglioGiocatore;
    }

    @Override
    public Giocatore call() throws Exception {
        Document doc = Jsoup.connect(this.url).get();
        return parseDettaglioGiocatore(doc);
    }



}
