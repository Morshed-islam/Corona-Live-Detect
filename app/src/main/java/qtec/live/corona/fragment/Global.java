package qtec.live.corona.fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import qtec.live.corona.MainActivity;
import qtec.live.corona.R;
import qtec.live.corona.api.ApiInterface;
import qtec.live.corona.api.ApiUtils;
import qtec.live.corona.model.GetGlobalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */
public class Global extends Fragment {

    private TextView _cases, _deaths, _recovered;
    //active cases
    private TextView _tv_currently_patient, _tv_condition, _tv_condition_ratio, _tv_critical, _tv_critical_ratio;
    //closed cases
    private TextView _tv_closed_patient, _tv_recovered, _tv_recovered_ratio, _tv_death, _tv_death_ratio;
    //    private LinearLayout _firstLayout, _secondLayout, _thirdLayout;
    private LinearLayout _layout_cases, _linear_layout_top;
    private ProgressBar progressBar;

    public Global() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_global, container, false);

        initViews(view);

        new globalRatioJSOUP().execute();

        getGlobalData();
        return view;
    }

    private void initViews(View view) {
        _cases = view.findViewById(R.id.cases);
        _deaths = view.findViewById(R.id.deaths);
        _recovered = view.findViewById(R.id.recovered);
        progressBar = view.findViewById(R.id.main_progressBar);

        _layout_cases = view.findViewById(R.id.layout_cases);
        _linear_layout_top = view.findViewById(R.id.linear_layout_top);

        //active and closed cases
        _tv_currently_patient = view.findViewById(R.id.active_case_patient);
        _tv_condition = view.findViewById(R.id.active_case_condition);
        _tv_condition_ratio = view.findViewById(R.id.active_case_condition_percentage);
        _tv_critical = view.findViewById(R.id.active_case_critical);
        _tv_critical_ratio = view.findViewById(R.id.active_case_critical_percentage);

        _tv_closed_patient = view.findViewById(R.id.closed_case_outcome);
        _tv_recovered = view.findViewById(R.id.closed_case_recovered);
        _tv_recovered_ratio = view.findViewById(R.id.closed_case_recovered_percentage);
        _tv_death = view.findViewById(R.id.closed_case_death);
        _tv_death_ratio = view.findViewById(R.id.closed_case_death_percentage);


    }


    private void getGlobalData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface();
        Call<GetGlobalModel> call = apiInterface.getGlobalDetails();

        call.enqueue(new Callback<GetGlobalModel>() {
            @Override
            public void onResponse(Call<GetGlobalModel> call, Response<GetGlobalModel> response) {

                if (response.isSuccessful()) {

                    Log.e("death", "onResponse: " + response.body().getDeaths());

                    _cases.setText("" + response.body().getCases());
                    _deaths.setText("" + response.body().getDeaths());
                    _recovered.setText("" + response.body().getRecovered());
                } else {
                    Toast.makeText(getContext(), "below response", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<GetGlobalModel> call, Throwable t) {
                Toast.makeText(getContext(), "server error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });


    }


    private class globalRatioJSOUP extends AsyncTask<Void, Void, Boolean> {


        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(Void... voids) {

            Document doc;
            Elements simplifiedData;

            try {
                doc = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();

                simplifiedData = doc.select("div.content-inner");

                //getting Currently Infected Patients and Cases which had an outcome values
                Elements _firstRow = simplifiedData.select("div.number-table-main");
                //getting in Mild Condition,Serious or Critical,Recovered / Discharged,Deaths
                Elements _2ndRow = simplifiedData.select("span.number-table");
                //percentage on both data
                Elements _percentage = simplifiedData.select("strong");

                String mFirstRow = _firstRow.text();
                String m2ndRow = _2ndRow.text();
                String mPercentage = _percentage.text();

                //first row active patient, closed patient data
                final String mActivePatient = mFirstRow.substring(0, 8);
                final String mClosedPatient = mFirstRow.substring(8, mFirstRow.length());

                //active ratio
                final String mActiveConditionRatio = mPercentage.substring(0, 3);
                final String mActiveCriticalRatio = mPercentage.substring(3, 5);

                //closed ratio
                final String mClosedRecoveredRatio = mPercentage.substring(5, 8);
                final String mClosedDeathRatio = mPercentage.substring(8, 10);

                //2nd Row-> condition,critical,recovered,death data
                final String mCondition = m2ndRow.substring(0, 8);
                final String mCritical = m2ndRow.substring(8, 14);
                final String mRecovered = m2ndRow.substring(14, 21);
                final String mDeath = m2ndRow.substring(21, m2ndRow.length());


                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //active case setText
                        _tv_currently_patient.setText(mActivePatient);
                        _tv_condition.setText(mCondition);
                        _tv_condition_ratio.setText("(" + mActiveConditionRatio + "%)");
                        _tv_critical.setText(mCritical);
                        _tv_critical_ratio.setText("(" + mActiveCriticalRatio + "%)");

                        //closed case setText
                        _tv_closed_patient.setText(mClosedPatient);
                        _tv_recovered.setText(mRecovered);
                        _tv_recovered_ratio.setText("(" + mClosedRecoveredRatio + "%)");
                        _tv_death.setText(mDeath);
                        _tv_death_ratio.setText("(" + mClosedDeathRatio + "%)");
                    }
                });


                Log.e("jsoup", "jsoup data-: " + m2ndRow.toString());
                Log.e("jsoup", "jsoup data-: " + mDeath.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            _layout_cases.setVisibility(View.VISIBLE);
            _linear_layout_top.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
