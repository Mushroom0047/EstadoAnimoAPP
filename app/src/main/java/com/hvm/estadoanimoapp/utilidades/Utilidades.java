package com.hvm.estadoanimoapp.utilidades;

public class Utilidades {
    //constantes
    public static final String TABLA_ANIMO="estadoAnimo";
    public static final String animo = "animo";
    public static final String cansancio = "cansancio";
    public static final String fecha = "fecha";
    public static final String campo_id = "id";

    public static final String CREAR_TABLA = "CREATE TABLE "+TABLA_ANIMO+" ( "+campo_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+animo+" INTEGER NOT NULL, "+cansancio+" INTEGER NOT NULL, "+fecha+" TEXT NOT NULL)";
}
