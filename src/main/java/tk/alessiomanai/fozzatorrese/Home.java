package tk.alessiomanai.fozzatorrese;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.glide.transformations.BlurTransformation;
import tk.alessiomanai.fozzatorrese.callable.ProssimaPartitaCallable;
import tk.alessiomanai.fozzatorrese.model.ProssimaPartita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class Home extends AppCompatActivity {

    ImageView calendarioButton, classificaButton, liveButton, teamButton, newsButton, giornataButton;
    TextView squadraCasa, squadraTrasferta, dataProssimaPartita;
    ImageView stemmaCasa, stemmaTrasferta;
    View viewStemmaCasa, viewStemmaTrasferta, viewProssimaPartita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EdgeToEdge.enable(this);

        View root = findViewById(R.id.home);
        ViewCompat.setOnApplyWindowInsetsListener(root, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                v.setPadding(0, top, 0, 0);
                return insets;
            }
        });

        calendarioButton = findViewById(R.id.bottoneCalendario);
        classificaButton = findViewById(R.id.bottoneClassifica);
        teamButton = findViewById(R.id.bottoneSquadra);
        liveButton = findViewById(R.id.bottoneLive);
        newsButton = findViewById(R.id.bottoneNews);
        giornataButton = findViewById(R.id.giornataButton);

        squadraCasa = findViewById(R.id.squadraCasaTextView);
        squadraTrasferta = findViewById(R.id.squadraTrasfertaTextView);
        stemmaCasa = findViewById(R.id.stemmaCasa);
        stemmaTrasferta = findViewById(R.id.stemmaTrasferta);
        dataProssimaPartita = findViewById(R.id.dataProssimaPartita);
        viewStemmaCasa = findViewById(R.id.layoutStemmaCasa);
        viewStemmaTrasferta = findViewById(R.id.layoutStemmaTrasferta);
        viewProssimaPartita = findViewById(R.id.prossimaPartitaLayout);

        viewProssimaPartita.setVisibility(View.GONE);

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                caricaProssimaPartita();
                return false;
            }
        };

        Looper.myQueue().addIdleHandler(handler);


        classificaButton.setOnClickListener(arg0 -> {
            Intent activity = new Intent(getBaseContext(), ClassificaActivity.class);
            startActivity(activity);
        });

        liveButton.setOnClickListener(arg0 -> {
            Intent activity = new Intent(getBaseContext(), LiveActivity.class);
            startActivity(activity);
        });

        teamButton.setOnClickListener(arg0 -> {
            Intent activity = new Intent(getBaseContext(), SquadraActivity.class);
            startActivity(activity);
        });

        calendarioButton.setOnClickListener(arg0 -> {
            Intent activity = new Intent(getBaseContext(), CalendarioActivity.class);
            startActivity(activity);
        });

        newsButton.setOnClickListener(arg0 -> {
            Intent activity = new Intent(getBaseContext(), NewsActivity.class);
            startActivity(activity);
        });

        giornataButton.setOnClickListener(arg0 -> {
            Intent activity = new Intent(getBaseContext(), GiornataAttualeActivity.class);
            startActivity(activity);
        });
    }

    private void caricaProssimaPartita(){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ProssimaPartita> process = executor.submit(new ProssimaPartitaCallable());

        ProssimaPartita prossimaPartita = null;
        try {
            prossimaPartita = process.get();
            if (Objects.nonNull(prossimaPartita) && prossimaPartita.getData().compareTo(new Date()) > 0){
                viewProssimaPartita.setVisibility(View.VISIBLE);
                setDettagliProssimaPartita(prossimaPartita);
            }

        } catch (ExecutionException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void setDettagliProssimaPartita(ProssimaPartita prossimaPartita){

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(FozzaTorreseConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        boolean loghiSfocati = prefs.getBoolean(FozzaTorreseConstants.OFFUSCAMENTO_LOGHI,
                FozzaTorreseConstants.DEFAULT_VALUE_SETTING_LOGHI);

        squadraCasa.setText(Objects.nonNull(prossimaPartita.getSquadraCasa()) ? prossimaPartita.getSquadraCasa() : "");
        squadraTrasferta.setText(Objects.nonNull(prossimaPartita.getSquadraTrasferta()) ? prossimaPartita.getSquadraTrasferta() : "");
        dataProssimaPartita.setText(Objects.nonNull(prossimaPartita.getDataOra()) ? prossimaPartita.getDataOra() : "");

        if (loghiSfocati) {

            Glide.with(this)
                    .load(prossimaPartita.getLogoCasa())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 1)))
                    .into(stemmaCasa);

            Glide.with(this)
                    .load(prossimaPartita.getLogoTrasferta())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 1)))
                    .into(stemmaTrasferta);

        } else {

            Glide.with(this)
                    .load(prossimaPartita.getLogoCasa())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(stemmaCasa);

            Glide.with(this)
                    .load(prossimaPartita.getLogoTrasferta())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(stemmaTrasferta);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.about) {

            Intent activity = new Intent(getBaseContext(), Info.class);
            startActivity(activity);

            return true;
        }
        if (id == R.id.settings) {

            Intent activity = new Intent(getBaseContext(), ImpostazioniActivity.class);
            startActivity(activity);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}