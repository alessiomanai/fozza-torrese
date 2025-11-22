package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.SquadraCallable;
import tk.alessiomanai.fozzatorrese.model.Squadra;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;
import tk.alessiomanai.fozzatorrese.listatore.ListatoreGiocatore;

public class SquadraActivity extends AppCompatActivity {

    private ListView list;
    private Squadra squadra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squadra);

        View root = findViewById(R.id.layoutSquadra);
        ViewCompat.setOnApplyWindowInsetsListener(root, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                v.setPadding(0, top, 0, 0);
                return insets;
            }
        });

        list = this.findViewById(R.id.listaGiocatori);

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {

            @Override
            public boolean queueIdle() {

                caricaSquadra();

                return false;
            }
        };

        Looper.myQueue().addIdleHandler(handler);

    }

    private void caricaSquadra(){

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Squadra> process = executor.submit(new SquadraCallable());

        try {
            squadra = process.get();
        } catch (ExecutionException | InterruptedException e) {
            FozzaTorreseUtils.error500(SquadraActivity.this);
            e.printStackTrace();
        }

        if(Objects.isNull(squadra)){
            FozzaTorreseUtils.error500(SquadraActivity.this);
        } else {
            ListatoreGiocatore adapter = new ListatoreGiocatore(SquadraActivity.this, squadra);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long idonet) {

                    Intent dettagli = new Intent(getBaseContext(), DettaglioGiocatoreActivity.class);

                    dettagli.putExtra(FozzaTorreseConstants.DETTAGLIO_GIOCATORE, squadra.get(position));

                    startActivity(dettagli);
                }
            });
        }
    }
}