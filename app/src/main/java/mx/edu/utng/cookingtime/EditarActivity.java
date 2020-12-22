package mx.edu.utng.cookingtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class EditarActivity extends AppCompatActivity {

    private RecetaAdapter personaAdapter;

    private TextInputEditText txtCaloriasEditar, txtNombreEditar, txtIngredientesEditar, txtCategoriaEditar;
    private Receta receta;


    private Button btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        getSupportActionBar().hide();
        txtCaloriasEditar = findViewById(R.id.txtCaloriasEditar);
        txtNombreEditar = findViewById(R.id.txtNombreEditar);
        txtIngredientesEditar = findViewById(R.id.txtIngredientesEditar);
        txtCategoriaEditar = findViewById(R.id.txtCategoriaEditar);
        btnEditar = findViewById(R.id.btnEditarReceta);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarDatos(v);
            }
        });

        receta = (Receta) getIntent().getSerializableExtra("receta");
        llenarDatos();


    }



    private void llenarDatos() {
        txtCaloriasEditar.setText(String.valueOf(receta.getCalorias()));
        txtCaloriasEditar.setEnabled(false);
        txtNombreEditar.setText(receta.getNombre());
        txtIngredientesEditar.setText(receta.getIngredientes());
        txtCategoriaEditar.setText(receta.getCategoria());

    }


    private void editarDatos(View v) {

        SqlLite sqlLite = new SqlLite(this, "receta", null, 1);

        SQLiteDatabase sqLiteDatabase = sqlLite.getWritableDatabase();
        Integer calorias = Integer.parseInt(txtCaloriasEditar.getText().toString());
        String nombre = txtNombreEditar.getText().toString();
        String ingredientes = txtIngredientesEditar.getText().toString();
        String categoria = txtCategoriaEditar.getText().toString();

        ContentValues values = new ContentValues();
        values.put("calorias", calorias);
        values.put("nombre", nombre);
        values.put("ingredientes", ingredientes);
        values.put("categoria", categoria);

        sqLiteDatabase.update("receta", values, "calorias=" + calorias, null);
        sqLiteDatabase.close();
        finish();
        Toast.makeText(this, "Datos Editados", Toast.LENGTH_SHORT).show();



        Intent intent = new Intent(EditarActivity.this, MostrarActivity.class);
        startActivity(intent);

    }


}