package tk.alessiomanai.fozzatorrese.listatore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import tk.alessiomanai.fozzatorrese.R;
import tk.alessiomanai.fozzatorrese.model.GiornataCampionato;
import tk.alessiomanai.fozzatorrese.model.Partita;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class ListatoreGiornata extends ArrayAdapter<Partita> {

    private Activity context;
    private GiornataCampionato calendario;

    public ListatoreGiornata(Activity context, GiornataCampionato calendario) {
        super(context, R.layout.lister_giornata, calendario);

        this.context = context;
        this.calendario = calendario;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.lister_giornata, null, true);    //infila il file xml nell' intent

        TextView squadra = rowView.findViewById(R.id.nomeGiornataLister);
        TextView risultato = rowView.findViewById(R.id.risultatoGiornataLister);
        ImageView fotoCasa = rowView.findViewById(R.id.fotoCasaGiornata);
        ImageView fotoTrasferta = rowView.findViewById(R.id.fotoTrasfertaGiornata);

        squadra.setText(calendario.get(position).getSquadraCasa() + " - " + calendario.get(position).getSquadraTrasferta());
        risultato.setText(calendario.get(position).getRisultato());

        SharedPreferences prefs = context.getSharedPreferences(FozzaTorreseConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        boolean loghiSfocati = prefs.getBoolean(FozzaTorreseConstants.OFFUSCAMENTO_LOGHI,
                FozzaTorreseConstants.DEFAULT_VALUE_SETTING_LOGHI);

        if(!loghiSfocati) {
            Glide.with(context.getApplicationContext())
                    .load(calendario.get(position).getLogoCasa())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(fotoCasa);

            Glide.with(context.getApplicationContext())
                    .load(calendario.get(position).getLogoTrasferta())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(fotoTrasferta);
        }

        if(position % 2 == 0){
            rowView.setBackgroundResource(R.color.blueLighterApp);
        }

        return rowView;
    }

}
