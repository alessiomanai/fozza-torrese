package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.CalendarioCallable;
import tk.alessiomanai.fozzatorrese.listatore.ListatoreCalendario;
import tk.alessiomanai.fozzatorrese.model.Calendario;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class CalendarioActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        list = findViewById(R.id.listViewCalendario);

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
        Future<Calendario> process = executor.submit(new CalendarioCallable());

        Calendario doc = null;
        try {
            doc = process.get();
        } catch (ExecutionException | InterruptedException e) {
            FozzaTorreseUtils.error500(CalendarioActivity.this);
            e.printStackTrace();
        }

        ListatoreCalendario adapter = new ListatoreCalendario(this, doc);
        list.setAdapter(adapter);
    }
}