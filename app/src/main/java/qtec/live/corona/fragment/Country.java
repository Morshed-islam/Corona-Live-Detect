package qtec.live.corona.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import qtec.live.corona.R;
import qtec.live.corona.adapter.CountryRecyclerAdapter;
import qtec.live.corona.api.ApiInterface;
import qtec.live.corona.api.ApiUtils;
import qtec.live.corona.model.GetCountryModel;
import qtec.live.corona.utils.InternetCheck;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */
public class Country extends Fragment {

    private RecyclerView recyclerView;
    private List<GetCountryModel> list;
    private CountryRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private TextView count;

    public Country() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        count = view.findViewById(R.id.cases_count);
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rc_view);
        progressBar = view.findViewById(R.id.main_progressBar);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CountryRecyclerAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

        InternetCheck check = new InternetCheck();
        if (check.isInternetOn(getActivity()) == false){
            Toast.makeText(getContext(), "Please Check Your Internet!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

        }else {

            getCountriesData();
        }


    return view;
    }


    private void getCountriesData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface();
        Call<List<GetCountryModel>> call = apiInterface.getCountryDetails();

        call.enqueue(new Callback<List<GetCountryModel>>() {
            @Override
            public void onResponse(Call<List<GetCountryModel>> call, Response<List<GetCountryModel>> response) {

                int sum =0;
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    for (GetCountryModel model : response.body()) {
                        Log.e("countries", "onResponse: " + model.getCountry());

                        for (int i=0; i<list.size(); i++){
                            Log.e("List", "list size: "+i);
                            sum+=list.get(i).getCases();
                        }
                        Log.e("List", "cases sum: "+sum );


                        count.setText(""+sum);

                        list.add(model);
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(getContext(), "below response", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<GetCountryModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.search, menu);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);

//        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        SearchView searchView = null;
        if (mSearchMenuItem != null) {
            searchView = (SearchView) mSearchMenuItem.getActionView();
        }


        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
