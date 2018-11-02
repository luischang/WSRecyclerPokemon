package com.ejemplo.recyclerpokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonActivity extends AppCompatActivity {
    private String urlPokemons = "https://pokeapi.co/api/v2/pokemon/";
    TextView tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        Intent intent = getIntent();
        String pk_id = intent.getStringExtra("idPokemon");
        ImageView imgPokemon = findViewById(R.id.imgPokemon);
        tvNombre = findViewById(R.id.tvNombre);
        String urlFoto = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pk_id + ".png";
        getObtenerPokemon(urlPokemons, pk_id);
        Glide.with(this)
                .load(urlFoto)
                .into(imgPokemon);
    }

    public void getObtenerPokemon(String urlActual, final String pokemonID) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, /**Que Metodo: GET, POST, PUT, etc*/
                urlActual, /**URL del servicio*/
                null, /**Envio de parámetro a traves de un JSON*/
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArrayPokemon = response.getJSONArray("results");
                            if (jsonArrayPokemon.length() > 0) {
                                List<Pokemon> miListaPokemon = new ArrayList<>();
                                for (int i = 0; i < jsonArrayPokemon.length(); i++) {
                                    if (i + 1 == Integer.parseInt(pokemonID)) {
                                        JSONObject jsonPokemon = jsonArrayPokemon.getJSONObject(i);
                                        tvNombre.setText("Nombre: " + jsonPokemon.getString("name"));
                                    }
                                }
                            }
                        } catch (JSONException je) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
