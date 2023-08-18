package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.NewsCallable;
import tk.alessiomanai.fozzatorrese.listatore.ListatoreNews;
import tk.alessiomanai.fozzatorrese.model.News;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class NewsActivity extends AppCompatActivity {

    private ListView list;
    private List<News> notizie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        list = findViewById(R.id.listViewNews);

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                caricaNotizie();
                return false;
            }
        };

        Looper.myQueue().addIdleHandler(handler);
    }

    private void caricaNotizie(){

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<List<News>> process = executor.submit(new NewsCallable());

        try {
            notizie = process.get();
        } catch (ExecutionException | InterruptedException e) {
            FozzaTorreseUtils.error500(NewsActivity.this);
            e.printStackTrace();
        }

        ListatoreNews adapter = new ListatoreNews(this, notizie);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long idonet) {

                Intent newsActivity = new Intent(getBaseContext(), DettaglioNewsActivity.class);
                newsActivity.putExtra(FozzaTorreseConstants.NEWS, notizie.get(position));
                startActivity(newsActivity);
            }
        });
    }
}