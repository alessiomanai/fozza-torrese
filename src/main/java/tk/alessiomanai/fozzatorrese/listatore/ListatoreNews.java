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

import java.util.List;

import tk.alessiomanai.fozzatorrese.R;
import tk.alessiomanai.fozzatorrese.model.News;

public class ListatoreNews extends ArrayAdapter<News> {

    private Activity context;
    private List<News> news;

    public ListatoreNews(Activity context, List<News> news) {
        super(context, R.layout.lister_news, news);

        this.context = context;
        this.news = news;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.lister_news, null, true);    //infila il file xml nell' intent

        TextView titolo = rowView.findViewById(R.id.nomeNotiziaLister);
        TextView data = rowView.findViewById(R.id.dataNotiziaLister);
        ImageView foto = rowView.findViewById(R.id.fotoNotiziaLister);

        titolo.setText(news.get(position).getTitolo());
        data.setText(news.get(position).getData());

        Glide.with(context.getApplicationContext())
                .load(news.get(position).getFoto())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(foto);

        return rowView;
    }
}
