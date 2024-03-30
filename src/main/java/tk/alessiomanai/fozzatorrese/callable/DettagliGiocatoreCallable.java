package tk.alessiomanai.fozzatorrese.callable;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.DettaglioGiocatore;
import tk.alessiomanai.fozzatorrese.model.Giocatore;

public class DettagliGiocatoreCallable implements Callable<Giocatore>  {

    private String url;
    private final String DATA_REGEX = "[0-9]{2}\\/[a-z]{3}\\/[0-9]{4}";

    public DettagliGiocatoreCallable(String url){
        this.url = url;
    }

    public DettaglioGiocatore parseDettaglioGiocatore(@NonNull Document doc){
        DettaglioGiocatore dettaglioGiocatore = new DettaglioGiocatore();

        Elements checkAssenza = doc.getElementsByClass("verletzungsbox");

        if(checkAssenza.size() > 0) {
            dettaglioGiocatore.setCausaAssenza(doc.getElementsByClass("verletzungsbox").get(0).text());
        }

        Element tableDettagli = doc.getElementsByClass("large-6 large-pull-6 small-12 columns spielerdatenundfakten").get(0);
        Elements dettagli = tableDettagli.getElementsByClass("info-table__content info-table__content--bold");

        if(dettagli.size() >= 10 && dettagli.get(1).text().matches(DATA_REGEX)){
            dettaglioGiocatore.setDataNascita(dettagli.get(1).text());
            dettaglioGiocatore.setLuogoNascita(dettagli.get(2).text());
            dettaglioGiocatore.setEta(dettagli.get(3).text());
            dettaglioGiocatore.setAltezza(dettagli.get(4).text());
            dettaglioGiocatore.setNazionalita(dettagli.get(5).text());
            dettaglioGiocatore.setPosizione(dettagli.get(6).text());
            dettaglioGiocatore.setPiede(dettagli.get(7).text());
            dettaglioGiocatore.setInRosaDa(dettagli.get(8).text());
            dettaglioGiocatore.setScadenzaContratto(dettagli.get(9).text());
        } else {
            dettaglioGiocatore.setDataNascita(dettagli.get(0).text());
            dettaglioGiocatore.setLuogoNascita(dettagli.get(1).text());
            dettaglioGiocatore.setEta(dettagli.get(2).text());
            dettaglioGiocatore.setAltezza(dettagli.get(3).text());
            dettaglioGiocatore.setNazionalita(dettagli.get(4).text());
            dettaglioGiocatore.setPosizione(dettagli.get(5).text());
            dettaglioGiocatore.setPiede(dettagli.get(6).text());
            dettaglioGiocatore.setInRosaDa(dettagli.get(7).text());

            if (dettagli.size() > 8){
                dettaglioGiocatore.setScadenzaContratto(dettagli.get(8).text());
            }
        }

//        Element valoreMercatoElement = doc.getElementsByClass("current-value").get(0);
//        dettaglioGiocatore.setValoreDiMercato(valoreMercatoElement.text());

        return dettaglioGiocatore;
    }

    @Override
    public Giocatore call() throws Exception {
        Document doc = Jsoup.connect(this.url).get();
        return parseDettaglioGiocatore(doc);
    }



}
