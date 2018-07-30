package ch.picturedesk.sbbapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import calculator.picturedesk.ch.sbbapp.R;

public class MainActivity extends AppCompatActivity {

    private ListViewAdapter trainListAdapter;

    private String endpoint = "https://transport.opendata.ch/v1/stationboard?station=%s&li";
    private Context ctx;
    private ArrayList<Train> data;
    private List<String> trainstations;
    private ListView listView;
    public static final String EXTRA_DATA = "extraData";
    private Spinner trainstationSpinner;
    private Boolean init = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.user_list);
        trainstationSpinner = (Spinner) findViewById(R.id.trainstation_spinner);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        data = new ArrayList<>();
        ctx = getApplicationContext();

        if(!init) {
            trainstations = new ArrayList<String>();
            trainstations.add("Zürich");
            trainstations.add("Luzern");
            trainstations.add("Lausanne");

            ArrayAdapter<String> trainstationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trainstations);
            trainstationSpinner.setAdapter(trainstationAdapter);

            // Spinner click listener
            trainstationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    serverCall(trainstations.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
        }

        serverCall(trainstations.get(0));
    }

    protected void serverCall(String trainstation) {
        progressBar.setVisibility(View.VISIBLE);

        if(init) {
            data.clear();
            trainListAdapter.notifyDataSetChanged();
        }
        //create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //define a request. You can also define the methods onPostsLoaded //and onPostsError as anonymous inner classes.
        StringRequest request =
                new StringRequest(Request.Method.GET, getEndpoint(trainstation), onPostsLoaded, onPostsError);
        //add the call to the request queue
        requestQueue.add(request);
        Log.i("SELECTED:", trainstation);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            buildGui(response);
            progressBar.setVisibility(View.GONE);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) { Log.e("PostActivity", error.toString());
        }
    };

    public void buildGui(String response) {

        Gson gson = new Gson();
        Stationboard m = gson.fromJson(response, Stationboard.class);

        for (Train train : m.stationboard) {
            data.add(train);
        }

        trainListAdapter = new ListViewAdapter(ctx, data);
        listView.setAdapter(trainListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //create the intent to our detail activity
            Intent detailActivity = new Intent(MainActivity.this, DetailActivity.class);
            detailActivity.putExtra(EXTRA_DATA, data.get(i));
            startActivity(detailActivity);
            Log.i("CLICKED", data.get(i).getName());
            }
        });
        init = true;
    }

    public String getEndpoint(String trainstation) {
        return String.format(endpoint,trainstation.toString());
    }
}
