package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.DettagliGiocatoreCallable;
import tk.alessiomanai.fozzatorrese.callable.SquadraCallable;
import tk.alessiomanai.fozzatorrese.model.DettaglioGiocatore;
import tk.alessiomanai.fozzatorrese.model.Giocatore;
import tk.alessiomanai.fozzatorrese.model.Squadra;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class DettaglioGiocatoreActivity extends AppCompatActivity {

    private ImageView fotoGiocatore;
    private TextView nomeGiocatore;
    private TextView dataNascita, luogoNascita, eta, altezza, nazionalita,
            posizione, piede, inRosaDal, scadenzaContratto, valoreMercato, indisponibilitaText;
    private View indisponibilitaView;
    private Giocatore giocatore;
    private DettaglioGiocatore dettaglioGiocatore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_giocatore);

        fotoGiocatore = findViewById(R.id.dettaglioFotoGiocatore);
        nomeGiocatore = findViewById(R.id.dettaglioNomeGiocatore);
        indisponibilitaView = findViewById(R.id.indisponibilitaView);
        dataNascita = findViewById(R.id.dataNascitaDettaglio);
        luogoNascita = findViewById(R.id.luogoNascitaDettaglio);
        eta = findViewById(R.id.etaDettaglio);
        altezza = findViewById(R.id.altezzaDettaglio);
        nazionalita = findViewById(R.id.nazionalitaDettaglio);
        posizione = findViewById(R.id.posizioneDettaglio);
        piede = findViewById(R.id.piedeDettaglio);
        inRosaDal = findViewById(R.id.inRosaDalDettaglio);
        scadenzaContratto = findViewById(R.id.scadenzaContrattoDettaglio);
        valoreMercato = findViewById(R.id.valoreMercatoDettaglio);
        indisponibilitaText = findViewById(R.id.indisponibilitaText);

        indisponibilitaView.setVisibility(View.GONE);

        giocatore = (Giocatore) getIntent().getExtras().get(FozzaTorreseConstants.DETTAGLIO_GIOCATORE);

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {

            @Override
            public boolean queueIdle() {

                caricaDettaglioGiocatore();

                return false;
            }
        };

        Looper.myQueue().addIdleHandler(handler);

        Glide.with(getApplicationContext().getApplicationContext())
                .load(giocatore.getFoto())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(fotoGiocatore);

        nomeGiocatore.setText(giocatore.getNome());
    }

    private void caricaDettaglioGiocatore(){

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Giocatore> process = executor.submit(new DettagliGiocatoreCallable(giocatore.getUrlDettagli()));

        try {
            giocatore = process.get();
            dettaglioGiocatore = (DettaglioGiocatore) giocatore;
        } catch (ExecutionException | InterruptedException e) {
            FozzaTorreseUtils.error500(DettaglioGiocatoreActivity.this);
            e.printStackTrace();
        }

        if(Objects.nonNull(dettaglioGiocatore)) {
            setLayout();
        }
    }

    private void setLayout(){

        if(Objects.nonNull(dettaglioGiocatore.getCausaAssenza())){
            indisponibilitaText.setText(dettaglioGiocatore.getCausaAssenza());
            indisponibilitaView.setVisibility(View.VISIBLE);
        }

        dataNascita.setText(dettaglioGiocatore.getDataNascita());
        luogoNascita.setText(dettaglioGiocatore.getLuogoNascita());
        eta.setText(dettaglioGiocatore.getEta());
        altezza.setText(dettaglioGiocatore.getAltezza());
        nazionalita.setText(dettaglioGiocatore.getNazionalita());
        posizione.setText(dettaglioGiocatore.getPosizione());
        piede.setText(dettaglioGiocatore.getPiede());
        inRosaDal.setText(dettaglioGiocatore.getInRosaDa());
        scadenzaContratto.setText(dettaglioGiocatore.getScadenzaContratto());
        valoreMercato.setText(dettaglioGiocatore.getValoreDiMercato());

    }
}