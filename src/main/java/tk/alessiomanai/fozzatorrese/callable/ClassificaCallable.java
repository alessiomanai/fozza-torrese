package tk.alessiomanai.fozzatorrese.callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.Classifica;
import tk.alessiomanai.fozzatorrese.model.RigaClassifica;

public class ClassificaCallable implements Callable<Classifica> {

    private Classifica parseClassificaWikipedia(Document doc){

        Classifica classifica = new Classifica();
        Element table = doc.select(".wikitable").get(7); //select the first table.
        Elements rows = table.select("td");

        for (int i = 0; i < rows.toArray().length; i += 11) {

            RigaClassifica temp = new RigaClassifica();
            temp.setNomeSquadra(rows.get(i+2).text());
            temp.setPosizione(rows.get(i+1).text());
            temp.setPunti(rows.get(i+3).text());
            temp.setVinte(rows.get(i+5).text());
            temp.setPareggiate(rows.get(i+6).text());
            temp.setPerse(rows.get(i+7).text());

            classifica.add(temp);
        }

        return classifica;
    }

    private Classifica parseClassificaTrasfermarkt(Document doc){

        Classifica classifica = new Classifica();
        Element table = doc.select("table").get(1); //select the first table.
        Element tbody = table.selectFirst("tbody");
        Elements rows = tbody.select("tr");

        for (Element row : rows) {

            Elements cols = row.select("td");

            RigaClassifica temp = new RigaClassifica();
            temp.setNomeSquadra(cols.get(2).text());
            temp.setPosizione(cols.get(0).text());
            temp.setPunti(cols.get(9).text());
            temp.setVinte(cols.get(4).text());
            temp.setPareggiate(cols.get(5).text());
            temp.setPerse(cols.get(6).text());
            temp.setStemma(cols.get(1).selectFirst("img").absUrl("src"));

            classifica.add(temp);
        }

        return classifica;
    }

    @Override
    public Classifica call() throws Exception {
        //Document doc = Jsoup.connect("https://it.wikipedia.org/wiki/Serie_C_2022-2023").get();
        Document doc = Jsoup.connect("https://www.transfermarkt.it/serie-c-girone-b/tabelle/wettbewerb/IT3B/saison_id/2022").get();
        return parseClassificaTrasfermarkt(doc);
    }
}
