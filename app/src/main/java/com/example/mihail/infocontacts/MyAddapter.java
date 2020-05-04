package com.example.mihail.infocontacts;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.StrictMode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mihail on 13/06/2017.
 */

public class MyAddapter extends BaseAdapter {
    private ArrayList<TelephoneContact> telList;
    private Context context;

    public MyAddapter(ArrayList<TelephoneContact> telList, Context context) {
        this.telList = telList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return telList.size();
    }

    @Override
    public Object getItem(int i) {
        return telList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;
        ImageView img;
        TextView txNames;
        TextView txNumber;

        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.activity_list_view_item_contacts,null);
        }else {
            rowView = view;
        }

        img = (ImageView) rowView.findViewById(R.id.imageView);
        txNames = (TextView)rowView.findViewById(R.id.textView2);
        txNumber = (TextView)rowView.findViewById(R.id.textView3);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Get image from web
        String url = telList.get(i).getImageRs();
        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;
        DownloaderImage downloaderImage = new DownloaderImage();
        Bitmap bm = downloaderImage.loadBitmap(url, bmOptions);

        //Set current row view
        img.setImageBitmap(bm);
        txNames.setText(telList.get(i).getNames());
        txNumber.setText(telList.get(i).getTelNumber());


        return rowView;
    }



}
