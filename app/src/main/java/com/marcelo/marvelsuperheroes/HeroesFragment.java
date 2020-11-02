package com.marcelo.marvelsuperheroes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcelo.marvelsuperheroes.adapters.HeroesAdapter;
import com.marcelo.marvelsuperheroes.models.Hero;
import com.marcelo.marvelsuperheroes.models.HeroDataContainer;
import com.marcelo.marvelsuperheroes.models.HeroDataWrapper;
import com.marcelo.marvelsuperheroes.models.HeroImage;
import com.marcelo.marvelsuperheroes.services.RetrofitConfig;
import com.marcelo.marvelsuperheroes.utils.Constants;
import com.marcelo.marvelsuperheroes.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeroesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroesFragment extends Fragment {

    private RecyclerView rvHeroes;
    private ProgressBar progressBar;
    private ProgressBar progressBarLoadMore;
    private TextView tvError;

    private LinearLayoutManager layoutManager;
    private ArrayList<Hero> heroes;
    private HeroesAdapter heroesAdapter;
    private boolean isLoading = false;
    private boolean loadedAll = false;

    private int PAGE_SIZE = 20;

    public HeroesFragment() {
        // Required empty public constructor
    }

    public static HeroesFragment newInstance() {
        HeroesFragment fragment = new HeroesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_heroes, container, false);

        progressBar = v.findViewById(R.id.pb_load_heroes);
        progressBarLoadMore = v.findViewById(R.id.pb_load_more_heroes);
        tvError = v.findViewById(R.id.tv_err_heroes);

        rvHeroes = v.findViewById(R.id.rv_heroes);

        layoutManager = new LinearLayoutManager(this.getContext());
        rvHeroes.setLayoutManager(layoutManager);

        heroes = new ArrayList<>();
        heroesAdapter = new HeroesAdapter(heroes);
        rvHeroes.setAdapter(heroesAdapter);

        rvHeroes.addOnScrollListener(rvOnScrollListener);

        heroesAdapter.setOnItemClickListener(heroClick);

        loadHeroes();

        return v;
    }

    private HeroesAdapter.OnItemClickListener heroClick = new HeroesAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Hero hero = heroes.get(position);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            HeroDetailsFragment heroDetailsFragment = HeroDetailsFragment.newInstance(hero);
            heroDetailsFragment.show(fm, "");
        }
    };

    private void loadHeroes() {
        rvHeroes.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        getHeroesFromServer();
    }

    private void loadMoreHeroes() {
        progressBarLoadMore.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        getHeroesFromServer();
    }

    private RecyclerView.OnScrollListener rvOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !loadedAll) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    loadMoreHeroes();
                }
            }
        }
    };

    private void getHeroesFromServer() {
        isLoading = true;

        Long ts = System.currentTimeMillis()/1000;

        Call<HeroDataWrapper> call = new RetrofitConfig()
                .getMarvelService()
                .getHeroes(ts.toString(), Constants.MARVEL_PUBLIC_API_KEY, Utils.getHashKey(), "name", PAGE_SIZE, heroes.size());

        call.enqueue(new Callback<HeroDataWrapper>() {
            @Override
            public void onResponse(Call<HeroDataWrapper> call, Response<HeroDataWrapper> response) {
                Log.d("MarvelService", response.toString());
                if (response.isSuccessful()) {
                    HeroDataWrapper heroDataWrapper = response.body();

                    if (heroDataWrapper.getCode() == 200) {
                        HeroDataContainer heroesData = heroDataWrapper.getData();

                        ArrayList<Hero> heroesReceived = heroesData.getResults();

                        heroes.addAll(heroesReceived);

                        heroesAdapter.notifyDataSetChanged();
                        isLoading = false;

                        if(heroes.size() >= heroesData.getTotal()) {
                            loadedAll = true;
                        }

                        getHeroesSuccess();
                    } else {
                        getHeroesFail();
                    }
                } else {
                    isLoading = false;
                    getHeroesFail();
                }
            }

            @Override
            public void onFailure(Call<HeroDataWrapper> call, Throwable t) {
                Log.e("MarvelService   ", "Erro:" + t.getMessage());
                isLoading = false;
                getHeroesFail();
            }
        });
    }

    private void getHeroesSuccess() {
        rvHeroes.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        progressBarLoadMore.setVisibility(View.GONE);
    }

    private void getHeroesFail() {
        rvHeroes.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvError.setText(R.string.error_server);
        tvError.setVisibility(View.VISIBLE);
        progressBarLoadMore.setVisibility(View.GONE);
    }
}