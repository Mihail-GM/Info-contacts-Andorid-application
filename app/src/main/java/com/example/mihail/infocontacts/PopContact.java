package com.example.mihail.infocontacts;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;


import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PopContact extends AppCompatActivity {
    private static ImageView imageView;
    private static TextView phoneNumer;
    private static TextView phoneName;

    String provider;
    protected LocationManager locationManager;
    public static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1252;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_contact);


        //get data from intent
        final Intent intent = getIntent();
        String phoneNum = intent.getExtras().getString("phoneNumber");
        String names = intent.getExtras().getString("nameContact");
        String imgSource = intent.getExtras().getString("imgSource");



        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;
        DownloaderImage downloaderImage = new DownloaderImage();
        Bitmap bm = downloaderImage.loadBitmap(imgSource, bmOptions);

        phoneName = (TextView) findViewById(R.id.textNamePopUp);
        phoneNumer = (TextView) findViewById(R.id.textPhonePopUP);
        imageView = (ImageView) findViewById(R.id.imagePopUP);
        //set information to pop up
        phoneName.setText(names);
        phoneNumer.setText(phoneNum);
        imageView.setImageBitmap(bm);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //get widh end height
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Double widhSize = width * .9;
        Double heighthSize = height * .7;

        //set pop windows size
        getWindow().setLayout(widhSize.intValue(), heighthSize.intValue());

        phoneNumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneNumer.getText().toString().trim();

                //getting permiton , this is for higher APIs
                if(checkPermission(Manifest.permission.CALL_PHONE)){
                    String number = "tel:" + phoneNumber ;
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                    startActivity(callIntent);
                } else {
                    Toast.makeText(PopContact.this, "Permission Call Phone denied" , Toast.LENGTH_SHORT).show();
                }


                Toast.makeText(getApplicationContext(),"Enable to perform call", Toast.LENGTH_SHORT).show();
            }
        });

        if (checkPermission(Manifest.permission.CALL_PHONE)){

        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }

    }

    private boolean checkPermission(String permision) {
        return ContextCompat.checkSelfPermission(this,permision) == PackageManager.PERMISSION_GRANTED;
    }



}


