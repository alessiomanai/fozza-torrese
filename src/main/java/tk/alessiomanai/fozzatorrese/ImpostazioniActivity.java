package tk.alessiomanai.fozzatorrese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class ImpostazioniActivity extends AppCompatActivity {

    SwitchCompat abilitaImmagini, sorgenteAlternativa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);

        View root = findViewById(R.id.layoutImpostazioni);
        ViewCompat.setOnApplyWindowInsetsListener(root, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                v.setPadding(0, top, 0, 0);
                return insets;
            }
        });

        abilitaImmagini = findViewById(R.id.switchImmagini);
        sorgenteAlternativa = findViewById(R.id.switchSorgenteLive);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(FozzaTorreseConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        boolean loghiSfocati = prefs.getBoolean(FozzaTorreseConstants.OFFUSCAMENTO_LOGHI,
                FozzaTorreseConstants.DEFAULT_VALUE_SETTING_LOGHI);

        SharedPreferences prefsLive = getApplicationContext().getSharedPreferences(FozzaTorreseConstants.SHARED_PREFERENCES_LIVE, Context.MODE_PRIVATE);
        boolean liveSource = prefsLive.getBoolean(FozzaTorreseConstants.SORGENTE_DIRETTA,
                FozzaTorreseConstants.UTILIZZA_131);

        abilitaImmagini.setChecked(!loghiSfocati);
        sorgenteAlternativa.setChecked(liveSource);

        abilitaImmagini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(FozzaTorreseConstants.OFFUSCAMENTO_LOGHI, !loghiSfocati);
                editor.apply();

            }
        });

        sorgenteAlternativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = prefsLive.edit();
                editor.putBoolean(FozzaTorreseConstants.SORGENTE_DIRETTA, !liveSource);
                editor.apply();

            }
        });

    }
}