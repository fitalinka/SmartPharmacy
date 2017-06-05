package com.example.smartpharmacy.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Александр on 30.05.2017.
 */


public class DBhelper extends SQLiteOpenHelper {
    public static final int  DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="smartpharmacy";
    public static final String TABLE_ACTIV ="activesubstr";
    public static final String TABLE_ACTION ="action";
    public static final String TABLE_FORM ="form";
    public static final String TABLE_COUNTRY ="country";
    public static final String TABLE_MAKER="maker";
    public static final String TABLE_ODIN ="odin";
    public static final String TABLE_DOSAGE ="dosage";
    public static final String TABLE_MEDIC="medic";
    public static final String TABLE_CITY="city";




    public static final String KEY_ACTIV_ID ="id_active";
    public static final String KEY_ACTIV_NAME="name_active";

    public static final String KEY_ACT_ID ="id_action";
    public static final String KEY_ACT_NAME ="action_name";

    public static final String KEY_FORM_ID ="id_form";
    public static final String KEY_FORM_NAME ="form_name";

    public static final String KEY_COUNTRY_ID ="country_id";
    public static final String KEY_COUNTRY_NAME ="country_name";

    public static final String KEY_MAKER_ID ="maker_id";
    public static final String KEY_NAME_MAKER ="name_maker";
    public static final String KEY_COUNTRY ="id_country";


    public static final String KEY_ODIN_ID ="id_odin";
    public static final String KEY_ODIN_NAME ="od_name";

    public static final String KEY_DOSAGE_ID ="id_dosage";
    public static final String KEY_DOSAGE ="dosage";
    public static final String KEY_FORM ="id_form";
    public static final String KEY_ODIN ="odin";


    public static final String KEY_MEDIC_ID ="id_medic";
    public static final String KEY_ACTION ="action";
    public static final String KEY_ACTIVE ="active";
    public static final String KEY_MAKE="make";
    public static final String KEY_MEDIC_NAME ="medic_name";
   public static final String KEY_MFORM = "medic_form";
   public static final String KEY_MDOSE="dose";

   public static final String KEY_CITY_ID ="id_city";
   public static final String KEY_CITY_NAME="name_city";
   public static final String KEY_CITY_ANG ="ang_city";
   public static final String KEY_CITY_KO ="ko_city";



//    public DBhelper(Context context, String name, int version ){
//        super(context,name,null,version);
//}
    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
@Override
public void onCreate(SQLiteDatabase db){
db.execSQL("create table" + TABLE_ACTIV + "("+ KEY_ACTIV_ID + "integer primary key autoincrement," + KEY_ACTIV_NAME + "text"+")");
    db.execSQL("create table" + TABLE_ACTION + "("+ KEY_ACT_ID + "integer primary key ," + KEY_ACT_NAME + "text"+")");
    db.execSQL("create table" + TABLE_FORM + "("+ KEY_FORM_ID + "integer primary key ," + KEY_FORM_NAME + "text"+")");
    db.execSQL("create table" + TABLE_COUNTRY + "("+ KEY_COUNTRY_ID + "integer primary key ," + KEY_COUNTRY_NAME + "text"+")");
    db.execSQL("create table" + TABLE_MAKER + "("+ KEY_MAKER_ID + "integer primary key ," + KEY_NAME_MAKER + "text," +"foreign key("+ KEY_COUNTRY+") references"+TABLE_CITY+ "("+KEY_COUNTRY_ID+")"+")");
    db.execSQL("create table" + TABLE_ODIN + "("+ KEY_ODIN_ID + "integer primary key autoincrement," + KEY_ODIN_NAME + "text"+")");
    db.execSQL("create table" + TABLE_DOSAGE + "("+ KEY_DOSAGE_ID + "integer primary key ," + KEY_DOSAGE + "text," +"foreign key("+ KEY_FORM+") references"+TABLE_FORM+ "("+KEY_FORM_ID+")"+"foreign key("+ KEY_ODIN+") references"+TABLE_ODIN+ "("+KEY_ODIN_ID+")"+")");
    db.execSQL("create table" + TABLE_MEDIC + "("+ KEY_MEDIC_ID + "integer primary key ," + KEY_MEDIC_NAME+ "text," +"foreign key("+ KEY_MFORM+") references"+TABLE_FORM+ "("+KEY_FORM_ID+")"+"foreign key("+ KEY_MDOSE+") references"+TABLE_DOSAGE+ "("+KEY_DOSAGE_ID+")"+"foreign key("+ KEY_ACTION+") references"+TABLE_ACTION+ "("+KEY_ACT_ID+")"+"foreign key("+ KEY_ACTIVE+") references"+TABLE_ACTIV+ "("+KEY_ACTIV_ID+")"+"foreign key("+ KEY_MAKE+") references"+TABLE_MAKER+ "("+KEY_MAKER_ID+")"+")");
    db.execSQL("create table" + TABLE_CITY + "("+ KEY_CITY_ID + "integer primary key autoincrement," + KEY_CITY_NAME + "text" + KEY_CITY_ANG + "text"+ KEY_CITY_KO + "text"+")");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion , int newVersion){
        db.execSQL("drop table if exist"+ TABLE_CITY);
        db.execSQL("drop table if exist"+ TABLE_FORM);
        db.execSQL("drop table if exist"+ TABLE_ACTIV);
        db.execSQL("drop table if exist"+ TABLE_ACTION);
        db.execSQL("drop table if exist"+ TABLE_DOSAGE);
        db.execSQL("drop table if exist"+ TABLE_ODIN);
        db.execSQL("drop table if exist"+ TABLE_MEDIC);
        db.execSQL("drop table if exist"+ TABLE_COUNTRY);
        db.execSQL("drop table if exist"+ TABLE_MAKER);
        onCreate(db);

    }
}
