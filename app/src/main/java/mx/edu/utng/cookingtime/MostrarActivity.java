package mx.edu.utng.cookingtime;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostrarActivity extends AppCompatActivity implements IAxiliarReceta {

    RecyclerView idrecyclerview;
    ArrayList<Receta> recetaArrayList;
    SqlLite sqlLite;

    private RecetaAdapter recetaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        idrecyclerview = findViewById(R.id.idrecyclerview);
        recetaArrayList = new ArrayList<>();
        sqlLite = new SqlLite(this, "receta", null, 1);
        recetaAdapter = new RecetaAdapter(this, recetaArrayList);

        RecyclerView recyclerView = findViewById(R.id.idrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(recetaAdapter);
        mostrarDatos();


    }

    public void mostrarDatos() {
        SQLiteDatabase sqLiteDatabase = sqlLite.getReadableDatabase();
        Receta receta = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM receta", null);
        while (cursor.moveToNext()) {
            receta = new Receta();
            receta.setCalorias(cursor.getInt(0));
            receta.setNombre(cursor.getString(1));
            receta.setIngredientes(cursor.getString(2));
            receta.setCategoria(cursor.getString(3));
            recetaAdapter.agregarReceta(receta);
        }
    }


    @Override
    public void OpcionEditar(Receta receta) {
        Intent intent = new Intent(MostrarActivity.this, EditarActivity.class);
        intent.putExtra("receta", receta);
        startActivity(intent);
    }

    @Override
    public void OpcionEliminar(final Receta receta) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Mensaje");
        alerta.setMessage("Esta seguro que desea Eliminar? " + receta.getNombre());
        alerta.setCancelable(false);
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarReceta(receta);
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();

    }

    private void eliminarReceta(Receta receta) {

        SqlLite sqlLite = new SqlLite(this, "receta", null, 1);
        SQLiteDatabase sqLiteDatabase = sqlLite.getWritableDatabase();
        String calorias = String.valueOf(receta.getCalorias());
        if(!calorias.isEmpty()){
            sqLiteDatabase.delete("receta","calorias="+calorias,null);
            Toast.makeText(this, "Se Elimino Correctamente", Toast.LENGTH_SHORT).show();
            recetaAdapter.eliminarReceta(receta);
            sqLiteDatabase.close();
        }else{
            Toast.makeText(this, "No se ha podido eliminar ", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_buscar,menu);
        MenuItem buscars=menu.findItem(R.id.idbuscar);
        SearchView searchView=(SearchView)MenuItemCompat.getActionView(buscars);
        buscar(searchView);
        return true;

    }

    private void buscar(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(recetaAdapter!=null)
                    recetaAdapter.getFilter().filter((newText));
                return true;
            }
        });
    }


}