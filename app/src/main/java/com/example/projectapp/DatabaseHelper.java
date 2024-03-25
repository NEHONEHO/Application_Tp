package com.example.projectapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Nom de la base de données
    private static final String DATABASE_NAME = "annonces_db";
    // Version de la base de données
    private static final int DATABASE_VERSION = 1;

    // Noms de table et colonnes
    public static final String TABLE_ANNONCES = "annonces";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_TYPE_CONTRAT = "type_contrat";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CATEGORIE = "categorie";
    public static final String COLUMN_SECTEUR = "secteur";
    public static final String COLUMN_VILLE = "ville";

    // Requête de création de la table annonces
    private static final String CREATE_ANNONCES_TABLE = "CREATE TABLE " + TABLE_ANNONCES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TITRE + " TEXT,"
            + COLUMN_TYPE_CONTRAT + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_CATEGORIE + " TEXT,"
            + COLUMN_SECTEUR + " TEXT,"
            + COLUMN_VILLE + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ANNONCES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer la table existante si elle existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNONCES);
        // Recréer la table
        onCreate(db);
    }
}
