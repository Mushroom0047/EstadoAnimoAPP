package com.hvm.estadoanimoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hvm.estadoanimoapp.bd.ConexionBD;
import com.hvm.estadoanimoapp.utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private Button btnMasAnimo, btnMenosAnimo, btnMasCans, btnMenosCans,btnGuardar, btnMisEstados;
private TextView tvFecha;
private EditText etAnimo, etCansa;
private int valorAnimo, valorCansancio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
        setValueAnimo(0);
        setValueCansancio(0);
        getFecha();
        ConexionBD con = new ConexionBD(this, "bd_estadoAnimo", null, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnMasAnimo.setOnClickListener(this);
        btnMenosAnimo.setOnClickListener(this);
        btnMasCans.setOnClickListener(this);
        btnMenosCans.setOnClickListener(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtener valor
                getValorAnimo();
                getValorCansancio();
                //comprobar el tamaño de los campos
                if(valorAnimo < 6 && valorAnimo > -6){
                    if(valorCansancio < 6 && valorCansancio > -6){
                        //Obtenemos los valores
                        String a = String.valueOf(getValorAnimo());
                        String c = String.valueOf(getValorCansancio());
                        String f = tvFecha.getText().toString();
                            //guardamos el objeto en array
                            guardarDatos(a, c, f);
                            setValueAnimo(0);
                            setValueCansancio(0);


                    }else{
                        etCansa.setText("0");
                        Toast.makeText(MainActivity.this, "El valor de cansancio supera el rango", Toast.LENGTH_LONG).show();
                    }
                }else{
                    etAnimo.setText("0");
                    Toast.makeText(MainActivity.this, "El valor ánimo supera el rango", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnMisEstados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Resultados.class);
                startActivity(intent);
            }
        });
    }

    public void guardarDatos(String animo, String cansancio, String fecha){
        ConexionBD con = new ConexionBD(this, "bd_estadoAnimo", null, 1);
        SQLiteDatabase db=con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.animo, animo);
        values.put(Utilidades.cansancio, cansancio);
        values.put(Utilidades.fecha, fecha);

        Long idResultante=db.insert(Utilidades.TABLA_ANIMO, Utilidades.campo_id, values);
        Toast.makeText(MainActivity.this, "Estado guardado exitosamente", Toast.LENGTH_LONG).show();
    }

    private void initVar(){
        btnMasAnimo = findViewById(R.id.btnPlusAnimo);
        btnMenosAnimo = findViewById(R.id.btnMinusAnimo);
        btnMasCans = findViewById(R.id.btnPlusCansa);
        btnMenosCans= findViewById(R.id.btnMinusCansa);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMisEstados = findViewById(R.id.btnEstado);
        tvFecha = findViewById(R.id.tvFechaHoy);
        etAnimo = findViewById(R.id.etAnimo);
        etCansa = findViewById(R.id.etCansancio);
        valorAnimo = 0;
        valorCansancio = 0;
    }

    private void getFecha(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy");
        String fd = df.format(c.getTime());
        tvFecha.setText(fd);
    }

    private void setValueAnimo(int i){
        etAnimo.setText(String.valueOf(i));
    }
    private void setValueCansancio(int i){
        etCansa.setText(String.valueOf(i));
    }

    private int getValorAnimo(){
        return valorAnimo = Integer.parseInt(etAnimo.getText().toString());
    }
    private int getValorCansancio(){
        return valorCansancio = Integer.parseInt(etCansa.getText().toString());
    }
    public void onClick(View v){
        getValorAnimo();
        getValorCansancio();

        switch (v.getId()){
            case R.id.btnPlusAnimo:
                if(valorAnimo < 5){
                    setValueAnimo(++valorAnimo);
                }
                break;
            case R.id.btnMinusAnimo:
                if(valorAnimo > -5){
                    setValueAnimo(--valorAnimo);
                }
                break;
            case R.id.btnPlusCansa:
                if(valorCansancio < 5){
                    setValueCansancio(++valorCansancio);
                }
                break;
            case R.id.btnMinusCansa:
                if(valorCansancio > -5){
                    setValueCansancio(--valorCansancio);
                }
                break;
        }
    }
}