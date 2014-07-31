package com.trademeservices.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trademeservices.app.cat.Categories;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jacksdl2 on 29/07/2014.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "servicesDb";
    private static final int DATABASE_VERSION = 1;
    private Context ctx;

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("out",
                "making 1");
        //Create District table
        db.execSQL("CREATE TABLE regions ( \n" +
                "    id   INT  PRIMARY KEY\n" +
                "              NOT NULL\n" +
                "              UNIQUE,\n" +
                "    name TEXT NOT NULL \n" +
                ");\n");
        //Create Region table
        db.execSQL("CREATE TABLE districts ( \n" +
                "    id       INT  PRIMARY KEY\n" +
                "                  NOT NULL\n" +
                "                  UNIQUE,\n" +
                "    name     TEXT NOT NULL,\n" +
                "    regionId INT  NOT NULL\n" +
                "                  REFERENCES regions ( id ) ON DELETE RESTRICT\n" +
                "                                            ON UPDATE CASCADE \n" +
                ");\n");
        //Create Suburb table
        db.execSQL("CREATE TABLE suburbs ( \n" +
                "    id         INT  PRIMARY KEY\n" +
                "                    NOT NULL\n" +
                "                    UNIQUE,\n" +
                "    name       TEXT NOT NULL,\n" +
                "    districtId INT  NOT NULL\n" +
                "                    REFERENCES districts ( id ) ON DELETE RESTRICT\n" +
                "                                                ON UPDATE CASCADE \n" +
                ");\n");


        //Create AdjacentSuburbs table - lists which suburbs are ajacent - used for advanced searches
        db.execSQL("CREATE TABLE ajacentSub ( \n" +
                "    baseId INT NOT NULL\n" +
                "               REFERENCES suburbs ( id ) ON DELETE RESTRICT\n" +
                "                                         ON UPDATE CASCADE,\n" +
                "    ajSub  INT NOT NULL\n" +
                "               REFERENCES suburbs ( id ) ON DELETE RESTRICT\n" +
                "                                         ON UPDATE CASCADE \n" +
                ");\n");


        //Create Category table
        db.execSQL("CREATE TABLE category ( \n" +
                "    name           TEXT    NOT NULL,\n" +
                "    number         TEXT    PRIMARY KEY\n" +
                "                           NOT NULL\n" +
                "                           UNIQUE,\n" +
                "    path           TEXT    NOT NULL,\n" +
                "    isRestricted   BOOLEAN,\n" +
                "    hasLegal       BOOLEAN,\n" +
                "    hasClassifieds BOOLEAN,\n" +
                "    mainCat        TEXT    NOT NULL \n" +
                ");\n");

        SharedPreferences appInfo = ctx.getSharedPreferences("AppInfo", 0);
        SharedPreferences.Editor editor = appInfo.edit();
        editor.putBoolean("rePopDb", true);
        editor.commit();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop all tables on update
        db.execSQL("DROP TABLE IF EXISTS category");

        db.execSQL("DROP TABLE IF EXISTS ajacentSub");
        db.execSQL("DROP TABLE IF EXISTS regionDistricts");
        db.execSQL("DROP TABLE IF EXISTS districtSuburbs");
        db.execSQL("DROP TABLE IF EXISTS regions");
        db.execSQL("DROP TABLE IF EXISTS districts");
        db.execSQL("DROP TABLE IF EXISTS suburbs");
        onCreate(db);
    }

    public void InsertCat(Categories cat)
    {
        //Log out to logcat the record that is being inserted
        Log.i("out", cat.toString());
        //Get the database on the device
        SQLiteDatabase db = this.getWritableDatabase();
        //Get values from passed in category object and store in ContentValue
        ContentValues values = new ContentValues();
        values.put("name", cat.getName());
        values.put("number", cat.getNumber());
        values.put("path", cat.getPath());
        values.put("isRestricted", cat.isRestricted());
        values.put("hasLegal", cat.isHasLegal());
        values.put("hasClassifieds", cat.GetHasClassifieds());
        values.put("mainCat", cat.getMainCat());
        //Put values into category table
        db.insert("category", null, values);
        db.close();
    }

    public List<Categories> getCat(String mainCat)
    {
        //Make list to hold selected cats
        List<Categories> selectedCat = new ArrayList<Categories>();

        //Make db connection and excecute query
        SQLiteDatabase db=this.getReadableDatabase();
        mainCat.replace("-", "\\-");
        Cursor c = db.rawQuery("SELECT * FROM category WHERE mainCat = '" + mainCat + "'", null);

        //Iterate through all results and insert returned categories into selectedCat
        if (c.moveToFirst())
        {
            do {
                String name = c.getString(0);
                String number = c.getString(1);
                String path = c.getString(2);
                boolean hasClassifieds = (c.getInt(5) == 1);
                boolean hasLegal = (c.getInt(4) == 1);
                boolean isRestricted = (c.getInt(3) == 1);
                String mainCatVal = c.getString(6);
                selectedCat.add(new Categories(name,number,path,hasClassifieds,hasLegal,isRestricted,mainCatVal));
            } while (c.moveToNext());
        }
        db.close();

        //Return the selected categories
        return selectedCat;

    }



}
