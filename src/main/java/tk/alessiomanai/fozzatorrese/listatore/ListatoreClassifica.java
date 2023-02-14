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

import java.util.List;

import tk.alessiomanai.fozzatorrese.R;
import tk.alessiomanai.fozzatorrese.model.RigaClassifica;
import tk.alessiomanai.fozzatorrese.utils.FozzaTorreseConstants;

public class ListatoreClassifica extends ArrayAdapter<RigaClassifica> {

    private Activity context;
    private List<RigaClassifica> squadra;

    public ListatoreClassifica(Activity context, List<RigaClassifica> squadra) {
        super(context, R.layout.lister_classifica, squadra);

        this.context = context;
        this.squadra = squadra;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.lister_classifica, null, true);    //infila il file xml nell' intent

        TextView txtTitle = rowView.findViewById(R.id.nomeClassificaLister);
        TextView numero = rowView.findViewById(R.id.posizioneClassificaLister);
        TextView punti = rowView.findViewById(R.id.puntiLister);
        TextView vinte = rowView.findViewById(R.id.vittorieLister);
        TextView pareggiate = rowView.findViewById(R.id.pareggiLister);
        TextView perse = rowView.findViewById(R.id.sconfitteLister);
        ImageView foto = rowView.findViewById(R.id.fotoClassificaLister);

        txtTitle.setText(squadra.get(position).getNomeSquadra());
        numero.setText(squadra.get(position).getPosizione());
        punti.setText(squadra.get(position).getPunti());
        vinte.setText(squadra.get(position).getVinte());
        pareggiate.setText(squadra.get(position).getPareggiate());
        perse.setText(squadra.get(position).getPerse());

        SharedPreferences prefs = context.getSharedPreferences(FozzaTorreseConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        boolean loghiSfocati = prefs.getBoolean(FozzaTorreseConstants.OFFUSCAMENTO_LOGHI,
                FozzaTorreseConstants.DEFAULT_VALUE_SETTING_LOGHI);

        if(!loghiSfocati) {
            Glide.with(context.getApplicationContext())
                    .load(squadra.get(position).getStemma())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(foto);
        }

        if(squadra.get(position).getNomeSquadra().contains("Torres")){
            rowView.setBackgroundResource(R.color.redApp);
        } else if(position % 2 == 0){
            rowView.setBackgroundResource(R.color.blueLighterApp);
        }

        return rowView;
    }

}