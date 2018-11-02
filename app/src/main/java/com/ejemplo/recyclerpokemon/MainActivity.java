package com.ejemplo.recyclerpokemon;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String urlPokemons = "https://pokeapi.co/api/v2/pokemon/";
    private RecyclerView recyclerViewPokemons;
    private PokemonRecyclerAdapter adapter;
    private boolean puedeCargar = false;
    private String nextUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**Referencia al Control RecyclerView*/
        recyclerViewPokemons = (RecyclerView) findViewById(R.id.rvPokemons);
        /**Se instancia el Adaptador RecyclerView*/
        adapter = new PokemonRecyclerAdapter();
        //Método onClick del adaptador
        adapter.setOnItemClickListener(new PokemonRecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(getApplicationContext(), "Valor enviado: " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PokemonActivity.class);
                intent.putExtra("idPokemon", String.valueOf(position + 1));
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(int position, View v) {

            }
        });


        /**Se asigna el Adaptador al RecyclerView*/
        recyclerViewPokemons.setAdapter(adapter);
        /**Se asigna el comportamiento del RecyclerView (Contexto, # de Columnas*/
        recyclerViewPokemons.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        /**En **/
        recyclerViewPokemons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int itemsVisibles = recyclerView.getLayoutManager().getChildCount();
                    int itemsTotales = recyclerView.getLayoutManager().getItemCount();
                    int primerItemVisible = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    //Log.i("TAG2","CARGA: "+ itemsVisibles + " - " + primerItemVisible + " - " + itemsTotales);
                    if (puedeCargar) {
                        if (itemsVisibles + primerItemVisible >= itemsTotales) {
                            Log.i("TAG", "CARGA: " + itemsVisibles + " - " + primerItemVisible + " - " + itemsTotales);
                            puedeCargar = false;
                            getObtenerPokemons(nextUrl);
                        }
                    }
                }
            }
        });

        getObtenerPokemons(urlPokemons);
    }

    public void getObtenerPokemons(String urlActual) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, /**Que Metodo: GET, POST, PUT, etc*/
                urlActual, /**URL del servicio*/
                null, /**Envio de parámetro a traves de un JSON*/
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nextUrl = response.getString("next");
                            /**Primero buscamos la etiqueta "results" que es de tipo JSONArray*/
                            JSONArray jsonArrayPokemon = response.getJSONArray("results");
                            /**Verificamos si existen valores*/
                            if (jsonArrayPokemon.length() > 0) {
                                List<Pokemon> miListaPokemon = new ArrayList<>();
                                puedeCargar = true;
                                /**Recorremos cada uno de los elemetos del JSONArray*/
                                for (int i = 0; i < jsonArrayPokemon.length(); i++) {
                                    /**Obtenemos uno a uno cada JSONObject*/
                                    JSONObject jsonPokemon = jsonArrayPokemon.getJSONObject(i);
                                    /**Buscamos las etiquetas "url" y "name"*/
                                    final String url = jsonPokemon.getString("url");
                                    final String nombre = jsonPokemon.getString("name");
                                    /**instanciamos nuestro objeto nuevoPokemon*/
                                    final Pokemon nuevoPokemon = new Pokemon(nombre, url);
                                    /**Llenamos nuestra lista de Pokemons*/
                                    miListaPokemon.add(nuevoPokemon);
                                }
                                adapter.setAddListPokemons(miListaPokemon);
                            }
                        } catch (JSONException je) {
                            puedeCargar = false;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /**Si ocurre un error*/
                    }
                }
        );
        /**Agregamos la solicitud a la cola.*/
        requestQueue.add(jsonObjectRequest);
    }


}
