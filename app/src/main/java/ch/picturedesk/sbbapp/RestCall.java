package ch.picturedesk.sbbapp;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class RestCall extends AsyncTask<String, Void, String> {

    private InputStreamReader responseBodyReader;

    @Override
    protected String doInBackground(String... strings) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create URL
                URL githubEndpoint = null;

                // Create connection
                try {
                    githubEndpoint = new URL("https://transport.opendata.ch/v1/stationboard?station=Zurich&li");
                    HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                    if (myConnection.getResponseCode() == 200) {
                        InputStream responseBody = myConnection.getInputStream();
                        responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                        int test = myConnection.getResponseCode();
                    } else {
                        // Error handling code goes here
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }

    public InputStreamReader getResponseBodyReader() {
        return responseBodyReader;
    }


}

