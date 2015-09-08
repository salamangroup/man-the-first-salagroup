package com.salagroup.salaman.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.salagroup.salaman.R;
import com.salagroup.salaman.adapter.LoadImageAdapter;
import com.salagroup.salaman.pojo.Product;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String URL_LOADIMAGE = "http://54.255.214.47/api/product/getimage";
    private ArrayList<String> strImageLists = new ArrayList<>();
    private LoadImageAdapter adapter;
    private String test="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gvLoadImage = (GridView) findViewById(R.id.gvLoadImage);
        adapter = new LoadImageAdapter(this, R.layout.custom_item_load_image_layout, strImageLists);
        gvLoadImage.setAdapter(adapter);
        gvLoadImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("trytothuan", "ID: " + position);
            }
        });

        LoadImageAsyncTask imageAsyncTask = new LoadImageAsyncTask();
        imageAsyncTask.execute(URL_LOADIMAGE);

    }

    private class LoadImageAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String urlLoadImage = params[0];
            Response response;

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(urlLoadImage).build();
                response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Gson gson = new Gson();
            Product product = gson.fromJson(json,Product.class);
            strImageLists.addAll(product.getImages());
            adapter.notifyDataSetChanged();
        }
    }
}
