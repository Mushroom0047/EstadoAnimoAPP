package com.hvm.estadoanimoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hvm.estadoanimoapp.bd.ConexionBD;
import com.hvm.estadoanimoapp.utilidades.Utilidades;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Resultados extends AppCompatActivity {
private ListView lsDatos;
private ArrayList<String> listaInfo;
private ConexionBD con;
private TextView tvFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        con = new ConexionBD(this, "bd_estadoAnimo", null, 1);
        lsDatos = findViewById(R.id.lsDatos);
        tvFecha = findViewById(R.id.tvFechaHoy);
        consultaBD();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfo);
        lsDatos.setAdapter(adaptador);
        getFecha();
    }

    private void getFecha(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy");
        String fd = df.format(c.getTime());
        tvFecha.setText(fd);
    }
    private void consultaBD(){
        SQLiteDatabase db=con.getReadableDatabase();
        listaInfo = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_ANIMO,null);
        String texto =null;
        while (cursor.moveToNext()){
            texto = "Fecha: "+cursor.getString(3)+"\n"+
                    "Ánimo: "+String.valueOf(cursor.getInt(1))+"\n"+
                    "Cansancio: "+String.valueOf(cursor.getInt(2))+"\n";

            listaInfo.add(texto);
        }
    }
}