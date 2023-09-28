package tk.alessiomanai.fozzatorrese.callable;

import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.GiornataCampionato;
import tk.alessiomanai.fozzatorrese.model.Partita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class GiornataAttualeCallable implements Callable<GiornataCampionato> {

    private int SUBSTRING = 5;

    private GiornataCampionato parseGiornata(Document document){

        GiornataCampionato giornataCampionato = new GiornataCampionato();

        Element tabella = document.getElementsByClass("livescore").get(1);
        Elements rigaPartite = tabella.getElementsByClass("begegnungZeile");

        giornataCampionato.setNumeroGiornata(document.getElementsByClass("footer-links fl").get(1).text());

        for(Element rigaPartita : rigaPartite){

            Partita partita = new Partita();
            partita.setSquadraCasa(rigaPartita.getElementsByClass("verein-heim").get(0).text().substring(SUBSTRING));
            partita.setLogoCasa(rigaPartita.getElementsByClass("verein-heim").get(0).select("img").get(0).absUrl("src"));
            partita.setRisultato(rigaPartita.getElementsByClass("ergebnis").get(0).text());
            partita.setSquadraTrasferta(rigaPartita.getElementsByClass("verein-gast").get(0).text());
            partita.setSquadraTrasferta(partita.getSquadraTrasferta().substring(0, partita.getSquadraTrasferta().length() - SUBSTRING));
            partita.setLogoTrasferta(rigaPartita.getElementsByClass("verein-gast").get(0).select("img").get(0).absUrl("src"));
            giornataCampionato.add(partita);
        }

        return giornataCampionato;

    }

    @Override
    public GiornataCampionato call() throws Exception {
        Document doc = Jsoup.connect(FozzaTorreseConstants.TM_SERIEC_GIORNATA).get();
        return parseGiornata(doc);
    }
}
