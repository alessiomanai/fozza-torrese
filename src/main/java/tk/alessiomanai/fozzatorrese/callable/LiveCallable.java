package tk.alessiomanai.fozzatorrese.callable;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.Partita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class LiveCallable implements Callable<Partita> {

    private Partita parseFromCentotrentuno() throws Exception {

        Partita partita = new Partita();
        List<String> cronaca = new ArrayList<>();

        Document doc = Jsoup.connect(FozzaTorreseConstants.LIVE_SITE).get();
        Element article = doc.select("article").get(0);
        Element link = article.select("a").get(0);
        String absHref = link.attr("abs:href");

        if(absHref.contains("live")) {

            Document livePage = Jsoup.connect(absHref).get();
            Elements results = livePage.getElementsByClass("score");
            Elements teams = livePage.getElementsByClass("team-name");

            Elements formazioni = livePage.getElementsByClass("comment-text timeline-box");

            String marcatori = livePage.getElementsByClass("scorers-title").get(0).text();

            partita.setSquadraCasaPunteggio(results.text().split("-")[0]);
            partita.setSquadraTrasfertaPunteggio(results.text().split("-")[1]);
            partita.setSquadraTrasferta(teams.get(1).text());
            partita.setSquadraCasa(teams.get(0).text());
            partita.setFormazioneCasa(formazioni.get(formazioni.size()-1).text());
            partita.setFormazioneTrasferta(formazioni.get(formazioni.size()-2).text());
            partita.setMarcatori(marcatori);

            //estrazione cronaca live
            Element divCronaca = livePage.getElementsByClass("entry-content").get(0);
            Elements righeCronaca = divCronaca.getElementsByClass("comment-live");

            for (Element p : righeCronaca) {
                cronaca.add(p.text());
            }

            String time = cronaca.get(0).substring(0, 3);
            partita.setTempo(time);
            partita.setCronaca(cronaca);

            return partita;
        }

        return null;
    }

    @Override
    public Partita call() throws Exception {
        return parseFromCentotrentuno();
    }
}
