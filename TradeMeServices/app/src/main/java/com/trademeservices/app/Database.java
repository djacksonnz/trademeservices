package com.trademeservices.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
        //Create District table
        db.execSQL("CREATE TABLE districts ( \n" +
                "    id   INT  PRIMARY KEY\n" +
                "              NOT NULL\n" +
                "              UNIQUE,\n" +
                "    name TEXT NOT NULL \n" +
                ");\n");
        //Create Region table
        db.execSQL("CREATE TABLE regions ( \n" +
                "    id   INT  PRIMARY KEY\n" +
                "              NOT NULL\n" +
                "              UNIQUE,\n" +
                "    name TEXT NOT NULL \n" +
                ");\n");
        //Create Suburb table
        db.execSQL("CREATE TABLE suburbs ( \n" +
                "    id   INT  PRIMARY KEY\n" +
                "              NOT NULL\n" +
                "              UNIQUE,\n" +
                "    name TEXT NOT NULL \n" +
                ");\n");
        //Create DistrictRegion table - used to specify which districts are associated with which region
        db.execSQL("CREATE TABLE regionDistricts ( \n" +
                "    regionId   INT NOT NULL\n" +
                "                   REFERENCES regions ( id ) ON DELETE RESTRICT\n" +
                "                                             ON UPDATE CASCADE,\n" +
                "    districtId INT NOT NULL\n" +
                "                   REFERENCES districts ( id ) ON DELETE RESTRICT\n" +
                "                                               ON UPDATE CASCADE \n" +
                ");\n");
        //Create DistrictSuburb table - used to specify which suburbs are associated with which district
        db.execSQL("CREATE TABLE districtSuburb ( \n" +
                "    districtId INT NOT NULL\n" +
                "                   REFERENCES districts ( id ) ON DELETE RESTRICT\n" +
                "                                               ON UPDATE CASCADE,\n" +
                "    suburbId   INT NOT NULL\n" +
                "                   REFERENCES suburbs ( id ) ON DELETE RESTRICT\n" +
                "                                             ON UPDATE CASCADE \n" +
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
                "    hasClassifieds BOOLEAN \n" +
                ");\n");
        //Create SubCat table - links cats with other cats to make sub cats
        db.execSQL("CREATE TABLE subCat ( \n" +
                "    catId    TEXT NOT NULL\n" +
                "                  REFERENCES category ( number ) ON DELETE RESTRICT\n" +
                "                                                 ON UPDATE CASCADE,\n" +
                "    subCatId TEXT NOT NULL\n" +
                "                  REFERENCES category ( number ) ON DELETE RESTRICT\n" +
                "                                                 ON UPDATE CASCADE \n" +
                ");");

        SharedPreferences appInfo = ctx.getSharedPreferences("AppInfo", 0);
        SharedPreferences.Editor editor = appInfo.edit();
        editor.putBoolean("rePopDb", true);
        editor.commit();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop all tables on update
        db.execSQL("DROP TABLE IF EXISTS subCat");
        db.execSQL("DROP TABLE IF EXISTS category");

        db.execSQL("DROP TABLE IF EXISTS ajacentSub");
        db.execSQL("DROP TABLE IF EXISTS regionDistricts");
        db.execSQL("DROP TABLE IF EXISTS districtSuburbs");
        db.execSQL("DROP TABLE IF EXISTS regions");
        db.execSQL("DROP TABLE IF EXISTS districts");
        db.execSQL("DROP TABLE IF EXISTS suburbs");
        onCreate(db);
    }

}
