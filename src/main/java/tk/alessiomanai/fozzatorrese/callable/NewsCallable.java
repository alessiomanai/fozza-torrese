package tk.alessiomanai.fozzatorrese.callable;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import tk.alessiomanai.fozzatorrese.model.News;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class NewsCallable implements Callable<List<News>> {

    private List<News> parseNews(Document doc){

        List<News> notizie = new ArrayList<>();
        Elements articles = doc.getElementsByClass("post");

        for (Element article: articles) {
            notizie.add(parseArticle(article));
        }

        return notizie;
    }

    private News parseArticle(Element article){

        News news = new News();

        news.setTitolo(article.getElementsByClass("entry-title").text());
        news.setData(article.getElementsByClass("published").text());
        news.setUrl(article.getElementsByClass("entry-title")
                .get(0).getElementsByAttribute("href").attr("abs:href"));
        news.setFoto(article.getElementsByAttribute("src").attr("src"));

        return news;
    }

    @Override
    public List<News> call() throws Exception {

        Document doc = Jsoup.connect(FozzaTorreseConstants.TORRES_SITE + FozzaTorreseConstants.TORRES_SITE_NEWS).get();

        return parseNews(doc);
    }
}
