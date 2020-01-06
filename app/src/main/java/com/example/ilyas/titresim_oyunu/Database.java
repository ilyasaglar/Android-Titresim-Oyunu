package com.example.ilyas.titresim_oyunu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "titresim_vt";
    public static final int DATABASE_VERSION = 1;


    public static final String GIRIS_SKOR = "table_skorlar";              	//giris cıkış tablosu satırları
    public static final String ROW_ID_SKOR = "id_skor";
    public static final String ROW_ADI_SOYADI="adi_soyadi";
    public static final String SKOR = "skor";



    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {                                 //tablo oluşturma

        db.execSQL("CREATE TABLE " + GIRIS_SKOR + "(" + ROW_ID_SKOR + " INTEGER PRIMARY KEY, " + ROW_ADI_SOYADI + " TEXT NOT NULL, " + SKOR + " TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {                   //tablo varsa yeniden oluşturma
        db.execSQL("DROP TABLE IF EXISTS " + GIRIS_SKOR);

        onCreate(db);
    }


    public void VeriEkle(String adi,int skor){                                //veri ekleme, verilerimiz kullanıcı_bilgileri clasından geliyor
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_ADI_SOYADI,adi);
            cv.put(SKOR,skor);
            db.insert(GIRIS_SKOR, null,cv);
        }catch (Exception e){
        }
        db.close();
    }



    public List<String> VeriListele(){                                 //veri listeleme
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {ROW_ID_SKOR,ROW_ADI_SOYADI,SKOR};
            Cursor cursor = db.query(GIRIS_SKOR, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                veriler.add(cursor.getInt(0)
                        + " - "
                        + cursor.getString(1)
                        + " - "
                        + cursor.getString(2));




            }
        }catch (Exception e){
        }
        db.close();

        return veriler;
    }


}