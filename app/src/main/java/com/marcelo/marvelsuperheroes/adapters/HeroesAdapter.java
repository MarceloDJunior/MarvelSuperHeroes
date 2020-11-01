package com.marcelo.marvelsuperheroes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcelo.marvelsuperheroes.R;
import com.marcelo.marvelsuperheroes.models.Hero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder> {
    private final ArrayList<Hero> heroes;
    static OnItemClickListener mItemClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class HeroesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvHero;
        public ImageView ivHero;

        public HeroesViewHolder(View v) {
            super(v);
            tvHero = v.findViewById(R.id.tv_hero);
            ivHero = v.findViewById(R.id.iv_hero);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HeroesAdapter(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HeroesAdapter.HeroesViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_heroes, parent, false);

        HeroesViewHolder vh = new HeroesViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HeroesViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Hero hero = heroes.get(position);

        if (hero != null) {
            holder.tvHero.setText(hero.getName());
            if (hero.getThumbnail() != null && hero.getThumbnail().getPath() != null)
                Picasso.get()
                        .load(hero.getThumbnail().getPath() + "/portrait_medium." + hero.getThumbnail().getExtension())
                        .into(holder.ivHero);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return heroes.size();
    }
}
