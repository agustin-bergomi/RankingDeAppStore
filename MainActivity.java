package com.agustinbergomi.lasmejores10apps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//EL SIGUIENTE PROGRAMA FUE REALIZADO SIGUIENTO EL PASO A PASO DE UN CURSO
//la aplicacion permitie ver informacion de un RSS que provee apple de las aplicaciones del appstore
//los comentarios son excesivos a proposito

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listApps = (ListView) findViewById(R.id.xmlListView);

        Log.d(TAG, "onCreate: starting Asynctask");
        //creo una instancia de Download Data
        DownloadData downloadData = new DownloadData();
        //el onCreate llama al metodo execute aplicado al objeto de tipo download Data
        //entonces se comienza a ejecutar do in background.
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=25/xml");
        Log.d(TAG, "onCreate: done");

        }

        //se declara una inner class (dentro de main activity)
        //se podria delcarar en una clase aparte
        //async task esta deprecado, lo uso aca para ver el funcionamiento
        //async task toma 3 parametros: url, progreso e info a retornar
        private class DownloadData extends AsyncTask<String, Void, String> {
            private static final String TAG = "DownloadData";

            //tarea que se va a ejecutar cuando finaliza do in background
            //se realiza en el uithread
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "onPostExecute: parameter is " + s);
                ParseApplications parseApplications = new ParseApplications();
                parseApplications.parse(s);

                //Creo un objeto de tipo ArrayAdapter
                //El array adapter toma como parametros el contexto, un layout que represente un item y objetos a representar en el listview.
                //applications se refiere a las aplicaciones que se van a obtener del listado de aplicaciones de internet
                //el adapter envia el texto antes de enviar el item al list view
                //el adapter va a tomar cualquier tipo de view incluso views anidadas
//                ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//                listApps.setAdapter(arrayAdapter);

                FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, parseApplications.getApplications());
                listApps.setAdapter(feedAdapter);

            }

            //tarea que se va a ejecutar en el background
            @Override
            //la notacion de 3 puntos es de java para cantidad variable de args
            protected String doInBackground(String... strings) {
                Log.d(TAG, "doInBackground: starts with " + strings[0]);
                String rssFeed = downloadXML(strings[0]);
                if(rssFeed == null){
                    Log.e(TAG, "doInBackground: Error downloading");
                }
                return "rssFeed";
            }

            private String downloadXML(String urlPath){
                StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);
//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    int charsRead;
                    char[] inputBuffer = new char[500];
                    while(true) {
                        charsRead = reader.read(inputBuffer);
                        if(charsRead < 0) {
                            break;
                        }
                        if(charsRead > 0) {
                            xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                        }
                    }
                    //cierro buffer. se cierran los demas streams por cascada
                    reader.close();

                } catch (MalformedURLException e) {
                    Log.e(TAG, "downloadXML: InvalidURL " + e.getMessage());
                }catch (IOException e){
                    Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
                } catch(SecurityException e){
                    Log.e(TAG, "downloadXML: Security Exception. Needs permission? " + e.getMessage());
//                    e.printStackTrace();
                }

                return null;

            }

        }

    }
