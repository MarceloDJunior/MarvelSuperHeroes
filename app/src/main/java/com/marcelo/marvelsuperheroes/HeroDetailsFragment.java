package com.marcelo.marvelsuperheroes;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcelo.marvelsuperheroes.models.Hero;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeroDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroDetailsFragment extends DialogFragment {

    private static final String ARG_PARAM = "hero";

    private ImageView ivHeroPicture;
    private TextView tvHeroName;
    private TextView tvHeroDescription;

    private Hero hero;

    public HeroDetailsFragment() {
        // Required empty public constructor
    }

    public static HeroDetailsFragment newInstance(Hero hero) {
        HeroDetailsFragment fragment = new HeroDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, hero);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hero = (Hero) getArguments().getSerializable(ARG_PARAM);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hero_details, container, false);

        ivHeroPicture = v.findViewById(R.id.iv_hero_picture);
        tvHeroName = v.findViewById(R.id.tv_hero_name);
        tvHeroDescription = v.findViewById(R.id.tv_hero_description);

        Picasso.get()
                .load(hero.getThumbnail().getPath() + "/portrait_medium." + hero.getThumbnail().getExtension())
                .into(ivHeroPicture);

        tvHeroName.setText(hero.getName());
        tvHeroDescription.setText(hero.getDescription());

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return v;
    }
}