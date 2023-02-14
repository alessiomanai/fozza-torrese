package tk.alessiomanai.fozzatorrese.callable;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.ProssimaPartita;

public class ProssimaPartitaCallable implements Callable<ProssimaPartita> {

    private ProssimaPartita parse(Document doc){

        ProssimaPartita partita = new ProssimaPartita();

        partita.setSquadraCasa(doc.select("strong").get(0).text());
        partita.setSquadraTrasferta(doc.select("strong").get(1).text());
        partita.setLogoCasa(doc.select("img").get(2).absUrl("src"));
        partita.setLogoTrasferta(doc.select("img").get(6).absUrl("src"));

        String timestamp = doc.getElementsByAttribute("data-end-timestamp").attr("data-end-timestamp");

        Date data = new Date(Long.parseLong(timestamp)*1000);
        partita.setDataOra(new SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.ITALIAN).format(data));

        return partita;
    }

    @Override
    public ProssimaPartita call() throws Exception {
        Document doc = Jsoup.connect("https://seftorrescalcio.it/").get();
        return parse(doc);
    }
}
