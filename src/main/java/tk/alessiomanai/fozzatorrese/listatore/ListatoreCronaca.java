package tk.alessiomanai.fozzatorrese.listatore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tk.alessiomanai.fozzatorrese.R;


public class ListatoreCronaca extends ArrayAdapter<String> {

    private Activity context;
    private List<String> cronaca;

    public ListatoreCronaca(Activity context, List<String> cronaca) {
        super(context, R.layout.lister_cronaca, cronaca);

        this.context = context;
        this.cronaca = cronaca;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lister_cronaca, null, true);

        TextView testo = rowView.findViewById(R.id.testoCronaca);
        testo.setText(cronaca.get(position));

        return rowView;
    }

}
