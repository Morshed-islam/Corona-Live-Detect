package qtec.live.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import qtec.live.corona.api.ApiInterface;
import qtec.live.corona.api.ApiUtils;
import qtec.live.corona.model.GetCountryModel;
import qtec.live.corona.model.GetGlobalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private TextView _cases,_deaths,_recovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        _cases = findViewById(R.id.cases);
        _deaths = findViewById(R.id.deaths);
        _recovered = findViewById(R.id.recovered);

        getGlobalData();
//        getCountriesData();


    }


    private void getGlobalData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface();
        Call<GetGlobalModel> call = apiInterface.getGlobalDetails();

        call.enqueue(new Callback<GetGlobalModel>() {
            @Override
            public void onResponse(Call<GetGlobalModel> call, Response<GetGlobalModel> response) {

                if (response.isSuccessful()) {

                    Log.e("death", "onResponse: " + response.body().getDeaths());

                    _cases.setText(""+response.body().getCases());
                    _deaths.setText(""+response.body().getDeaths());
                    _recovered.setText(""+response.body().getRecovered());
                } else {
                    Toast.makeText(MainActivity.this, "below response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetGlobalModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void getCountriesData(){

        ApiInterface apiInterface = ApiUtils.getApiInterface();
        Call<List<GetCountryModel>> call = apiInterface.getCountryDetails();


        call.enqueue(new Callback<List<GetCountryModel>>() {
            @Override
            public void onResponse(Call<List<GetCountryModel>> call, Response<List<GetCountryModel>> response) {


                if (response.isSuccessful()){

                    for (GetCountryModel model : response.body()){

                        Log.e("countries", "onResponse: "+model.getCountry());

                    }

                }else {

                    Toast.makeText(MainActivity.this, "below response", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<GetCountryModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
