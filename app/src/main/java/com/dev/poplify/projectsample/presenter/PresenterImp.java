package com.dev.poplify.projectsample.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dev.poplify.projectsample.activity.MainActivity;
import com.dev.poplify.projectsample.adapter.CustomListAdapter;
import com.dev.poplify.projectsample.model.TuneModel;
import com.dev.poplify.projectsample.view.ViewClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

public class PresenterImp implements Presenter{

    public ViewClass mView;
    public Activity activity;
    public ArrayList<TuneModel> listData;


    public PresenterImp(ViewClass mView, Activity activity, ArrayList<TuneModel> listData ) {
        this.activity = activity;
        this.mView = mView;
        this.listData = listData;
    }

    /***
     *  ececuting asyncTask
     */

    @Override
    public void backgroundTask() {
        new TuneService(activity, "https://itunes.apple.com/search?term=Michael+jackson", new OnTaskDoneListener() {
            @Override
            public void onTaskDone(String responseData) {
                try{
                    JSONObject jsonObj = new JSONObject(responseData);
                    JSONArray ja_data = jsonObj.getJSONArray("results");
                    for(int i=0; i<ja_data.length(); i++){
                        TuneModel myModel = new TuneModel();
                        JSONObject jObj = ja_data.getJSONObject(i);
                        Log.d("TAG", jObj.getString("artworkUrl30"));
                        Log.d("TAG", jObj.getString("artworkUrl60"));
                        Log.d("TAG", jObj.getString("artworkUrl100"));
                        myModel.setArtworkUrl30(jObj.getString("artworkUrl30"));
                        myModel.setArtworkUrl60(jObj.getString("artworkUrl60"));
                        myModel.setArtworkUrl100(jObj.getString("artworkUrl100"));
                        myModel.setTrackName(jObj.getString("trackName"));
                        listData.add(myModel);
                        mView.onBackgroundTaskComplete();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                Log.d("Error", "error");
            }
        }).execute();

    }

    /***
     *  calling Rest API in background
     */
    public class TuneService extends AsyncTask<String, Void, String> {
        private Context mContext;
        private OnTaskDoneListener onTaskDoneListener;
        private String urlStr = "";

        public TuneService(Context context, String url, OnTaskDoneListener onTaskDoneListener) {
            this.mContext = context;
            this.urlStr = url;
            this.onTaskDoneListener = onTaskDoneListener;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL mUrl = new URL(urlStr);
                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("Content-length", "0");
                httpConnection.setUseCaches(false);
                httpConnection.setAllowUserInteraction(false);
                httpConnection.setConnectTimeout(100000);
                httpConnection.setReadTimeout(100000);
                httpConnection.connect();
                int responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (onTaskDoneListener != null && s != null) {
                onTaskDoneListener.onTaskDone(s);
            } else
                onTaskDoneListener.onError();
        }
    }

    /***
     *  interface
     */
    public interface OnTaskDoneListener {
        void onTaskDone(String responseData);
        void onError();
    }
}
