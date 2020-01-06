package com.example.ilyas.titresim_oyunu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class kullanici_bilgileri  extends Activity {
    Button kaydet;
    EditText adi, soyadi;
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kullanici_bilgileri_activity);
        kaydet=(Button)findViewById(R.id.kaydet);  //kaydet butonu
        adi=(EditText)findViewById(R.id.adi);       //adi edittext i
        soyadi=(EditText)findViewById(R.id.soyadi);     //soyadi edittext i
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //adi ve soyadi en az 3 karakter girilmesi gerekiyor

                if (adi.getText().length() < 2 & soyadi.getText().length() <2) {
                    Toast.makeText(getApplicationContext(), "Lütfen Bilgilerinizi Giriniz!!", Toast.LENGTH_SHORT).show(); }
                else {
                    String kullanici_bilgi=(adi.getText().toString()+" "+soyadi.getText().toString());
                   intent = new Intent(kullanici_bilgileri.this, MainActivity.class);
                    intent.putExtra("veri",kullanici_bilgi);
                    veriGonder();
              } }
        });
    }
    private void veriGonder() { //verileri veritabanına kaydedilmesi için gönderiliyor
        Toast.makeText(getApplicationContext(), "Bilgileriniz Kaydedildi", Toast.LENGTH_SHORT).show();//toast mesajı
        Intent girisekrani=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(girisekrani);
        startActivity(intent);
    }
}
