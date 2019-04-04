package com.dev.poplify.projectsample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dev.poplify.projectsample.R;
import com.dev.poplify.projectsample.model.TuneModel;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/***
 *  List adapter to show content in the list
 */

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<TuneModel> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, ArrayList<TuneModel> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.img_a = (ImageView) convertView.findViewById(R.id.img_a);
            holder.img_b = (ImageView) convertView.findViewById(R.id.img_b);
            holder.img_c = (ImageView) convertView.findViewById(R.id.img_c);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (holder.img_a != null) {
            new ImageDownloaderTask(holder.img_a).execute(listData.get(position).getArtworkUrl30());
        }

        if (holder.img_b != null) {
            new ImageDownloaderTask(holder.img_b).execute(listData.get(position).getArtworkUrl60());
        }


        if (holder.img_c != null) {
            new ImageDownloaderTask(holder.img_c).execute(listData.get(position).getArtworkUrl100());
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView img_a;
        ImageView img_b;
        ImageView img_c;
    }



    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public ImageDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }


        /***
         *  download images asynchronously
         */
        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode != 200) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}
