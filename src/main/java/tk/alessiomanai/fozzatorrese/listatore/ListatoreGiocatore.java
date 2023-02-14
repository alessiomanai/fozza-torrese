package tk.alessiomanai.fozzatorrese.listatore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import tk.alessiomanai.fozzatorrese.R;
import tk.alessiomanai.fozzatorrese.model.Giocatore;
import tk.alessiomanai.fozzatorrese.model.Squadra;

public class ListatoreGiocatore extends ArrayAdapter<Giocatore> {

    private Activity context;
    private Squadra squadra;

    public ListatoreGiocatore(Activity context, Squadra squadra) {
        super(context, R.layout.lister_giocatore, squadra);

        this.context = context;
        this.squadra = squadra;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.lister_giocatore, null, true);    //infila il file xml nell' intent

        TextView txtTitle = rowView.findViewById(R.id.nomeLister);
        TextView numero = rowView.findViewById(R.id.numeroLister);
        TextView ruolo = rowView.findViewById(R.id.ruoloLister);
        ImageView foto = rowView.findViewById(R.id.fotoLister);

        txtTitle.setText(squadra.get(position).getNome());
        numero.setText(squadra.get(position).getNumero());
        ruolo.setText(squadra.get(position).getRuolo());

        Glide.with(context.getApplicationContext())
                .load(squadra.get(position).getFoto())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(foto);

        return rowView;
    }

}