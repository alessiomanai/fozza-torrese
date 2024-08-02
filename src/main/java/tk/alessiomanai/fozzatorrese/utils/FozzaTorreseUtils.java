package tk.alessiomanai.fozzatorrese.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;

public class FozzaTorreseUtils {

    public static void noInternet(Context context){
        new AlertDialog.Builder(context)
                .setTitle("Nessuna connessione ad internet")
                .setMessage("Non è stato possibile effettuare il caricamento. Controllare la propria connessione.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    public static void noLive(Context context){
        new AlertDialog.Builder(context)
                .setTitle("Non ci sono partite in corso")
                .setMessage("Non è stato possibile effettuare il caricamento")


                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    public static void error500(Context context){
        new AlertDialog.Builder(context)
                .setTitle("Error 500")
                .setMessage("Non è stato possibile effettuare il caricamento")


                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    public static Element getRigaPartita(Document doc){

        int partitaTarget = 0;

        Element partiteLive = doc.getElementsByClass("livescore").get(1);
        Elements righePartite = partiteLive.select("tr");

        for(int i=0; i < righePartite.size(); i++){
            if(righePartite.get(i).text().contains("Torres")){
                partitaTarget = i;
                break;
            }
        }

        return righePartite.get(partitaTarget);
    }

    public static String getSaisonId(){
        Integer year = Calendar.getInstance().get(Calendar.YEAR);

        if (Calendar.getInstance().get(Calendar.MONTH) > 7) {
            return year.toString();
        }
        return (--year).toString();
    }
}
