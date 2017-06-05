package com.example.smartpharmacy;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;

public class MainActivityOld extends Activity implements View.OnClickListener {

    public Elements namePharmacy;
    public ArrayList<String> titleList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView lv;
    private Button btnSearch;
    private Button btnMap;
    private Button btnAddParameters;
    private EditText txtSearch;

    private GoogleApiClient client;

    /********** Главная функция программы ***********/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        lv = (ListView) findViewById(R.id.listPharmacy);
//        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);
        btnAddParameters = (Button) findViewById(R.id.btnAddParameters);
        btnAddParameters.setOnClickListener(this);
        //txtSearch = (EditText)findViewById(R.id.txtSearch);
        String urlSite = "https://tabletki.ua/";
        new ParsePage().execute(urlSite);

        //adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.pro_item, titleList);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /********** Открытие новой формы ***********/
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btnAddParameters:
                Intent intent = new Intent(this, ActivityParameters.class);
                startActivity(intent);
                break;
            case R.id.btnMap:
                Intent intent_map = new Intent(this, MapsActivity.class);
                startActivity(intent_map);
                break;
            default: break;
        }
    }

//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Main Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }

    //@Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }


    /********** Функция парсинга ***********/
    private class ParsePage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            //String cityCode = params[1];
            String nameMedicametn = "Анальгин";
            url += nameMedicametn + "/pharmacy/";
            try {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla")
                        .cookie("tabletki.region", "0")
                        .get();

                namePharmacy = document.select(".p-title a");
                titleList.clear();
                for (Element names : namePharmacy) {
                    titleList.add(names.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        /********** Запуск потока ***********/
        @Override
        protected void onPostExecute(String result) {
            lv.setAdapter(adapter);
        }
    }
}
