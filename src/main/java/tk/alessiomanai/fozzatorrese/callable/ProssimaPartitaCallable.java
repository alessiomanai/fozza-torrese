package tk.alessiomanai.fozzatorrese.callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.ProssimaPartita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class ProssimaPartitaCallable implements Callable<ProssimaPartita> {

    private ProssimaPartita parse(Document doc){

        ProssimaPartita partita = new ProssimaPartita();

        partita.setSquadraCasa(doc.select("strong").get(0).text());
        partita.setSquadraTrasferta(doc.select("strong").get(1).text());
        partita.setLogoCasa(doc.select("img").get(1).absUrl("src"));
        partita.setLogoTrasferta(doc.select("img").get(3).absUrl("src"));

        String timestamp = doc.getElementsByAttribute("data-end-timestamp").attr("data-end-timestamp");

        if (!timestamp.equals("")) {

            Date data = new Date(Long.parseLong(timestamp) * 1000);
            partita.setData(data);
            partita.setDataOra(new SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.ITALIAN).format(data));

            return partita;

        } else {
            return null;
        }
    }

    @Override
    public ProssimaPartita call() throws Exception {
        Document doc = Jsoup.connect(FozzaTorreseConstants.TORRES_SITE).get();
        return parse(doc);
    }
}
