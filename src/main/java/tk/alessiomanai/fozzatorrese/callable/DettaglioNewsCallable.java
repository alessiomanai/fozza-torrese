package tk.alessiomanai.fozzatorrese.callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.News;

public class DettaglioNewsCallable implements Callable<News> {

    private String url;
    private News notizia;

    public DettaglioNewsCallable(String url){
        this.url = url;
    }

    public DettaglioNewsCallable(News notizia){
        this.notizia = notizia;
        this.url = notizia.getUrl();
    }

    private News parseNews(Document doc){

        notizia.setArticolo(doc.getElementsByClass("entry-content").text());

        return notizia;
    }

    @Override
    public News call() throws Exception {

        Document doc = Jsoup.connect(url).get();

        return parseNews(doc);
    }
}
