package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.GiornataAttualeCallable;
import tk.alessiomanai.fozzatorrese.listatore.ListatoreCalendario;
import tk.alessiomanai.fozzatorrese.listatore.ListatoreGiornata;
import tk.alessiomanai.fozzatorrese.model.GiornataCampionato;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class GiornataAttualeActivity extends AppCompatActivity {

    private ListView list;
    private TextView numeroGiornata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giornata_attuale);

        list = findViewById(R.id.listViewGiornata);
        numeroGiornata = findViewById(R.id.numeroGiornata);

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                caricaCalendario();
                return false;
            }
        };

        Looper.myQueue().addIdleHandler(handler);
    }

    private void caricaCalendario(){

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<GiornataCampionato> process = executor.submit(new GiornataAttualeCallable());

        GiornataCampionato doc = null;
        try {
            doc = process.get();
        } catch (ExecutionException | InterruptedException e) {
            FozzaTorreseUtils.error500(GiornataAttualeActivity.this);
            e.printStackTrace();
        }

        numeroGiornata.setText(doc.getNumeroGiornata());

        ListatoreGiornata adapter = new ListatoreGiornata(this, doc);
        list.setAdapter(adapter);
    }
}