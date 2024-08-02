package tk.alessiomanai.fozzatorrese.callable.live;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.Partita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class PunteggioLiveCallable implements Callable<Partita> {

    private Partita parse() throws Exception {

        Partita partita = new Partita();

        Document doc = Jsoup.connect(FozzaTorreseConstants.TM_SERIEC + FozzaTorreseUtils.getSaisonId()).get();

        Element riga = FozzaTorreseUtils.getRigaPartita(doc);

        String squadraCasa = riga.getElementsByClass("verein-heim").get(0).text();
        String punteggio = riga.getElementsByClass("ergebnis").get(0).text();
        String squadraTrasferta = riga.getElementsByClass("verein-gast").get(0).text();

        partita.setSquadraTrasferta(squadraTrasferta);
        partita.setSquadraCasa(squadraCasa);
        partita.setSquadraCasaPunteggio(punteggio.substring(0, punteggio.indexOf(":")));
        partita.setSquadraTrasfertaPunteggio(punteggio.substring(punteggio.indexOf(":")+1));

        return partita;
    }

    @Override
    public Partita call() throws Exception {
        return parse();
    }
}
