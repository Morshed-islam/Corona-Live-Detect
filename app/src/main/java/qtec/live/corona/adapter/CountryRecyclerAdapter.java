package qtec.live.corona.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import qtec.live.corona.R;
import qtec.live.corona.model.GetCountryModel;
import qtec.live.corona.model.GetGlobalModel;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.MyViewHolder>{

    private Context mContext;
    private List<GetCountryModel> list = new ArrayList<>();

    public CountryRecyclerAdapter(Context mContext, List<GetCountryModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_custom_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryRecyclerAdapter.MyViewHolder holder, int position) {
        holder._country.setText(""+list.get(position).getCountry());
        holder._cases.setText(""+list.get(position).getCases());
        holder._cases_today.setText(""+list.get(position).getTodayCases());
        holder._cases_active.setText(""+list.get(position).getActive());
        holder._death.setText(""+list.get(position).getDeaths());
        holder._death_today.setText(""+list.get(position).getTodayDeaths());
        holder._recovered.setText(String.valueOf(list.get(position).getRecovered()));
        holder._critical.setText(""+list.get(position).getCritical());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView _cases,_cases_today,_cases_active;
        public TextView _death,_death_today;
        public TextView _recovered,_critical;
        public TextView _country;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            _country = itemView.findViewById(R.id.country);
            _cases = itemView.findViewById(R.id.tv_cases);
            _cases_today = itemView.findViewById(R.id.tv_cases_today);
            _cases_active = itemView.findViewById(R.id.tv_cases_active);
            _death = itemView.findViewById(R.id.tv_deaths);
            _death_today = itemView.findViewById(R.id.tv_deaths_today);
            _recovered = itemView.findViewById(R.id.tv_recovered);
            _critical = itemView.findViewById(R.id.tv_critical);

        }
    }


}
