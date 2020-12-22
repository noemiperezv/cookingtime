package mx.edu.utng.cookingtime;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RecetasActivity extends AppCompatActivity {


    private TextInputEditText txtCalorias, txtNombre, txtIngredientes, txtCategoria;

    private Button btnGuardar, btnMostrar, btnCerrar;
    FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);
        getSupportActionBar().hide();
        txtCalorias = (TextInputEditText) findViewById(R.id.txtCalorias);
        txtNombre = (TextInputEditText) findViewById(R.id.txtNombre);
        txtIngredientes = (TextInputEditText) findViewById(R.id.txtIngredientes);
        txtCategoria = (TextInputEditText) findViewById(R.id.txtCategoria);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnMostrar = (Button) findViewById(R.id.btnMostrar);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);
        dbAuth = FirebaseAuth.getInstance();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtCalorias.getText().toString().equals("")|| txtNombre.getText().toString().
                        equals("")||txtIngredientes.getText().toString().equals("") || txtCategoria.getText().toString().equals("")){
                    validarTextos();
                }else{
                    GuardarDatos(v);
                    limpiarTextos();
                    Toast.makeText(RecetasActivity.this, "Datos Registrados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecetasActivity.this,MostrarActivity.class);
                startActivity(intent);
                Toast.makeText(RecetasActivity.this, "Datos Mostrados", Toast.LENGTH_SHORT).show();
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAuth.signOut();
                Intent intent = new Intent(RecetasActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void GuardarDatos(View v) {

        SqlLite sqlLite = new SqlLite(this, "receta", null, 1);
        SQLiteDatabase sqLiteDatabase = sqlLite.getWritableDatabase();

        int calorias = Integer.parseInt(txtCalorias.getText().toString());
        String nombre = txtNombre.getText().toString();
        String ingredientes = txtIngredientes.getText().toString();
        String categoria = txtCategoria.getText().toString();


        ContentValues values = new ContentValues();
        values.put("calorias", calorias);
        values.put("nombre", nombre);
        values.put("ingredientes", ingredientes);
        values.put("categoria", categoria);

        Long resultado = sqLiteDatabase.insert("receta", null, values);
        Toast.makeText(this, "Resultado: " + resultado, Toast.LENGTH_SHORT).show();
    }

    private void limpiarTextos() {
        txtCalorias.setText("");
        txtNombre.setText("");
        txtIngredientes.setText("");
        txtCategoria.setText("");
    }

    public void validarTextos() {
        if (txtCalorias.getText().toString().equals("")) {
            txtCalorias.setText("Faltan las calorías");
        }
        if (txtNombre.getText().toString().equals("")) {
            txtNombre.setText("Falta el Nombre");
        }
        if (txtIngredientes.getText().toString().equals("")) {
            txtIngredientes.setText("Faltan los ingredientes");
        }
        if (txtCategoria.getText().toString().equals("")) {
            txtCategoria.setText("Falta la categoría");
        }
    }


}