package tk.alessiomanai.fozzatorrese.callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.Giocatore;
import tk.alessiomanai.fozzatorrese.model.Squadra;

public class SquadraCallable implements Callable<Squadra> {

    private Squadra parseSquadra(Document doc){

        Squadra squadra = new Squadra();

        Element table = doc.select("table").get(1).selectFirst("tbody");
        Elements rows = table.select("tr");

        for (int i = 0; i < rows.size(); i += 3) {

            Elements colonne = rows.get(i).select("td");

            //Log.i("URL", colonne.get(3).getElementsByAttribute("href").attr("abs:href"));

            Giocatore giocatore = new Giocatore();

            giocatore.setNome(colonne.get(3).text());
            giocatore.setNumero(colonne.get(0).text());
            giocatore.setRuolo(colonne.get(4).text());
            giocatore.setEta(colonne.get(5).text());
            giocatore.setContratto(colonne.get(6).text());
            giocatore.setValore(colonne.get(7).text());
            giocatore.setFoto(colonne.get(1).getElementsByAttribute("data-src").attr("data-src"));
            giocatore.setUrlDettagli(colonne.get(3).getElementsByAttribute("href").attr("abs:href"));

            squadra.add(giocatore);

        }

        return squadra;
    }

    @Override
    public Squadra call() throws Exception {
        Document doc = Jsoup.connect("https://www.transfermarkt.it/sef-torres-1903/kader/verein/2253").get();
        return parseSquadra(doc);
    }
}
