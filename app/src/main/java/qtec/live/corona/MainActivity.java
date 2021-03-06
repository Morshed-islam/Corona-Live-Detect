package qtec.live.corona;


import android.content.Context;
import android.net.ConnectivityManager;
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
/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */
public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    int view = R.layout.activity_main;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Corona Live Stats");



        loadFragment(new Global());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_global:
                    toolbar.setTitle("Corona Live Stats");
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


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(1);
            return;
        } else {
            Toast.makeText(getBaseContext(), "Click two times to close an activity",    Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }





}
