package com.ejemplo.recyclerpokemon;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lchang on 31/10/17.
 */

public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private ImageView imgFotoPokemon;
    private TextView lblNombrePokemon;
    private PokemonRecyclerAdapter pokemonRecyclerAdapter;

    public PokemonViewHolder(View view) {
        super(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        imgFotoPokemon = (ImageView) view.findViewById(R.id.imgPokemon_item);
        lblNombrePokemon = (TextView) view.findViewById(R.id.lblNombrePokemon_item);
    }
    public ImageView getImgFotoPokemon() {
        return imgFotoPokemon;
    }
    public TextView getLblNombrePokemon() {
        return lblNombrePokemon;
    }
    @Override
    public void onClick(View view) {
        pokemonRecyclerAdapter.clickListener.onItemClick(getAdapterPosition(), view);
    }
    @Override
    public boolean onLongClick(View view) {
        pokemonRecyclerAdapter.clickListener.onItemLongClick(getAdapterPosition(), view);
        return false;
    }
}