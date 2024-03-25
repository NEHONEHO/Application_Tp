package com.example.projectapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectapp.DatabaseHelper;
import com.example.projectapp.MainActivity;
import com.example.projectapp.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    String EmailHolder;
    EditText editTextTitre, editTextTypeContrat, editTextDescription;
    Spinner spinnerCategories, spinnerSecteur, spinnerVille;
    Button btnSubmit, LogOUT;
    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialisation des vues
        editTextTitre = findViewById(R.id.editTextTitre);
        editTextTypeContrat = findViewById(R.id.editTextTypeContrat);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerCategories = findViewById(R.id.spinnerCategories);
        spinnerSecteur = findViewById(R.id.spinnerSecteur);
        spinnerVille = findViewById(R.id.spinnerVille);
        btnSubmit = findViewById(R.id.btnSubmit);
        LogOUT = findViewById(R.id.button1);

        // Initialisation du DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ajouter des valeurs au Spinner de catégorie
        List<String> categoriesList = new ArrayList<>();
        categoriesList.add("Finance");
        categoriesList.add("Technologie");
        categoriesList.add("Santé");
        categoriesList.add("Éducation");
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(categoriesAdapter);

        // Ajouter des valeurs au Spinner de secteur
        List<String> secteurList = new ArrayList<>();
        secteurList.add("Privé");
        secteurList.add("Public");
        secteurList.add("À but non lucratif");
        ArrayAdapter<String> secteurAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, secteurList);
        secteurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSecteur.setAdapter(secteurAdapter);

        // Ajouter des valeurs au Spinner de ville
        List<String> villeList = new ArrayList<>();
        villeList.add("Agadir");
        villeList.add("Marrakech");
        villeList.add("Rabat");
        villeList.add("Casablanca");
        ArrayAdapter<String> villeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, villeList);
        villeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVille.setAdapter(villeAdapter);

        Intent intent = getIntent();
        // Receiving User Email Send By MainActivity.
        EmailHolder = intent.getStringExtra(MainActivity.UserEmail);

        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Finishing current DashBoard activity on button click.
                finish();
                Toast.makeText(DashboardActivity.this, "Log Out Successful", Toast.LENGTH_LONG).show();
            }
        });

        // Adding click listener to Submit button.
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values from form fields
                String titre = editTextTitre.getText().toString();
                String typeContrat = editTextTypeContrat.getText().toString();
                String description = editTextDescription.getText().toString();
                String categorie = spinnerCategories.getSelectedItem().toString();
                String secteur = spinnerSecteur.getSelectedItem().toString();
                String ville = spinnerVille.getSelectedItem().toString();

                // Ajouter les données à la base de données
                addAnnonceToDatabase(titre, typeContrat, description, categorie, secteur, ville);
            }
        });
    }

    private void addAnnonceToDatabase(String titre, String typeContrat, String description,
                                      String categorie, String secteur, String ville) {
        // Ajouter les données à la base de données
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITRE, titre);
        values.put(DatabaseHelper.COLUMN_TYPE_CONTRAT, typeContrat);
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_CATEGORIE, categorie);
        values.put(DatabaseHelper.COLUMN_SECTEUR, secteur);
        values.put(DatabaseHelper.COLUMN_VILLE, ville);

        long newRowId = db.insert(DatabaseHelper.TABLE_ANNONCES, null, values);
        if (newRowId != -1) {
            Toast.makeText(DashboardActivity.this, "Annonce ajoutée à la base de données", Toast.LENGTH_LONG).show();

            // Afficher le nombre d'annonces dans la ville sélectionnée
            displayAdsCount(ville);
        } else {
            Toast.makeText(DashboardActivity.this, "Erreur lors de l'ajout de l'annonce à la base de données", Toast.LENGTH_LONG).show();
        }
    }

    private void displayAdsCount(String selectedCity) {
        // Simulated data for ads in different cities
        String[] cities = {"Paris", "Londres", "New York", "Tokyo"};
        int[] adsCount = {15, 20, 30, 25}; // Simulated ads count for each city

        // Find the ads count for the selected city
        int adsInSelectedCity = 0;
        for (int i = 0; i < cities.length; i++) {
            if (selectedCity.equals(cities[i])) {
                adsInSelectedCity = adsCount[i];
                break;
            }
        }

        // Show a message with the number of ads in the selected city
        String message = "Nombre d'annonces dans " + selectedCity + ": " + adsInSelectedCity;
        Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
