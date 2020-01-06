package com.example.ilyas.titresim_oyunu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class skorlar extends Activity {

    ListView listele_lv;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skorlar_activity);
        listele_lv =(ListView) findViewById(R.id.listele);
        Listele();

    }
    public void Listele(){   //veritabanından gelen bilgileri listeliyoruz
        Database vt = new Database(skorlar.this);
        List<String> list = vt.VeriListele();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(skorlar.this, android.R.layout.simple_list_item_1,android.R.id.text1,list);
        listele_lv.setAdapter(adapter);
    }

}
