package com.example.mihail.infocontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

/**
 * Created by Mihail on 13/06/2017.
 */


public class DbHellperContacts extends SQLiteOpenHelper {
    private static String dbName = "ContactInfo.db";
    private static String tableName =  "Contacts";

    public DbHellperContacts(Context context) {
        super(context, dbName, null, 1);
    }


    private void createDb(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                " (Names VARCHAR, PhoneNumber VARCHAR, ImageSource VARCHAR);");
    }

    public  void insertToDb(TelephoneContact phContact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Names", phContact.getNames());
        values.put("PhoneNumber", phContact.getTelNumber());
        values.put("ImageSource", phContact.getImageRs());
        values.put("bitmapImage", phContact.getImageRs());


        db.insert(tableName,null,values);
    }

    public ArrayList<TelephoneContact> getFromDb(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        int col1 = cursor.getColumnIndex("Names");
        int col2 = cursor.getColumnIndex("PhoneNumber");
        int col3 = cursor.getColumnIndex("ImageSource");

        cursor.moveToFirst();
        ArrayList<TelephoneContact> allContacts = new ArrayList<>();
        if(cursor != null){
            do{
                String name = cursor.getString(col1);
                String phNumber = cursor.getString(col2);
                String imgSrc = cursor.getString(col3);


                TelephoneContact product = new TelephoneContact(imgSrc, name, phNumber);

                allContacts.add(product);

            }while (cursor.moveToNext());
        }

        return  allContacts;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createDb(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
