package com.famsoft.adb;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQL sql = null;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private GridView gvArticulos;
    private TextView txtArticulo;
    private TextView txtDescripcion;
    private TextView txtSerie;

    public MainActivity() {
        sql = new SQL( this );
    }

    private void Limpiar() {
        txtArticulo.setText("");
        txtDescripcion.setText("");
        txtSerie.setText("");
    }

    private void CargarProductos() {
        list = new ArrayList<String>();

        try
        {
            sql.abrir();
            Cursor cursor = sql.obtenerProductos();
            gvArticulos.setAdapter(null);
            if(cursor.moveToFirst())
            {
                do
                {
                    list.add(cursor.getString(cursor.getColumnIndex("articulo")));
                    list.add(cursor.getString(cursor.getColumnIndex("descripcion")));
                    list.add(cursor.getString(cursor.getColumnIndex("serie")));
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    gvArticulos.setAdapter(adapter);
                }
                while(cursor.moveToNext());
                Toast.makeText(getApplicationContext(), "Productos cargados!.", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Sin productos!.", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error al cargar productos!." + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.w("Error", e.getMessage());
        }
        finally
        {
            if (sql != null) sql.cerrar();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtArticulo = (TextView) findViewById(R.id.txtArticulo);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        txtSerie = (TextView) findViewById(R.id.txtSerie);

        Button btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if (txtArticulo.getText().toString().length() == 0 ||
                            txtDescripcion.getText().toString().length() == 0 ||
                            txtSerie.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Llene el formulario correctamente!.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    sql = new SQL(getApplicationContext());
                    sql.abrir();
                    sql.insertarProducto(txtArticulo.getText().toString(), txtDescripcion.getText().toString(), txtSerie.getText().toString());
                    Toast.makeText(getApplicationContext(), "Producto agregado!.", Toast.LENGTH_LONG).show();
                    CargarProductos();
                    Limpiar();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error al agregar producto!." + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.w("Error", e.getMessage());
                }
                finally {
                    if (sql != null) sql.cerrar();
                }
            }
        });

        Button btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if (txtArticulo.getText().toString().length() == 0 ||
                            txtDescripcion.getText().toString().length() == 0 ||
                            txtSerie.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Llene el formulario correctamente!.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    sql = new SQL(getApplicationContext());
                    sql.abrir();
                    Cursor cursor = sql.obtenerProductos(1);
                    if (cursor.moveToFirst()) {
                        int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex("id")));
                        sql.actualizarProducto(id, txtArticulo.getText().toString(), txtDescripcion.getText().toString(), txtSerie.getText().toString());
                        Toast.makeText(getApplicationContext(), "Producto actualizado!.", Toast.LENGTH_LONG).show();
                    }
                    CargarProductos();
                    Limpiar();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error al actualizar producto!." + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                finally {
                    if (sql != null) sql.cerrar();
                }
            }
        });

        Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    sql = new SQL(getApplicationContext());
                    sql.abrir();
                    Cursor cursor = sql.obtenerProductos(1);
                    if (cursor.moveToFirst()) {
                        int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex("id")));
                        sql.eliminarProducto(id);
                        Toast.makeText(getApplicationContext(), "Producto eliminado!.", Toast.LENGTH_LONG).show();
                    }
                    CargarProductos();
                    Limpiar();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error al eliminar producto!." + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                finally {
                    if (sql != null) sql.cerrar();
                }
            }
        });

        gvArticulos = (GridView) findViewById(R.id.gvArticulos);
        gvArticulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        CargarProductos();
    }
}
