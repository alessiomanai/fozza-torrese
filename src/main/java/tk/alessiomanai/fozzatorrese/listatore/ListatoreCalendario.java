package tk.alessiomanai.fozzatorrese.listatore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import tk.alessiomanai.fozzatorrese.R;
import tk.alessiomanai.fozzatorrese.model.Calendario;
import tk.alessiomanai.fozzatorrese.model.PartitaCalendario;

public class ListatoreCalendario extends ArrayAdapter<PartitaCalendario> {

    private Activity context;
    private Calendario calendario;

    public ListatoreCalendario(Activity context, Calendario calendario) {
        super(context, R.layout.lister_classifica, calendario);

        this.context = context;
        this.calendario = calendario;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.lister_calendario, null, true);    //infila il file xml nell' intent

        TextView squadra = rowView.findViewById(R.id.nomeCalendarioLister);
        TextView data = rowView.findViewById(R.id.dataCalendarioLister);
        TextView risultato = rowView.findViewById(R.id.risultatoCalendarioLister);
        TextView orario = rowView.findViewById(R.id.orarioCalendarioLister);

        squadra.setText(calendario.get(position).getSquadraCasa());
        data.setText(calendario.get(position).getData());
        risultato.setText(calendario.get(position).getRisultato());
        orario.setText(calendario.get(position).getOrario());

        if(position % 2 == 0){
            rowView.setBackgroundResource(R.color.blueLighterApp);
        }

        return rowView;
    }
}
