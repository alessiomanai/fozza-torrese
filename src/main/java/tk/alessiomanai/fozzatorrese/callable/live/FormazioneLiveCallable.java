package tk.alessiomanai.fozzatorrese.callable.live;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.Partita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class FormazioneLiveCallable implements Callable<Partita> {

    private Partita parse() throws Exception {

        Partita partita = new Partita();
        StringBuilder marcatori = new StringBuilder();

        Document doc = Jsoup.connect(FozzaTorreseConstants.TM_SERIEC).get();

        Element riga = FozzaTorreseUtils.getRigaPartita(doc);

        String matchId = riga.attr("id");

        Document pagePartita = Jsoup.connect(FozzaTorreseConstants.TM_LIVE + matchId).get();
        Element hrefFormazione = pagePartita.getElementById("formazione");
        String formazionePage = hrefFormazione.select("a").attr("abs:href");

        Elements h2s = pagePartita.getElementsByClass("large-12 columns");

        for (Element e : h2s){
            if(e.text().contains("Gol")){
                Elements lis = e.select("li");
                for (Element li : lis){
                    marcatori.append("- ").append(li.text()).append("\n");
                }
                break;
            }
        }

        partita.setMarcatori(marcatori.toString());

        Document pageFormazione = Jsoup.connect(formazionePage).get();
        Elements formazioni = pageFormazione.getElementsByClass("wichtig");

        //formazione casa
        StringBuilder formazioneCasa = new StringBuilder();
        for(int i = 0; i < 11; i++){
            formazioneCasa.append(formazioni.get(i).text()).append("\n");
        }

        partita.setFormazioneCasa(formazioneCasa.toString());

        //formazione trasferta
        StringBuilder formazioneTrasferta = new StringBuilder();
        for(int i = 11; i < 22; i++){
            formazioneTrasferta.append(formazioni.get(i).text()).append("\n");
        }

        partita.setFormazioneTrasferta(formazioneTrasferta.toString());

        return partita;
    }

    @Override
    public Partita call() throws Exception {
        return parse();
    }
}
