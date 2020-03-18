package qtec.live.corona.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import qtec.live.corona.R;
import qtec.live.corona.model.GetCountryModel;

/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */
public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.MyViewHolder> implements Filterable {

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
        holder._recovered.setText(""+list.get(position).getRecovered());
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

    @Override
    public Filter getFilter() {
        return countryFilter;
    }
    List<GetCountryModel> filterList;
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
             filterList = new ArrayList<>();

            if (charSequence == null || charSequence.length() ==0){
                filterList.addAll(list);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (GetCountryModel model : list){

                    if (model.getCountry().toLowerCase().contains(filterPattern)){
                        filterList.add(model);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((List) filterResults.values);
           updateList(list);

        }
    };


    public void updateList(List<GetCountryModel> list){
        filterList = list;
        notifyDataSetChanged();
    }


    public StringBuilder convertNumberIntoBangla(String str){
        char[] arabicChars = {'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<str.length();i++)
        {
            if(Character.isDigit(str.charAt(i)))
            {
                builder.append(arabicChars[(int)(str.charAt(i))-48]);
            }
            else
            {
                builder.append(str.charAt(i));
            }
        }
        return builder;
    }


}
