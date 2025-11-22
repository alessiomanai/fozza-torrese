package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.widget.ListView;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tk.alessiomanai.fozzatorrese.callable.ClassificaCallable;
import tk.alessiomanai.fozzatorrese.model.Classifica;
import tk.alessiomanai.fozzatorrese.listatore.ListatoreClassifica;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseUtils;

public class ClassificaActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);

        list = findViewById(R.id.listviewClassifica);

        View root = findViewById(R.id.layoutClassifica);

        ViewCompat.setOnApplyWindowInsetsListener(root, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                v.setPadding(0, top, 0, 0);
                return insets;
            }
        });

        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {

                caricaClassifica();

                return false;
            }
        };

        Looper.myQueue().addIdleHandler(handler);

    }

    private void caricaClassifica(){

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Classifica> process = executor.submit(new ClassificaCallable());

        Classifica doc = null;
        try {
            doc = process.get();
        } catch (ExecutionException | InterruptedException e) {
            FozzaTorreseUtils.error500(ClassificaActivity.this);
            e.printStackTrace();
        }

        ListatoreClassifica adapter = new ListatoreClassifica(ClassificaActivity.this, doc);
        list.setAdapter(adapter);
    }
}