package com.example.ilyas.titresim_oyunu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button button_Basla,skor_Button;
    TextView goster, kullanici_Adi;
    TextView skor;
    Vibrator vib;
    int puanlar;
    int count = 0, score = 0;
    boolean timer = false;
    Database veritabani = null;
    String gelenYazi;

    SensorManager sm = null;
    int xeksen, yeksen, zeksen;
    Sensor accelerometer;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        kullanici_Adi=(TextView)findViewById(R.id.kullanici_adi);                //gelen kullanıcı bilgileri
        gelenYazi=getIntent().getExtras().getString("veri");
        kullanici_Adi.setText(gelenYazi);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        button_Basla = (Button) findViewById(R.id.button);    // buton tanımlamaları
        goster = (TextView) findViewById(R.id.sure);
        skor = (TextView) findViewById(R.id.skor);
        skor_Button=(Button)findViewById(R.id.skor_goster);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sm.SENSOR_DELAY_NORMAL);

        button_Basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Yüksek Puan Almak İçin Telefonu Daha Çok Sallayınız", Toast.LENGTH_LONG).show();
                skor.setText(" ");
                new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        goster.setText(String.valueOf(millisUntilFinished / 1000));       // 10 saniyelik sayacın azalması
                        timer = true;
                    }
                    @Override
                    public void onFinish() {
                        goster.setText("0");
                        timer = false;
                        Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_LONG).show(); //skoru görüntülemek için toast mesajı
                        if (score < count) {
                            score = count;
                        }
                        skor.setText(String.valueOf(score));
                        puanlar=Integer.parseInt(skor.getText().toString());   //parse işlemi
                        skorlar_method();
                        count = 0;
                    }
                }.start();
            }
        });
        skor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                        //vt skoru kaydetme
                Intent girisekrani=new Intent(getApplicationContext(),skorlar.class);
                startActivity(girisekrani);
            }
        });
    }

    public void skorlar_method(){               //vt girişten gelen ad soyad  ve burada kazanılan skor bilgilerini gonderme
        Database vt = new Database(MainActivity.this);
        vt.VeriEkle(gelenYazi,puanlar);
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xeksen = Integer.parseInt((Integer.toString((int) event.values[0])));
            yeksen = Integer.parseInt((Integer.toString((int) event.values[1])));
            zeksen = Integer.parseInt((Integer.toString((int) event.values[2])));
            if (xeksen >= 3) {                                     //eşik noktamızı geçince ne kadar puan alacagını burada belirliyoruz
                if (timer == true) vib.vibrate(300);{
                    count=count+5;                                 //x eşigini geçince +5 şeklinde puan kazanıyor
                }
            }
            if (yeksen >= 4) {
                if (timer == true)
                    vib.vibrate(300);{
                    count=count+10;                          //y eşigini geçince +10 şeklinde puan kazanıyor
                }
            }
            if (zeksen >= 5) {
                if (timer == true)
                    vib.vibrate(300);{
                    count=count+2;                        //z eşigini geçince +5 şeklinde puan kazanıyor
                }
            }
        }
    }

    public void onBackPressed() {                   //uygulamadan çıkış için onay ekranı çıkıyor.

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Çıkış")
                .setMessage("Uygulamadan çıkmak istediğinizden emin misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);;
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                }).setNegativeButton("Hayır", null).show();

    }

}
