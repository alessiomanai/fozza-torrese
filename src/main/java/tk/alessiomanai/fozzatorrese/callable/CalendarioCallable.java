package tk.alessiomanai.fozzatorrese.callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tk.alessiomanai.fozzatorrese.model.Calendario;
import tk.alessiomanai.fozzatorrese.model.PartitaCalendario;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class CalendarioCallable implements Callable<Calendario> {

    private final String REGEX = "([0-9]{1,2}) .{3} .{8} [0-9]{2}:[0-9]{2} (C|T) (\\([0-9]{2}\\.\\) )?.* (\\(.*\\))?.*:.*";
    private final String REGEX_SQUADRA = "[A-Z]{1}[a-z]{2,}( [A-Z]{1}[a-z]{2,})?";

    private Calendario parse(Document doc){

        Calendario calendario = new Calendario();
        Element table = doc.select("table").get(1); //select the first table.
        Elements rows = table.select("tr");

        for (Element row : rows) {
            if(row.text().matches(REGEX)) {
                String tmp = row.text();

                String imgsrc = row.selectFirst("img").absUrl("src");

                String[] elementi = tmp.split(" ");

                Pattern pattern = Pattern.compile(REGEX_SQUADRA);
                Matcher matcher = pattern.matcher(tmp);

                PartitaCalendario partita = new PartitaCalendario();
                partita.setData(elementi[2]);
                partita.setOrario(elementi[3]);

                if(matcher.find()){
                    partita.setSquadraCasa(matcher.group());
                }

                partita.setRisultato(elementi[elementi.length-1]);
                partita.setCasaTrasfertaTorres(elementi[4]);

                partita.setSquadraCasa( partita.getCasaTrasfertaTorres().equals("C") ?
                        FozzaTorreseConstants.TORRES_CASA + partita.getSquadraCasa() :
                        partita.getSquadraCasa() + FozzaTorreseConstants.TORRES_TRASFERTA);

                partita.setUrlLogo(imgsrc);

                calendario.add(partita);
            }
        }

        return calendario;
    }

    @Override
    public Calendario call() throws Exception {
        Document doc = Jsoup.connect("https://www.transfermarkt.it/sef-torres-1903/spielplandatum/verein/2253").get();
        return parse(doc);
    }

}
