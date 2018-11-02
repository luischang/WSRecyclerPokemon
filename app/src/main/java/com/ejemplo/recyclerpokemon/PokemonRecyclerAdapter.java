package com.ejemplo.recyclerpokemon;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lchang on 31/10/17.
 */

public class PokemonRecyclerAdapter extends RecyclerView.Adapter<PokemonViewHolder> {

    public static ClickListener clickListener;
    /**Variable Global*/
    private List<Pokemon> pokemonList = new ArrayList<>();
    /**Constructor*/
    public PokemonRecyclerAdapter(List<Pokemon> pokemonList) {
        /**Asignamos la lista a nuestra variable*/
        this.pokemonList = pokemonList;
    }
    public PokemonRecyclerAdapter() {
        this.pokemonList = new ArrayList<>();
    }

    public void setAddListPokemons(List<Pokemon> pokemonLista) {
        pokemonList.addAll(pokemonLista);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        /**Devuelve la cantidad total de los items que contendrá el RecyclerView*/
        return pokemonList.size();
    }
    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        /**Actualiza el valor de cada uno de los elementos por posición del RecyclerView*/
        /*Obtenemos un pokemon por posición*/
        Pokemon pokemon = pokemonList.get(position);
        /*Actualizamos el valor del control que contendrá el nombre de Pokemon*/
        holder.getLblNombrePokemon().setText(pokemon.getNombre());
        /*Utilizamos Glide para colocar la Foto del Pokemon*/
        String urlFoto = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getId() + ".png";
        Glide.with(holder.itemView.getContext())
                .load(urlFoto)
                .into(holder.getImgFotoPokemon());
    }
    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**Creamos y devolvemos el objeto ViewHolder*/
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_pokemon, parent, false);

        return new PokemonViewHolder(v);
    }
    public interface ClickListener{
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);

    }
    public void setOnItemClickListener(ClickListener clickListener){
        PokemonRecyclerAdapter.clickListener = clickListener;
    }

}
