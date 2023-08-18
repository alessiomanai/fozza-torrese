package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.DettaglioNewsCallable;

import tk.alessiomanai.fozzatorrese.model.News;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class DettaglioNewsActivity extends AppCompatActivity {

    private News notizia;
    private ImageView fotoArticolo;
    private TextView testoArticolo;
    private TextView titoloArticolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_news);

        notizia = (News) Objects.requireNonNull(getIntent().getExtras()).get(FozzaTorreseConstants.NEWS);

        fotoArticolo = findViewById(R.id.dettaglioNewsFoto);
        testoArticolo = findViewById(R.id.dettaglioNewsTesto);
        titoloArticolo = findViewById(R.id.dettaglioNewsTitolo);

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                caricaNotizia();
                return false;
            }
        };

        Looper.myQueue().addIdleHandler(handler);

        Glide.with(this.getApplicationContext())
                .load(notizia.getFoto())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(fotoArticolo);

        titoloArticolo.setText(notizia.getTitolo());
    }

    private void caricaNotizia(){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<News> process = executor.submit(new DettaglioNewsCallable(notizia));

        try {
            notizia = process.get();

            testoArticolo.setText(notizia.getArticolo());

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}