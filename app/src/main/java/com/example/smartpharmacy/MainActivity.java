package com.example.smartpharmacy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartpharmacy.Fragments.DBhelper;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    DBhelper dbhelper;

    public ProgressBar progBar;
    public TextView txtNotFound;
    boolean found = false;

    public String urlSite = "https://tabletki.ua/";
    public String medicament = "";

    public Elements namePharmacyElements;
    public Elements addressElements;
    public Elements time_workElements;
    public Elements nameMedicamentElements = new Elements();
    public Elements priceElements = new Elements();

    ArrayList<Pharmacy> pharmaciesList = new ArrayList<Pharmacy>();
    ListPharmacyAdapter listPharmacyAdapter;

    private ListView lv;
    private SearchView searchView;

    private GoogleApiClient client;

    /********** Главная функция программы ***********/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_map = new Intent(view.getContext(), MapsActivity.class);
                startActivity(intent_map);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lv = (ListView) findViewById(R.id.listPharmacy);
        progBar = (ProgressBar) findViewById(R.id.progBar);
        progBar.setVisibility(View.INVISIBLE);
        txtNotFound = (TextView) findViewById(R.id.notFound);
        txtNotFound.setVisibility(View.INVISIBLE);

        dbhelper = new DBhelper(this);//database
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText){
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query){
                /********** Выполнение парсинга ***********/
                new MainActivity.ParsePage().execute(urlSite, query);
                listPharmacyAdapter = new ListPharmacyAdapter(getBaseContext(), pharmaciesList);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_parameters) {
            Intent intent_map = new Intent(this, ActivityParameters.class);
            startActivity(intent_map);
        } else if (id == R.id.nav_map) {
            Intent intent_map = new Intent(this, MapsActivity.class);
            startActivity(intent_map);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /********** Функция парсинга ***********/
    private class ParsePage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            //String cityCode = params[1];
            String nameMedicament = params[1];
            url += nameMedicament + "/pharmacy/";
            found = false;

            try {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla")
                        .cookie("tabletki.region", "0")
                        .get();


                namePharmacyElements = document.select(".p-title a");
                addressElements = document.select(".p-address");
                time_workElements = document.select(".p-schedule");
                Elements name_block = document.select(".p-rest-panel");

                int n = namePharmacyElements.size() - addressElements.size();

                for(int i = n; i < name_block.size(); i++){
                    nameMedicamentElements.add(name_block.get(i).select(".p-rest-uppertext").first());
                    priceElements.add(name_block.get(i).select(".p-rest-price").first());
                }

                pharmaciesList.clear();
                for (int i = n; i < namePharmacyElements.size(); i++) {
                    String price = priceElements.get(i - n).attr("content");
                    if(price == ""){
                        price = "в наличии";
                    }
                    pharmaciesList.add(
                            new Pharmacy(namePharmacyElements.get(i).text(),
                                    addressElements.get(i - n).text(),
                                    "",
                                    nameMedicamentElements.get(i - n).text(),
                                    price)
                    );
                }

                if(pharmaciesList.size() != 0){
                    found = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**********
         * Начало потока
         ***********/
        @Override
        protected void onPreExecute(){
            progBar.setVisibility(View.VISIBLE);
            txtNotFound.setVisibility(View.INVISIBLE);
        }

        /**********
         * Окончание потока
         ***********/
        @Override
        protected void onPostExecute(String result) {
            lv.setAdapter(listPharmacyAdapter);
            progBar.setVisibility(View.INVISIBLE);
            if (!found){
                txtNotFound.setVisibility(View.VISIBLE);
            }
        }
    }
}
