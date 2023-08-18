package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView versione = findViewById(R.id.infoVersioneStringa);

        versione.append(BuildConfig.VERSION_NAME);
    }
}