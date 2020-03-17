package qtec.live.corona;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import qtec.live.corona.api.ApiInterface;
import qtec.live.corona.api.ApiUtils;
import qtec.live.corona.fragment.About;
import qtec.live.corona.fragment.Country;
import qtec.live.corona.fragment.Global;
import qtec.live.corona.model.GetCountryModel;
import qtec.live.corona.model.GetGlobalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Corona Live");


        loadFragment(new Global());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_global:
                    toolbar.setTitle("Global");
                    fragment = new Global();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_country:
                    toolbar.setTitle("Country");
                    fragment = new Country();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_about:
                    toolbar.setTitle("About");
                    fragment = new About();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };


    private void getCountriesData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface();
        Call<List<GetCountryModel>> call = apiInterface.getCountryDetails();

        call.enqueue(new Callback<List<GetCountryModel>>() {
            @Override
            public void onResponse(Call<List<GetCountryModel>> call, Response<List<GetCountryModel>> response) {

                if (response.isSuccessful()) {

                    for (GetCountryModel model : response.body()) {
                        Log.e("countries", "onResponse: " + model.getCountry());
                    }

                } else {
                    Toast.makeText(MainActivity.this, "below response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetCountryModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
