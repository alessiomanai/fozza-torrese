package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.LiveCallable;
import tk.alessiomanai.fozzatorrese.callable.live.FormazioneLiveCallable;
import tk.alessiomanai.fozzatorrese.callable.live.PunteggioLiveCallable;
import tk.alessiomanai.fozzatorrese.listatore.ListatoreCronaca;
import tk.alessiomanai.fozzatorrese.model.Partita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class LiveActivity extends AppCompatActivity {

    private TextView squadraCasa, squadraTrasferta, punteggioCasa, punteggioTrasferta,
            minutoDiGioco, formazioneCasa, formazioneTrasferta, marcatori;
    private Partita partitaLive, formazioneLive, punteggioLive;
    private ListView listviewCronaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        View root = findViewById(R.id.layoutLive);

        ViewCompat.setOnApplyWindowInsetsListener(root, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                v.setPadding(0, top, 0, 0);
                return insets;
            }
        });

        squadraCasa = findViewById(R.id.squadraHomeLive);
        squadraTrasferta = findViewById(R.id.squadraAwayLive);
        punteggioCasa = findViewById(R.id.punteggioHomeLive);
        punteggioTrasferta = findViewById(R.id.punteggioAwayLive);
        minutoDiGioco = findViewById(R.id.minutoLive);
        formazioneCasa = findViewById(R.id.formazioneHomeLive);
        formazioneTrasferta = findViewById(R.id.formazioneAwayLive);
        marcatori = findViewById(R.id.marcatoriLive);
        listviewCronaca = findViewById(R.id.cronacaList);

        caricaPartita();

    }

    private void caricaPartita(){

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {

                ExecutorService executor = Executors.newFixedThreadPool(2);
                Future<Partita> process = executor.submit(new LiveCallable());

                try {
                    partitaLive = process.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                if(Objects.nonNull(partitaLive)) {

                    squadraCasa.setText(partitaLive.getSquadraCasa());
                    squadraTrasferta.setText(partitaLive.getSquadraTrasferta());
                    punteggioCasa.setText(partitaLive.getSquadraCasaPunteggio());
                    punteggioTrasferta.setText(partitaLive.getSquadraTrasfertaPunteggio());
                    minutoDiGioco.setText(partitaLive.getTempo());
                    formazioneCasa.setText(partitaLive.getFormazioneCasa());
                    formazioneTrasferta.setText(partitaLive.getFormazioneTrasferta());
                    marcatori.setText(partitaLive.getMarcatori());

                    ListatoreCronaca listatoreCronaca = new ListatoreCronaca(LiveActivity.this, partitaLive.getCronaca());
                    listviewCronaca.setAdapter(listatoreCronaca);

                } else {
                    FozzaTorreseUtils.noLive(LiveActivity.this);
                }
                return false;
            }
        };

        MessageQueue.IdleHandler punteggioHandler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {

                ExecutorService executor = Executors.newFixedThreadPool(2);
                Future<Partita> punteggioFuture = executor.submit(new PunteggioLiveCallable());

                try {
                    punteggioLive = punteggioFuture.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                if(Objects.nonNull(punteggioLive)) {

                    squadraCasa.setText(punteggioLive.getSquadraCasa());
                    squadraTrasferta.setText(punteggioLive.getSquadraTrasferta());
                    punteggioCasa.setText(punteggioLive.getSquadraCasaPunteggio());
                    punteggioTrasferta.setText(punteggioLive.getSquadraTrasfertaPunteggio());
                    minutoDiGioco.setText(punteggioLive.getTempo());

                } else {
                    FozzaTorreseUtils.noLive(LiveActivity.this);
                }
                return false;
            }
        };

        MessageQueue.IdleHandler formazioneHandler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {

                ExecutorService executor = Executors.newFixedThreadPool(2);
                Future<Partita> formazioneFuture = executor.submit(new FormazioneLiveCallable());

                try {
                    formazioneLive = formazioneFuture.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                if(Objects.nonNull(formazioneLive)) {

                    formazioneCasa.setText(formazioneLive.getFormazioneCasa());
                    formazioneTrasferta.setText(formazioneLive.getFormazioneTrasferta());
                    marcatori.setText(formazioneLive.getMarcatori());

                }
                return false;
            }
        };

        SharedPreferences prefsLive = getApplicationContext().getSharedPreferences(FozzaTorreseConstants.SHARED_PREFERENCES_LIVE, Context.MODE_PRIVATE);
        boolean liveSource = prefsLive.getBoolean(FozzaTorreseConstants.SORGENTE_DIRETTA,
                FozzaTorreseConstants.UTILIZZA_131);

        if(liveSource){
            Looper.myQueue().addIdleHandler(punteggioHandler);
            Looper.myQueue().addIdleHandler(formazioneHandler);
        } else {
            Looper.myQueue().addIdleHandler(handler);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_live, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.refresh) {
            caricaPartita();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}