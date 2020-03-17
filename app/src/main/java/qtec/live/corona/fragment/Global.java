package qtec.live.corona.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import qtec.live.corona.MainActivity;
import qtec.live.corona.R;
import qtec.live.corona.api.ApiInterface;
import qtec.live.corona.api.ApiUtils;
import qtec.live.corona.model.GetGlobalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Global extends Fragment {

    private TextView _cases, _deaths, _recovered;
    private ProgressBar progressBar;

    private LinearLayout _firstLayout,_secondLayout,_thirdLayout;


    public Global() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_global, container, false);

        _cases = view.findViewById(R.id.cases);
        _deaths = view.findViewById(R.id.deaths);
        _recovered = view.findViewById(R.id.recovered);
        progressBar = view.findViewById(R.id.main_progressBar);
        _firstLayout = view.findViewById(R.id.first_layout);
        _secondLayout = view.findViewById(R.id.second_layout);
        _thirdLayout = view.findViewById(R.id.third_layout);


        getGlobalData();
        return view;
    }


    private void getGlobalData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface();
        Call<GetGlobalModel> call = apiInterface.getGlobalDetails();

        call.enqueue(new Callback<GetGlobalModel>() {
            @Override
            public void onResponse(Call<GetGlobalModel> call, Response<GetGlobalModel> response) {

                if (response.isSuccessful()) {

                    _firstLayout.setVisibility(View.VISIBLE);
                    _secondLayout.setVisibility(View.VISIBLE);
                    _thirdLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Log.e("death", "onResponse: " + response.body().getDeaths());

                    _cases.setText(""+response.body().getCases());
                    _deaths.setText(""+response.body().getDeaths());
                    _recovered.setText(""+response.body().getRecovered());
                } else {
                    Toast.makeText(getContext(), "below response", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    _firstLayout.setVisibility(View.GONE);
                    _secondLayout.setVisibility(View.GONE);
                    _thirdLayout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<GetGlobalModel> call, Throwable t) {
                Toast.makeText(getContext(), "server error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                _firstLayout.setVisibility(View.GONE);
                _secondLayout.setVisibility(View.GONE);
                _thirdLayout.setVisibility(View.GONE);
            }
        });


    }

}
