package tk.alessiomanai.fozzatorrese.callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.Partita;

public class LiveCallable implements Callable<Partita> {

    private Partita parseFromCentotrentuno() throws Exception {

        Partita partita = new Partita();

        Document doc = Jsoup.connect("https://www.centotrentuno.com/tag/calcio-torres-sassari/").get();
        Element article = doc.select("article").get(0);
        Element link = article.select("a").get(0);
        String absHref = link.attr("abs:href");

        if(absHref.contains("live")) {

            Document livePage = Jsoup.connect(absHref).get();
            Elements results = livePage.getElementsByClass("result");
            Elements teams = livePage.getElementsByClass("tbteam");
            String time = livePage.getElementsByClass("overlay").get(0).text();
            Elements formazioni = livePage.getElementsByClass("tab-live");

            String marcatori = livePage.select("p").get(0).text();

            partita.setTempo(time);
            partita.setSquadraCasaPunteggio(results.get(0).text());
            partita.setSquadraTrasfertaPunteggio(results.get(1).text());
            partita.setSquadraTrasferta(teams.get(1).text());
            partita.setSquadraCasa(teams.get(0).text());
            partita.setFormazioneCasa(formazioni.get(0).text());
            partita.setFormazioneTrasferta(formazioni.get(1).text());
            partita.setMarcatori(marcatori);

            return partita;
        }

        return null;
    }

    @Override
    public Partita call() throws Exception {
        return parseFromCentotrentuno();
    }
}
